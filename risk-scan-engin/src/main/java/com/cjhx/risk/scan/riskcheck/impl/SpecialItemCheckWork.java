package com.cjhx.risk.scan.riskcheck.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.scan.dao.RcScanRuleMapper;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.scan.riskcheck.CorrelativeFactory;
import com.cjhx.risk.scan.riskcheck.ExceptionUtil;
import com.cjhx.risk.scan.riskcheck.impl.algorithm.special.BuybackCollateralRestrictio;
import com.cjhx.risk.scan.riskcheck.impl.algorithm.special.ReverseBuybackPledgeRateMonitor;
import com.cjhx.risk.system.SpringUtil;
import com.cjhx.risk.util.DateUtil;

/**
 * @function: 特殊类校验
 * @date:2017年11月14日 下午2:53:32
 * @author: chencang
 */
public class SpecialItemCheckWork extends CorrelativeFactory implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(SpecialItemCheckWork.class);
	private static RcScanRuleMapper rcScanRuleMapper = (RcScanRuleMapper) SpringUtil.getBean("rcScanRuleMapper");
	
	private ExceptionUtil exceptionUtil;
	
	//本条逆回购记录是否触发风控
	private Integer date;	
	private RcRuleItem ruleItem;
	private List<ProductModel> products;
	private String scanId;
	private List<RcScanRule> scanRules = new ArrayList<>();
	private CheckUnit checkUnit;
	
	public SpecialItemCheckWork(ExceptionUtil exceptionUtil,int scanDatabaseDate,String scanId, CheckUnit checkUnit){
		this.exceptionUtil = exceptionUtil;
		this.scanId = scanId;
		this.checkUnit = checkUnit;
		this.ruleItem = checkUnit.getRuleItemBean();
		this.products = checkUnit.getProductList();
		this.date = scanDatabaseDate;
	}
	
	
	public void run() {
		this.execute();
	}

	
	//特殊类检查逻辑
	public void execute() {
		//一个checkUnit保存一条汇总记录
		try {
			this.saveRcScanRule();
		} catch (Exception e) {
			exceptionUtil.setSaveRcScanRuleError();
			logger.error("RuleId(" + ruleItem.getRuleId() + ") scanId(" + scanId
					+ ")======== saveRcScanRule():save RcScanRule error and this scan stop!!! ========\n" + e);
			e.printStackTrace();
			return;
		}
		
		try {
			this.correlateCheck();
			this.riskCheck();
		} catch (Exception e) {
			for(RcScanRule scanRule: scanRules){
				scanRule.setStatus(ParamConfig.STATUS_ERROR);
				scanRule.setErrorLog(e.getMessage());
			}
			logger.error("RuleId(" + ruleItem.getRuleId() + ") scanId(" + scanId + ") execute()计算异常：" + e.getMessage());
			e.printStackTrace();
		}
		
		//更新汇总记录的状态
		try {
			this.updateRcScanRule();
		} catch (Exception e) {
			exceptionUtil.setSaveRcScanRuleError();
			logger.error("RuleId(" + ruleItem.getRuleId() + ") scanId(" + scanId
					+ ")======== updateRcScanRule():update RcScanRule error and this scan stop!!! ========\n" + e);
			e.printStackTrace();
			return;
		}
	}

	
	/**
	 * RcScanRule保存逆回购汇总
	 */
	private void saveRcScanRule() {
		for (ProductModel product : products){
			RcScanRule scanRule = new RcScanRule(ruleItem, product);
			scanRule.setStartDate(DateUtil.formatToDayInt(new Date()));
			scanRule.setStartTime(DateUtil.formatToTimeInt(new Date()));
			Integer createDate = DateUtil.formatToDayInt(new Date());
			Integer createTime = DateUtil.formatToTimeInt(new Date());
			// 保存RcScanRule表记录
			String scanSeqId = rcScanRuleMapper.getScanSeqId();
			scanRule.setScanId(scanId);
			scanRule.setScanRuleId(scanSeqId);
			scanRule.setCreateDate(createDate);
			scanRule.setCreateTime(createTime);
			rcScanRuleMapper.insertRcScanRuleSelective(scanRule);
			this.scanRules.add(scanRule);
		}
		
	}

	/**
	 * @author: chencang
	 * @description: 根据不同的指标类型,进行特殊类风控检查
	 * @createTime: 2017年11月16日 上午10:31:42
	 */
	private void riskCheck(){
		String specialType = ruleItem.getSpecialType();
		if (ParamConfig.SPECIAL_RULE_REVERSEBUYBACK_PLEDGERATE_MONITOR.equals(specialType)){
			ReverseBuybackPledgeRateMonitor monitor = new ReverseBuybackPledgeRateMonitor();
			monitor.rickCheck(date,scanId, this.checkUnit,this.scanRules);
		} else if (ParamConfig.SPECIAL_RULE_BUYBACK_COLLATERAL_RESTRICTIO.equals(specialType)){
			BuybackCollateralRestrictio restrictio = new BuybackCollateralRestrictio();
			restrictio.rickCheck(date, specialType, checkUnit, this.scanRules);
		} else {
			return;
		}
	}
	
	private void updateRcScanRule() {
		RcScanRule result;
		for (RcScanRule scanRule : scanRules){
			result = new RcScanRule();
			result.setScanRuleId(scanRule.getScanRuleId());
			result.setComupteDate(date);
			result.setEndDate(scanRule.getEndDate());
			result.setEndTime(scanRule.getEndTime());
			if (ParamConfig.STATUS_ERROR.equals(scanRule.getStatus())) {
				result.setStatus(ParamConfig.STATUS_ERROR);
				result.setErrorLog(scanRule.getErrorLog());
			} else {
				result.setStatus(ParamConfig.STATUS_NORMAL);
			}
			rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
		}
	}

	@Override
	public void correlateCheck() {
		return;
	}
	
}
