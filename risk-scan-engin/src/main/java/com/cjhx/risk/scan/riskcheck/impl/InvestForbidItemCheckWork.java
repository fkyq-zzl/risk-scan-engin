package com.cjhx.risk.scan.riskcheck.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32scan.domain.AllStockInfoHis;
import com.cjhx.risk.o32scan.service.O32ScanStockService;
import com.cjhx.risk.scan.dao.RcRuleItemMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail0Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleMapper;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetail;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.scan.riskcheck.CorrelativeFactory;
import com.cjhx.risk.scan.riskcheck.ExceptionUtil;
import com.cjhx.risk.system.SpringUtil;
import com.cjhx.risk.util.DateUtil;

/**
 * 禁止投资类日终检查
 *
 * @author lujinfu
 * @date 2017年10月19日
 */
public class InvestForbidItemCheckWork extends CorrelativeFactory implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(InvestForbidItemCheckWork.class);

	private static O32ScanStockService o32ScanStockService = (O32ScanStockService) SpringUtil
			.getBean("o32ScanStockService");

	private static RcScanRuleMapper rcScanRuleMapper = (RcScanRuleMapper) SpringUtil
			.getBean("rcScanRuleMapper");
	private static RcScanRuleDetail0Mapper rcScanRuleDetail0Mapper = (RcScanRuleDetail0Mapper) SpringUtil
			.getBean("rcScanRuleDetail0Mapper");
	private static RcRuleItemMapper rcRuleItemMapper = (RcRuleItemMapper) SpringUtil.getBean("rcRuleItemMapper");
	
	private ExceptionUtil exceptionUtil;
	private int scanDatabaseDate;
	private CheckUnit checkUnit;
	private RcRuleItem ruleItem;
	private List<ProductModel> productList;
	private String scanId;
	//根据基金CODE查询对应RcScanRule
	private HashMap<String,RcScanRule> scanRuleMap = new HashMap<String,RcScanRule>();
	private List<RcScanRuleDetail> detailList;
	
	public InvestForbidItemCheckWork(ExceptionUtil exceptionUtil,int scanDatabaseDate, String scanId,CheckUnit checkUnit) {
		super();
		this.exceptionUtil = exceptionUtil;
		this.scanDatabaseDate = scanDatabaseDate;
		this.checkUnit = checkUnit;
		this.ruleItem = checkUnit.getRuleItemBean();
		this.productList = checkUnit.getProductList();
		this.scanId = scanId;
	}

	@Override
	public void run() {
		this.execute();
	}

	public void execute() {
		try {
			this.saveRcScanRule();
		} catch (Exception e) {
			exceptionUtil.setSaveRcScanRuleError();
			logger.error("ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") ======== saveRcScanRule():save RcScanRule error and this scan stop!!! ========\n"+ e);
			e.printStackTrace();
			return;
		}
		try {
			//记录时间
			this.correlateCheck();
			this.riskCheck();
		} catch (Exception e) {
			for(RcScanRule scanRule: scanRuleMap.values()){
				scanRule.setStatus(ParamConfig.STATUS_ERROR);
				scanRule.setErrorLog(e.getMessage());
			}
			logger.error("ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") execute()计算异常：" + e.getMessage());
			e.printStackTrace();
		}
		try {
			this.updateRcScanRule();
		} catch (Exception e) {
			exceptionUtil.setSaveRcScanRuleError();
			logger.error("ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") "
					+ "======== updateRcScanRule():update RcScanRule error and this scan stop!!! ========\n"+ e);
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 保存RcScanRule表记录
	 */
	private void saveRcScanRule(){
		RcScanRule scanRule;
		Integer startDate = DateUtil.formatToDayInt(new Date());
		Integer startTime = DateUtil.formatToTimeInt(new Date());
		String scanSeqId;
		for(ProductModel product:productList){
			scanRule = new RcScanRule(ruleItem, product);
			//保存RcScanRule表记录
			scanSeqId = rcScanRuleMapper.getScanSeqId();
			scanRule.setScanId(scanId);
			scanRule.setScanRuleId(scanSeqId);
			scanRule.setCreateDate(startDate);
			scanRule.setCreateTime(startTime);
			//记录时间
			scanRule.setStartDate(startDate);
			scanRule.setStartTime(startTime);
			rcScanRuleMapper.insertRcScanRuleSelective(scanRule);
			scanRuleMap.put(product.getProductionCode(), scanRule);
		}
	}
	
	private void updateRcScanRule(){
		Integer endDate = DateUtil.formatToDayInt(new Date());
		Integer endTime = DateUtil.formatToTimeInt(new Date());
		RcScanRule result;
		for(RcScanRule scanRule: scanRuleMap.values()){
			result = new RcScanRule();
			result.setScanRuleId(scanRule.getScanRuleId());
			result.setComupteDate(scanDatabaseDate);
			result.setEndDate(endDate);
			result.setEndTime(endTime);
			if (ParamConfig.STATUS_ERROR.equals(scanRule.getStatus())) {
				result.setStatus(ParamConfig.STATUS_ERROR);
				result.setErrorLog(scanRule.getErrorLog());
			} else {
				result.setStatus(ParamConfig.STATUS_NORMAL);
			}
			rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
		}
	}
	
	/**
	 * 禁止投资类指标查询是否触发风控
	 * 
	 * @param unit
	 */
	private void riskCheck() {
		// 是否触发风控与关联性检查一致，不作重复执行
	}

	@Override
	public void correlateCheck() {
		String ruleId = ruleItem.getRuleId();
		// 大类资产查询内码sql
		String addInnercodeSql = rcRuleItemMapper.selectCharValueByCharCodeAndRuleId(ParamConfig.HIS_ADD_INNERCODE_SQL,
				ruleId);
		addInnercodeSql = addInnercodeSql.replaceAll("(?i)#\\{scanDatabaseDate\\}", String.valueOf(scanDatabaseDate));
		String subInnercodeSql = rcRuleItemMapper.selectCharValueByCharCodeAndRuleId(ParamConfig.HIS_SUB_INNERCODE_SQL,
				ruleId);
		subInnercodeSql = subInnercodeSql.replaceAll("(?i)#\\{scanDatabaseDate\\}", String.valueOf(scanDatabaseDate));
		// 查询相关联的持仓证券集
		List<AllStockInfoHis> productStocks = o32ScanStockService.selectRelatedProductStocks(scanDatabaseDate,productList, addInnercodeSql,
				subInnercodeSql);
		// 记录关联详情
		if (productStocks != null && productStocks.size() > 0) {
			detailList = new ArrayList<RcScanRuleDetail>();
			RcScanRuleDetail detail;
			for (AllStockInfoHis stock : productStocks) {
				detail = new RcScanRuleDetail();
				detail.setProductionId(stock.getVcProdCode());//基金Code
				detail.setStockId(stock.getVcInterCodeO32());
				detail.setStockName(stock.getVcStockName());
				detail.setIssuerId(stock.getlIssuerId());
				detail.setIssuerName(stock.getVcIssuerName());
				detailList.add(detail);
			}
			//保存记录
			this.saveScanRuleDetail(detailList);
		}
		
	}
	
	/**
	 * 保存ScanRuleDetail记录
	 * @param checkUnitList
	 */
	private void saveScanRuleDetail(List<RcScanRuleDetail> scanDetailList){
		RcScanRule scanRule;
		int i;
		String scanSeqId;
		for(RcScanRuleDetail detail:scanDetailList){
			scanRule = scanRuleMap.get(detail.getProductionId());
			scanSeqId = rcScanRuleDetail0Mapper.getScanSeqId();
			detail.setScanRuleDetailId(scanSeqId);
			detail.setScanRuleId(scanRule.getScanRuleId());
			detail.setRuleId(ruleItem.getRuleId());
			detail.setProductionId(scanRule.getProductionId());
			detail.setProductionName(scanRule.getProductionName());
			detail.setProductionType(scanRule.getProductionType());
			detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_FORBID);
			detail.setRiskAccmulation(0);//连续触发天数
			detail.setComupteDate(DateUtil.formatToDayInt(new Date()));
			detail.setComputeTime(DateUtil.formatToTimeInt(new Date()));
			i = rcScanRuleDetail0Mapper.insertRcScanRuleDetailSelective(detail);
		}
	}

}
