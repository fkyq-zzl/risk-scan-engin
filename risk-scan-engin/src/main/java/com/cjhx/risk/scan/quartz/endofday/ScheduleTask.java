package com.cjhx.risk.scan.quartz.endofday;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32prod.service.O32ProductStockService;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.scan.riskcheck.ExceptionUtil;
import com.cjhx.risk.scan.riskcheck.MutexUnionHackle;
import com.cjhx.risk.scan.riskcheck.impl.InvestForbidItemCheckWork;
import com.cjhx.risk.scan.riskcheck.impl.InvestLimitItemCheckWork;
import com.cjhx.risk.scan.riskcheck.impl.InvestRangeItemCheckWork;
import com.cjhx.risk.scan.riskcheck.impl.SpecialItemCheckWork;
import com.cjhx.risk.scan.riskcheck.impl.algorithm.special.BuybackCollateralRestrictio;
import com.cjhx.risk.scan.riskcheck.impl.algorithm.special.ReverseBuybackPledgeRateMonitor;
import com.cjhx.risk.scan.service.AfterwardsScanService;
import com.cjhx.risk.util.DateUtil;

@Configuration
@Component // 此注解必加
@EnableScheduling // 此注解必加
/**
 * 任务类
 *
 * @author lujinfu
 * @date 2017年10月10日
 */
public class ScheduleTask {
	private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
	
	@Value("${scan.database.day:0}")
	private int scanDatabaseDay;
	
	@Value("${scan.thread.pool.threads:10}")
	private int nThreads;
	@Value("${scan.thread.pool.timeout:5}")
	private long timeout;
	
	@Autowired
	private AfterwardsScanService afterwardsScanService;
	
	@Autowired
	private O32ProductStockService o32ProductStockService;
	
	public int getScanDatabaseDay() {
		return scanDatabaseDay;
	}

	public void setScanDatabaseDay(int scanDatabaseDay) {
		this.scanDatabaseDay = scanDatabaseDay;
	}

	public void sayHello() {
		logger.info("======== sayHello() : Hello world !!!");
	}

	//定时任务入口
	public void scheduleTask() {
		try {
			logger.info("======== scheduleTask():save RcScan log...");
			String scanId = null;
			try {
				scanId = afterwardsScanService.startScanLog();
				logger.info("======== scheduleTask():save RcScan end");
			} catch (Exception e) {
				logger.error("======== scanId("+scanId+") scheduleTask():save RcScan error and scan stop!!! ========\n"+ e);
				e.printStackTrace();
				return;
			}
			if (scanId == null) {
				logger.error("======== scanId("+scanId+") scheduleTask():save RcScan fail and scan stop!!! ========");
				return;
			}
			
			this.afterwardsScanTask(scanId);
		} catch (Exception e) {
			logger.info("======== scheduleTask():scheduleTask error!!!/n" + e);
			e.printStackTrace();
		}
	}

	//事后风控扫描
	public void afterwardsScanTask(String scanId) throws Exception {
		boolean flag = false;
		
		//校验是否有正在跑的任务
		flag = this.valitateRunningScan(scanId);
		if(!flag){
			return;
		}
		
		//校验是否为交易日
		int scanDatabaseDate = DateUtil.getDaysOffsetDate(new Date(),scanDatabaseDay);
		int beforeDate = DateUtil.getTheDayBeforeOfIntDate(scanDatabaseDate);
		//因为函数需要一个偏移天数，当偏移天数为0时不生效，所以把要校验的日期减一天当偏移1天求值判断是否一致
		Integer callTradingDay = o32ProductStockService.callTradingDay(beforeDate, 1, 2, "1");
		if(callTradingDay == null){
			logger.error("======== scanId("+scanId+") afterwardsScanTask():查询"+scanDatabaseDate+"交易日为空!!! ========\n");
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return;
		}
		if(callTradingDay.intValue() != scanDatabaseDate){
			logger.info("======== scanId("+scanId+") afterwardsScanTask():"+scanDatabaseDate+"取持仓日期非交易日,扫描终止 ========\n");
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_F);
			return;
		}
		
		logger.info("======== afterwardsScanTask():scan rule...");
		// 指标产品集
		Map<RcRuleItem, List<CheckUnit>> scanMap = null;
		try {
			scanMap = afterwardsScanService.scanRule(scanDatabaseDate,scanId);
			logger.info("======== afterwardsScanTask():scan rule end");
		} catch (Exception e) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():scan rule error and scan stop!!! ========\n"
							+ e);
			e.printStackTrace();		
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return;
		}
		if (scanMap == null) {
			logger.info("======== afterwardsScanTask():scan rule is empty ========");
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_C);
			return;
		}

		logger.info("afterwardsScanTask():risk check correlative...");
		ExceptionUtil excep = new ExceptionUtil();
		// 指标和产品集组合为CheckUnit
		Set<Entry<RcRuleItem, List<CheckUnit>>> entrySet = scanMap.entrySet();
		RcRuleItem ruleItem;
		List<CheckUnit> srlist;
		String rcType;
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads);//线程池初始代
		for (Entry<RcRuleItem, List<CheckUnit>> entry : entrySet) {
			ruleItem = entry.getKey();
			srlist = entry.getValue();
			rcType = ruleItem.getRcType();
			// 关联性检查：查询出相关联的证券
			if (ParamConfig.RULE_TYPE_RATIO.equals(rcType)
					||ParamConfig.RULE_TYPE_ABSOLUTE.equals(rcType)) {
				for(CheckUnit unit: srlist){
					InvestLimitItemCheckWork checkWork = new InvestLimitItemCheckWork(excep,scanDatabaseDate,scanId,unit);
//					checkWork.execute();
					fixedThreadPool.execute(checkWork);//线程池添加线程
				}
			}
			if (ParamConfig.RULE_TYPE_RANGE.equals(rcType)) {
				for(CheckUnit unit: srlist){
					InvestRangeItemCheckWork checkWork = new InvestRangeItemCheckWork(excep,scanDatabaseDate,scanId,unit);
//					checkWork.execute();
					fixedThreadPool.execute(checkWork);//线程池添加线程
				}
			}
			if (ParamConfig.RULE_TYPE_FORBID.equals(rcType)) {
				for(CheckUnit unit: srlist){
					InvestForbidItemCheckWork checkWork = new InvestForbidItemCheckWork(excep,scanDatabaseDate,scanId,unit);
//					checkWork.execute();
					fixedThreadPool.execute(checkWork);//线程池添加线程
				}
			}
			if (ParamConfig.RULE_TYPE_SPECIAL.equals(rcType)){
				for (CheckUnit unit: srlist){
					String specialType = unit.getRuleItemBean().getSpecialType();
					if (ParamConfig.SPECIAL_RULE_REVERSEBUYBACK_PLEDGERATE_MONITOR.equals(specialType)
							|| ParamConfig.SPECIAL_RULE_BUYBACK_COLLATERAL_RESTRICTIO.equals(specialType)){
						SpecialItemCheckWork checkWork = new SpecialItemCheckWork(excep,scanDatabaseDate,scanId,unit);
//						checkWork.execute();
						fixedThreadPool.execute(checkWork);
					} 
				}
			}
			
		}
		try {
			fixedThreadPool.shutdown();//线程池关闭
			// awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
			//System.out.println("nThreads:"+nThreads);
			//System.out.println("timeout:"+timeout);
			while (!fixedThreadPool.awaitTermination(timeout, TimeUnit.SECONDS));
		} catch (InterruptedException ie) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():risk check correlative error and scan stop!!! ========\n"+ ie);
			ie.printStackTrace();
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return;
		}
		if(excep.getIsSaveRcScanRuleError()){
			logger.error("======== scanId("+scanId+") afterwardsScanTask():risk check correlative error and scan stop!!! ========\n");
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return;
		}
		logger.info("======== afterwardsScanTask():risk check correlative end");

		logger.info("======== afterwardsScanTask():unit check mutex union exclude...");
		try {
			MutexUnionHackle.mutexHackle(scanId);
			MutexUnionHackle.unionHackle(scanId);
			logger.info("======== afterwardsScanTask():unit check mutex union exclude end");
		} catch (Exception e) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():unit check mutex union exclude error and scan stop!!! ========\n"+ e);
			e.printStackTrace();
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return;
		}
		
		logger.info("======== afterwardsScanTask():save result...");
		try {
			afterwardsScanService.handleScanResult(scanId);
			logger.info("======== afterwardsScanTask():save result end ========");
		} catch (Exception e) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():save result handleScanResult() error!!! ========\n"+ e);
			e.printStackTrace();
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return;
		}
		this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_C);
	}

	//加同步锁，避免R001发现有P002后更新N状态期间，P002也进来发现有个R001也更新为N状态，致两者都是无扫描的N状态
	private synchronized boolean valitateRunningScan(String scanId){
		boolean flag = false;
		try {
			flag = afterwardsScanService.valitateRunningScan(scanId);
			if(!flag){
				logger.info("======== scanId("+scanId+") afterwardsScanTask():当前有未完成的扫描或有手动待执行的任务，终止当前扫描！！！");
				this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_N);
				return false;
			}
		} catch (Exception e) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():valitateRunningScan() error and scan stop!!! ========\n"+ e);
			e.printStackTrace();
			this.endScanLog(scanId, ParamConfig.RC_SCAN_STATUS_E);
			return false;
		}
		return true;
	}
	
	//记录日志扫描结束
	private void endScanLog(String scanId,String status){
		boolean flag = false;
		try {
			flag = afterwardsScanService.endScanLog(scanId, status);
			logger.info("======== afterwardsScanTask():endScanLog() and scan over!!! ========");
		} catch (Exception e) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():endScanLog() error and scan stop!!! ========\n"+ e);
			e.printStackTrace();
		}
		if (!flag) {
			logger.error("======== scanId("+scanId+") afterwardsScanTask():endScanLog() fail and scan stop!!! ========");
		}
	}
	
	//手动任务入口
	public void singleScheduleTask() {
//		if(true)return;//TODO 测试
		try {
			List<String> scanIds;
			try {
				scanIds = afterwardsScanService.selectWaitingScanTask();
			} catch (Exception e) {
				logger.error("======== singleScheduleTask():查询手动插入待事后扫描的任务 异常 扫描终止!!! ========\n"+ e);
				e.printStackTrace();
				return;
			}
			if (scanIds == null) {
				logger.info("======== singleScheduleTask():没有待扫描的任务  ========");
				return;
			}
			boolean flag = false;
			for(String scanId:scanIds){
				try {
					flag = afterwardsScanService.updateWaitingScanTask(scanId);
					if(!flag){
						continue;
					}
				} catch (Exception e) {
					logger.error("======== singleScheduleTask():更新 手动插入任务scanId("+scanId+") 异常 扫描终止!!! ========\n"+ e);
					e.printStackTrace();
					return;
				}
				this.afterwardsScanTask(scanId);
			}
		} catch (Exception e) {
			logger.info("======== singleScheduleTask():scheduleTask error!!!/n" + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置任务类的执行方法名，如 sayHello
	 * 
	 * @return
	 */
	public String getTargetMethod() {
		return "scheduleTask";
	}
}
