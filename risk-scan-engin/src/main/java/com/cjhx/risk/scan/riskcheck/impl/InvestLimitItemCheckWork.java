package com.cjhx.risk.scan.riskcheck.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32scan.service.O32ScanStockService;
import com.cjhx.risk.scan.dao.RcRuleItemMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail0Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail1Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail1StockMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail3Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail3ProdMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail4Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail4StockMapper;
import com.cjhx.risk.scan.dao.RcScanRuleMapper;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcRuleNumerator;
import com.cjhx.risk.scan.domain.RcRuleThreshold;
import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.ComputingResult;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetail;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;
import com.cjhx.risk.scan.model.RuleDenominatorDto;
import com.cjhx.risk.scan.model.StockValueDto;
import com.cjhx.risk.scan.model.StockValuePage;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.scan.riskcheck.CorrelativeFactory;
import com.cjhx.risk.scan.riskcheck.ExceptionUtil;
import com.cjhx.risk.system.SpringUtil;
import com.cjhx.risk.util.DateUtil;

/**
 * 投资限制类日终检查
 *
 * @author lujinfu
 * @date 2017年11月06日
 */
public class InvestLimitItemCheckWork extends CorrelativeFactory implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(InvestLimitItemCheckWork.class);

	// private static O32ProductStockService o32ProductStockService =
	// (O32ProductStockService) SpringUtil
	// .getBean("o32ProductStockService");
	private static O32ScanStockService o32ScanStockService = (O32ScanStockService) SpringUtil
			.getBean("o32ScanStockService");

	private static RcScanRuleMapper rcScanRuleMapper = (RcScanRuleMapper) SpringUtil.getBean("rcScanRuleMapper");
	private static RcScanRuleDetail0Mapper rcScanRuleDetail0Mapper = (RcScanRuleDetail0Mapper) SpringUtil
			.getBean("rcScanRuleDetail0Mapper");
	private static RcScanRuleDetail1Mapper rcScanRuleDetail1Mapper = (RcScanRuleDetail1Mapper) SpringUtil
			.getBean("rcScanRuleDetail1Mapper");
	private static RcScanRuleDetail1StockMapper rcScanRuleDetail1StockMapper = (RcScanRuleDetail1StockMapper) SpringUtil
			.getBean("rcScanRuleDetail1StockMapper");
	private static RcScanRuleDetail4Mapper rcScanRuleDetail4Mapper = (RcScanRuleDetail4Mapper) SpringUtil
			.getBean("rcScanRuleDetail4Mapper");
	private static RcScanRuleDetail3ProdMapper rcScanRuleDetail3ProdMapper = (RcScanRuleDetail3ProdMapper) SpringUtil
			.getBean("rcScanRuleDetail3ProdMapper");
	private static RcScanRuleDetail3Mapper rcScanRuleDetail3Mapper = (RcScanRuleDetail3Mapper) SpringUtil
			.getBean("rcScanRuleDetail3Mapper");
	private static RcScanRuleDetail4StockMapper rcScanRuleDetail4StockMapper = (RcScanRuleDetail4StockMapper) SpringUtil
			.getBean("rcScanRuleDetail4StockMapper");
	private static RcRuleItemMapper rcRuleItemMapper = (RcRuleItemMapper) SpringUtil.getBean("rcRuleItemMapper");

	private ExceptionUtil exceptionUtil;
	private int scanDatabaseDate;
	private CheckUnit checkUnit;
	private RcRuleItem ruleItem;
	private List<ProductModel> productList;
	// 便于根据基金Code查询产品
	private HashMap<String, ProductModel> fundMap = new HashMap<String, ProductModel>();
	private String scanId;
	// 多基金系列所有基金一个RcScanRule
	private RcScanRule scanRule;
	// 单基金系列根据基金CODE查询对应RcScanRule
	private HashMap<String, RcScanRule> scanRuleMap = new HashMap<String, RcScanRule>();
	// 多基金系列一个RcScanRule一个List<RcScanRuleDetail>
	private List<RcScanRuleDetail> detailList;
	// 单基金系列根据基金CODE查询对应List<RcScanRuleDetail>
	private HashMap<String, List<RcScanRuleDetail>> detailMap = new HashMap<String, List<RcScanRuleDetail>>();
	// 汇总类型
	private String summaryType;
	private String addInnercodeSql;
	private String subInnercodeSql;

	public InvestLimitItemCheckWork(ExceptionUtil exceptionUtil, int scanDatabaseDate, String scanId,
			CheckUnit checkUnit) {
		super();
		this.exceptionUtil = exceptionUtil;
		this.scanDatabaseDate = scanDatabaseDate;
		this.checkUnit = checkUnit;
		this.ruleItem = checkUnit.getRuleItemBean();
		this.summaryType = this.ruleItem.getSummaryType();
		this.productList = checkUnit.getProductList();
		for (ProductModel p : checkUnit.getProductList()) {
			if (ParamConfig.HIERARCHY_FUND.equals(p.getProductionType())) {
				fundMap.put(p.getProductionCode(), p);
			}
		}
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
			logger.error("ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") "
					+ "======== saveRcScanRule():save RcScanRule error and this scan stop!!! ========\n" + e);
			e.printStackTrace();
			return;
		}
		try {
			this.correlateCheck();
			this.riskCheck();
		} catch (Exception e) {
			if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
					|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)
					|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
				scanRule.setStatus(ParamConfig.STATUS_ERROR);
				scanRule.setErrorLog(e.toString());
			} else {
				for (RcScanRule scanRule1 : scanRuleMap.values()) {
					scanRule1.setStatus(ParamConfig.STATUS_ERROR);
					scanRule1.setErrorLog(e.toString() + e.getStackTrace());
				}
			}
			e.printStackTrace();
			logger.error(
					"ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ")  execute()计算异常：\n" + e);
		}
		try {
			this.updateRcScanRule();
		} catch (Exception e) {
			exceptionUtil.setSaveRcScanRuleError();
			logger.error("ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") "
					+ "======== updateRcScanRule():update RcScanRule error and this scan stop!!! ========\n" + e);
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 保存RcScanRule表记录
	 */
	private void saveRcScanRule() {
		Integer startDate = DateUtil.formatToDayInt(new Date());
		Integer startTime = DateUtil.formatToTimeInt(new Date());
		String scanSeqId;
		if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
			// 保存RcScanRule表记录
			scanRule = new RcScanRule(ruleItem);
			scanSeqId = rcScanRuleMapper.getScanSeqId();
			scanRule.setScanId(scanId);
			scanRule.setScanRuleId(scanSeqId);
			scanRule.setProductionType(ParamConfig.HIERARCHY_NONE);
			scanRule.setProductionName("汇总控制");
			scanRule.setCreateDate(startDate);
			scanRule.setCreateTime(startTime);
			// 记录时间
			scanRule.setStartDate(startDate);
			scanRule.setStartTime(startTime);
			rcScanRuleMapper.insertRcScanRuleSelective(scanRule);
		} else {
			RcScanRule scanRule1;
			for (ProductModel product : productList) {
				scanRule1 = new RcScanRule(ruleItem, product);
				// 保存RcScanRule表记录
				scanSeqId = rcScanRuleMapper.getScanSeqId();
				scanRule1.setScanId(scanId);
				scanRule1.setScanRuleId(scanSeqId);
				scanRule1.setProductionId(product.getProductionId());
				scanRule1.setProductionType(product.getProductionType());
				scanRule1.setProductionName(product.getProductionName());
				scanRule1.setCreateDate(startDate);
				scanRule1.setCreateTime(startTime);
				// 记录时间
				scanRule1.setStartDate(startDate);
				scanRule1.setStartTime(startTime);
				rcScanRuleMapper.insertRcScanRuleSelective(scanRule1);
				scanRuleMap.put(product.getProductionCode(), scanRule1);
			}
		}
	}

	private void updateRcScanRule() {
		Integer endDate = DateUtil.formatToDayInt(new Date());
		Integer endTime = DateUtil.formatToTimeInt(new Date());
		RcScanRule result;
		if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
			result = new RcScanRule();
			result.setScanRuleId(scanRule.getScanRuleId());
			result.setCheckResult(scanRule.getCheckResult());
			result.setComupteDate(scanDatabaseDate);
			result.setEndDate(endDate);
			result.setEndTime(endTime);
			if (ParamConfig.STATUS_ERROR.equals(scanRule.getStatus())) {
				result.setStatus(ParamConfig.STATUS_ERROR);
				result.setErrorLog(scanRule.getErrorLog());
			} else {
				// 默认为未触警，互斥子联合后再获取最高级别的触警状态更新
				result.setStatus(ParamConfig.STATUS_NORMAL);
			}
			rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
		} else {
			for (RcScanRule scanRule1 : scanRuleMap.values()) {
				result = new RcScanRule();
				result.setScanRuleId(scanRule1.getScanRuleId());
				result.setCheckResult(scanRule1.getCheckResult());
				result.setComupteDate(scanDatabaseDate);
				result.setEndDate(endDate);
				result.setEndTime(endTime);
				if (ParamConfig.STATUS_ERROR.equals(scanRule1.getStatus())) {
					result.setStatus(ParamConfig.STATUS_ERROR);
					result.setErrorLog(scanRule1.getErrorLog());
				} else {
					// 默认为未触警，互斥子联合后再获取最高级别的触警状态更新
					result.setStatus(ParamConfig.STATUS_NORMAL);
				}
				rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
			}
		}

	}

	@Override
	public void correlateCheck() throws Exception {
		// 查询总投资范围关联的证券并记录，默认为无触发风控
		String ruleId = ruleItem.getRuleId();
		String rcType = ruleItem.getRcType();
		String numeratorType = ruleItem.getNumeratorType();
		String operator = ruleItem.getOperator();

		// 投资范围-大类资产 查询内码sql
		addInnercodeSql = rcRuleItemMapper.selectCharValueByCharCodeAndRuleId(ParamConfig.HIS_ADD_INNERCODE_SQL,
				ruleId);
		addInnercodeSql = addInnercodeSql.replaceAll("(?i)#\\{scanDatabaseDate\\}", String.valueOf(scanDatabaseDate));
		subInnercodeSql = rcRuleItemMapper.selectCharValueByCharCodeAndRuleId(ParamConfig.HIS_SUB_INNERCODE_SQL,
				ruleId);
		subInnercodeSql = subInnercodeSql.replaceAll("(?i)#\\{scanDatabaseDate\\}", String.valueOf(scanDatabaseDate));
		// (关联证券)查询关联持仓证券集的分子值
		List<StockValueDto> stockValues = o32ScanStockService.selectRelatedProductStockValues(scanDatabaseDate,
				productList, addInnercodeSql, subInnercodeSql);

		//关联不到证券则不作计算，单基金多基金除外
		if (!ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)
				&& !ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
			if (stockValues == null || stockValues.size() < 1) {
				if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
						|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)
						|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
					scanRule.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
				} else {
					for (RcScanRule scanRule1 : scanRuleMap.values()) {
						scanRule1.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					}
				}
				logger.info("======== (ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") correlateCheck(),无关联持仓证券 ========");
				return;
			}
		}

		// (分子)根据汇总类型整合为RcScanRuleDetail详情记录并计算分子汇总值
		this.collectScanRuleDetailBySummaryType(stockValues);

		// (特殊分子)如果有选特殊分子，则分子累加选项对应值,数量没有特殊分子，只有单基金和所有基金有特殊分子
		BigDecimal specilNum = new BigDecimal("0");
		if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
			if (!ParamConfig.NUMERATOR_TYPE_AMOUNT.equals(numeratorType)) {
				List<RcRuleNumerator> numeratorList = rcRuleItemMapper.selectNumeratorItemFromRcRuleNumerator(ruleId);
				if (numeratorList != null && numeratorList.size() > 0) {
					// 对特殊分子已选的项累加
					List<StockValueDto> specialMoleculeSum = o32ScanStockService.selectSpecialMoleculeSumValue(scanDatabaseDate,
							summaryType,productList, numeratorList);
					if (specialMoleculeSum != null) {
						if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)){
							Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
							ComputingResult computingResult;
							String prodCode;
							BigDecimal specialMolecule;
							for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
								prodCode = entry.getKey();
								specialMolecule = new BigDecimal("0");
								for(StockValueDto dto: specialMoleculeSum){
									if(prodCode.equals(dto.getVcProdCode())&&dto.getTotalValue()!=null){
										specialMolecule = dto.getTotalValue();
										break;
									}
								}
								for (RcScanRuleDetail detail : entry.getValue()) {
									computingResult = detail.getComputingResult();
									computingResult.setNumerator(computingResult.getNumerator().add(specialMolecule));
								}
							}
						}else if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)){
							ComputingResult computingResult;
							BigDecimal  specialMolecule = new BigDecimal("0");
							for(StockValueDto dto: specialMoleculeSum){
								if(dto.getTotalValue()!=null){
									specialMolecule = dto.getTotalValue();
									break;
								}
							}
							for (RcScanRuleDetail detail : detailList) {
								computingResult = detail.getComputingResult();
								computingResult.setNumerator(computingResult.getNumerator().add(specialMolecule));
							}
						}
					}
				}
			}
		}
		
		// (分母)查询分母值
		if (ParamConfig.RULE_TYPE_RATIO.equals(rcType)) {
			// 分母累加选项对应值
			List<RuleDenominatorDto> denominatorItems = rcRuleItemMapper
					.selectDenominatorItemFromRcRuleDenominator(ruleId);
			// 分母选项为空则标记为无触警
			if (denominatorItems == null || denominatorItems.size() < 1) {
				if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
						|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)
						|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
					scanRule.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
				} else {
					for (RcScanRule scanRule1 : scanRuleMap.values()) {
						scanRule1.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					}
				}
				logger.info("======== ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") correlateCheck(),分母选项为空 ========");
				return;
			}
			//分子为数量时，按汇总类型查询对应 总股本/流通股本
			if (ParamConfig.NUMERATOR_TYPE_AMOUNT.equals(numeratorType)) {
				ComputingResult computingResult;
				BigDecimal amountSum = null;
				
				if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_STOCK.equals(summaryType)){
					for(List<RcScanRuleDetail> detailList0: detailMap.values()){
						for (RcScanRuleDetail detail : detailList0){
							computingResult = detail.getComputingResult();
							amountSum = o32ScanStockService.selectStockTotalSum(scanDatabaseDate,denominatorItems, detail.getStockId(),
									null);
							computingResult.setDenominator(amountSum);
						}
					}
				}else if(ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
					for (RcScanRuleDetail detail : detailList){
						computingResult = detail.getComputingResult();
						amountSum = o32ScanStockService.selectStockTotalSum(scanDatabaseDate,denominatorItems, detail.getStockId(),
								null);
						computingResult.setDenominator(amountSum);
					}
				} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)) {
					for(List<RcScanRuleDetail> detailList1: detailMap.values()){
						for (RcScanRuleDetail detail : detailList1){
							computingResult = detail.getComputingResult();
							amountSum = o32ScanStockService.selectStockTotalSum(scanDatabaseDate,denominatorItems, null,
									detail.getIssuerId());
							computingResult.setDenominator(amountSum);
						}
					}
				} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
					for (RcScanRuleDetail detail : detailList){
						computingResult = detail.getComputingResult();
						amountSum = o32ScanStockService.selectStockTotalSum(scanDatabaseDate,denominatorItems, null,
								detail.getIssuerId());
						computingResult.setDenominator(amountSum);
					}
				}
				
			} else {
				//分子为非数量时，分母计算(0 净资产 1 总资产 2 融资回购 3正回购金额):和产品关联
				List<StockValueDto> denominatorModel = o32ScanStockService.selectDenominatorSumValue(scanDatabaseDate, summaryType, productList,
						denominatorItems);
				BigDecimal denominator;
				ComputingResult computingResult;
				if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
						|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
						|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
					for (RcScanRuleDetail detail : detailList) {
						computingResult = detail.getComputingResult();
						denominator = new BigDecimal("0");
						if (denominatorModel != null) {
							for(StockValueDto dto: denominatorModel){
								if(dto.getTotalValue()!=null){
									denominator = dto.getTotalValue();
									break;
								}
							}
						}
						computingResult.setDenominator(denominator);
					}
				}else {
					Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
					String prodCode;
					for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
						prodCode = entry.getKey();
						denominator = new BigDecimal("0");
						if (denominatorModel != null){
							for(StockValueDto dto: denominatorModel){
								if(prodCode.equals(dto.getVcProdCode())&&dto.getTotalValue()!=null){
									denominator = dto.getTotalValue();
									break;
								}
							}
						}
						for (RcScanRuleDetail detail : entry.getValue()) {
							computingResult = detail.getComputingResult();
							computingResult.setDenominator(denominator);
						}
					}
				}
					
				// 分母计算 6前一日净值（取T-1日净资产）：和产品关联
				String denominatorType;
				for (RuleDenominatorDto item : denominatorItems) {
					denominatorType = item.getDenominator();
					if (ParamConfig.ASSET_TYPE_BEFORE_NET.equals(denominatorType)) {
						int beforeDate = DateUtil.getTheDayBeforeOfIntDate(scanDatabaseDate);
						List<RuleDenominatorDto> denominatorTemp = new ArrayList<RuleDenominatorDto>();
						RuleDenominatorDto itemTemp = new RuleDenominatorDto();
						itemTemp.setDenominator(ParamConfig.ASSET_TYPE_NET_ASSETS);
						denominatorTemp.add(itemTemp);
						List<StockValueDto> beforeNetModel = o32ScanStockService.selectDenominatorSumValue(beforeDate, summaryType, productList,
								denominatorTemp);
						BigDecimal beforeNet;
						if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
								|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
								|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)){
							for (RcScanRuleDetail detail : detailList) {
								computingResult = detail.getComputingResult();
								beforeNet = new BigDecimal("0");
								if (beforeNetModel != null) {
									for(StockValueDto dto: beforeNetModel){
										if(dto.getTotalValue()!=null){
											beforeNet = dto.getTotalValue();
											break;
										}
									}
									computingResult.setDenominator(computingResult.getDenominator().add(beforeNet));
								}
							}
						}else{
							Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
							String prodCode;
							for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
								prodCode = entry.getKey();
								beforeNet = new BigDecimal("0");
								if (beforeNetModel != null) {
									for(StockValueDto dto: beforeNetModel){
										if(prodCode.equals(dto.getVcProdCode())&&dto.getTotalValue()!=null){
											beforeNet = dto.getTotalValue();
											break;
										}
									}
									for (RcScanRuleDetail detail : entry.getValue()) {
										computingResult = detail.getComputingResult();
										computingResult.setDenominator(computingResult.getDenominator().add(beforeNet));
									}
								}
							}
						}
					}
				}
				
				// 分母计算 7 过去10交易日平均份额：和分子可投资范围关联
				for (RuleDenominatorDto item : denominatorItems) {
					denominatorType = item.getDenominator();
					if (ParamConfig.ASSET_TYPE_AVERAGE_SHARE.equals(denominatorType)) {
						List<RuleDenominatorDto> denominatorTemp = new ArrayList<RuleDenominatorDto>();
						RuleDenominatorDto itemTemp = new RuleDenominatorDto();
						itemTemp.setDenominator(ParamConfig.ASSET_TYPE_NET_ASSETS);
						denominatorTemp.add(itemTemp);
						BigDecimal stocksAvg = o32ScanStockService.selectEnTotalStocksAvg10(scanDatabaseDate,addInnercodeSql,
								subInnercodeSql);
						if (stocksAvg != null) {
							logger.info("======== ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") 过去10交易日平均份额:"
									+ stocksAvg + " ========");
							if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
									|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
									|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)){
								for (RcScanRuleDetail detail : detailList) {
									computingResult = detail.getComputingResult();
									computingResult.setDenominator(computingResult.getDenominator().add(stocksAvg));
								}
							}else{
								Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
								String prodCode;
								for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
									prodCode = entry.getKey();
									for (RcScanRuleDetail detail : entry.getValue()) {
										computingResult = detail.getComputingResult();
										computingResult.setDenominator(computingResult.getDenominator().add(stocksAvg));
									}
								}
							}
						}
					}
				}
			}
		} else if (ParamConfig.RULE_TYPE_ABSOLUTE.equals(rcType)) {
			ComputingResult computingResult;
			if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
					|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
					|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)){
				for (RcScanRuleDetail detail : detailList) {
					computingResult = detail.getComputingResult();
					computingResult.setDenominator(new BigDecimal("1"));
				}
			}else{
				Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
				String prodCode;
				for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
					prodCode = entry.getKey();
					for (RcScanRuleDetail detail : entry.getValue()) {
						computingResult = detail.getComputingResult();
						computingResult.setDenominator(new BigDecimal("1"));
					}
				}
			}
		}

		// (计算)分子除以分母
		// 设置的阀值
		if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)){
			this.computingResult(detailList);
			this.setThreshold(ruleId, null, detailList);
		}else{
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				this.computingResult(entry.getValue());
				this.setThreshold(ruleId, fundMap.get(entry.getKey()).getProductionId(), entry.getValue());
			}
		}

		// (比较)计算结果与阀值对比
		boolean isTrig;
		ComputingResult computingResult;
		if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)){
			List<RcScanRuleDetail> detailList1;
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				detailList1 = entry.getValue();
				for (RcScanRuleDetail detail : detailList1) {
					computingResult = detail.getComputingResult();
					isTrig = false;
					if(computingResult.getThreshold()!=null){
						isTrig = this.compareThreshold(computingResult.getThreshold(), rcType, computingResult.getResult(), operator,
								computingResult);
					}
					if (isTrig) {
						detail.setCheckResult(computingResult.getWarnningAction());
						detail.setTotalValue(
								String.valueOf(computingResult.getResult().setScale(5, BigDecimal.ROUND_HALF_UP)));
						detail.setSetValue(String
								.valueOf(computingResult.getTriggerThreshold().setScale(5, BigDecimal.ROUND_HALF_UP)));
						// 设置计算值
						for (RcScanRuleDetailValue value : detail.getScanDetailValues()) {
							value.setComputeValue(String.valueOf(new BigDecimal(value.getComputeValue())
									.divide(computingResult.getDenominator(), 5, BigDecimal.ROUND_HALF_UP)));
							value.setComupteDate(DateUtil.formatToDayInt(new Date()));
							value.setComputeTime(DateUtil.formatToTimeInt(new Date()));
						}
					} else {
						detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					}
				}
			}
		}else if(ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
				|| ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
			for (RcScanRuleDetail detail : detailList) {
				computingResult = detail.getComputingResult();
				isTrig = false;
				if(computingResult.getThreshold()!=null){
					isTrig = this.compareThreshold(computingResult.getThreshold(), rcType, computingResult.getResult(), operator,
							computingResult);
				}
				if (isTrig) {
					detail.setCheckResult(computingResult.getWarnningAction());
					detail.setTotalValue(
							String.valueOf(computingResult.getResult().setScale(5, BigDecimal.ROUND_HALF_UP)));
					detail.setSetValue(String
							.valueOf(computingResult.getTriggerThreshold().setScale(5, BigDecimal.ROUND_HALF_UP)));
					// 设置计算值
					for (RcScanRuleDetailValue value : detail.getScanDetailValues()) {
						value.setComputeValue(String.valueOf(new BigDecimal(value.getComputeValue())
								.divide(computingResult.getDenominator(), 5, BigDecimal.ROUND_HALF_UP)));
						value.setComupteDate(DateUtil.formatToDayInt(new Date()));
						value.setComputeTime(DateUtil.formatToTimeInt(new Date()));
					}
				} else {
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
				}
			}
		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)){
			List<RcScanRuleDetail> detailList2;
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				detailList2 = entry.getValue();
				for (RcScanRuleDetail detail : detailList2) {
					computingResult = detail.getComputingResult();
					isTrig = false;
					if(computingResult.getThreshold()!=null
							&&new BigDecimal("0").compareTo(computingResult.getDenominator())!=0){
						isTrig = this.compareThreshold(computingResult.getThreshold(), rcType, computingResult.getResult(), operator,
								computingResult);
					}
					if (isTrig) {
						detail.setCheckResult(computingResult.getWarnningAction());
						detail.setTotalValue(
								String.valueOf(computingResult.getResult().setScale(5, BigDecimal.ROUND_HALF_UP)));
						detail.setSetValue(String
								.valueOf(computingResult.getTriggerThreshold().setScale(5, BigDecimal.ROUND_HALF_UP)));
					} else {
						detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					}
				}
			}
			
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
			for (RcScanRuleDetail detail : detailList) {
				computingResult = detail.getComputingResult();
				isTrig = false;
				if(computingResult.getThreshold()!=null){
					isTrig = this.compareThreshold(computingResult.getThreshold(), rcType, computingResult.getResult(), operator,
							computingResult);
				}
				if (isTrig) {
					detail.setCheckResult(computingResult.getWarnningAction());
					detail.setTotalValue(
							String.valueOf(computingResult.getResult().setScale(5, BigDecimal.ROUND_HALF_UP)));
					detail.setSetValue(String
							.valueOf(computingResult.getTriggerThreshold().setScale(5, BigDecimal.ROUND_HALF_UP)));
				} else {
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
				}
			}
		} else {
			List<RcScanRuleDetail> detailList0;
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				detailList0 = entry.getValue();
				for (RcScanRuleDetail detail : detailList0) {
					computingResult = detail.getComputingResult();
					isTrig = false;
					if(computingResult.getThreshold()!=null){
						isTrig = this.compareThreshold(computingResult.getThreshold(), rcType, computingResult.getResult(), operator,
								computingResult);
					}
					if (isTrig) {
						detail.setCheckResult(computingResult.getWarnningAction());
						detail.setSetValue(String
								.valueOf(computingResult.getTriggerThreshold().setScale(5, BigDecimal.ROUND_HALF_UP)));
						// 设置计算值
						detail.setComputeValue(
								String.valueOf(computingResult.getResult().setScale(5, BigDecimal.ROUND_HALF_UP)));
						detail.setComupteDate(DateUtil.formatToDayInt(new Date()));
						detail.setComputeTime(DateUtil.formatToTimeInt(new Date()));
					} else {
						detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					}
				}
			}
		}
		// 保存记录关联详情
		this.saveScanRuleDetail();
	}

	// (计算)分子除以分母
	private void computingResult(List<RcScanRuleDetail> detailListTemp){
		ComputingResult computingResult;
		BigDecimal numerator, denominator, result = null;
		for (RcScanRuleDetail detail : detailListTemp) {
			computingResult = detail.getComputingResult();
			numerator = computingResult.getNumerator();
			denominator = computingResult.getDenominator();
			if(new BigDecimal("0").compareTo(denominator)!=0){//分母非0判断
				result = numerator.divide(denominator, 5, BigDecimal.ROUND_HALF_UP);
			}
			computingResult.setResult(result);
			logger.info("======== ScanId(" + scanId + ") RuleId(" + ruleItem.getRuleId() + ") 分子(" + numerator.toString() + ")"
					+ " 分母(" + denominator.toString() + ") 商(" + (result==null?"null":result.toString())
					+ ")========");
		}
	}
	
	// 设置的阀值
	private void setThreshold(String ruleId,String productionId,List<RcScanRuleDetail> detailListTemp){
		RcRuleThreshold threshold = rcRuleItemMapper.selectRcRuleThresholdByFundId(ruleId, summaryType,
				productionId);
		ComputingResult computingResult;
		for (RcScanRuleDetail detail : detailListTemp) {
			computingResult = detail.getComputingResult();
			if(computingResult.getResult()!=null){
				computingResult.setThreshold(threshold);
			}
		}
	}

	private void addRcScanRuleDetail(String prodCode,RcScanRuleDetail detail,Collection<RcScanRuleDetail> details){
		if(prodCode == null){
			if(detailList == null){
				detailList = new ArrayList<RcScanRuleDetail>();
			}
			if(detail != null){
				detailList.add(detail);
			}
			if(details != null){
				detailList.addAll(details);
			}
		}else{
			if(!detailMap.containsKey(prodCode)){
				detailMap.put(prodCode, new ArrayList<RcScanRuleDetail>());
			}
			if(detail != null){
				detailMap.get(prodCode).add(detail);
			}
			if(details != null){
				detailMap.get(prodCode).addAll(details);
			}
		}
	}
	
	private void collectScanRuleDetailBySummaryType(List<StockValueDto> stockValues) {
		String numeratorType = ruleItem.getNumeratorType();
		ProductModel product;
		RcScanRuleDetail detail;
		ComputingResult computingResult;
		if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_STOCK.equals(summaryType)) {
			// 一个证券一条RcScanRuleDetail记录
			for (StockValueDto dto : stockValues) {
				product = fundMap.get(dto.getVcProdCode());
				detail = new RcScanRuleDetail();
				detail.setProductionId(product.getProductionId());
				detail.setProductionName(product.getProductionName());
				detail.setProductionType(product.getProductionType());
				detail.setStockId(dto.getVcInterCodeO32());
				detail.setStockName(dto.getVcStockName());
				detail.setIssuerId(dto.getlIssuerId());
				detail.setIssuerName(dto.getVcIssuerName());
				detail.setComputeValue(this.getNumeratorValue(dto).toString());

				computingResult = new ComputingResult();
				computingResult.setNumerator(this.getNumeratorValue(dto));
				detail.setComputingResult(computingResult);
				this.addRcScanRuleDetail(dto.getVcProdCode(), detail, null);
			}
		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)) {
			// 一个公司一条RcScanRuleDetail记录，对应证券集为RcScanRuleDetail的RcScanRuleDetailProdStock:<prodCode,<issuerId,detail>>
			HashMap<String, HashMap<String, RcScanRuleDetail>> issuerAllMap = new HashMap<String, HashMap<String, RcScanRuleDetail>>();
			String issuerId;
			HashMap<String, RcScanRuleDetail> issuerMap;
			List<RcScanRuleDetailValue> scanDetailValues;
			RcScanRuleDetailValue dValue;
			for (StockValueDto dto : stockValues) {
				if(!issuerAllMap.containsKey(dto.getVcProdCode())){
					issuerAllMap.put(dto.getVcProdCode(), new HashMap<String, RcScanRuleDetail>());
				}
				issuerMap = issuerAllMap.get(dto.getVcProdCode());
				issuerId = dto.getlIssuerId();
				if (!issuerMap.containsKey(issuerId)) {
					detail = new RcScanRuleDetail();
					product = fundMap.get(dto.getVcProdCode());
					detail.setProductionId(product.getProductionId());
					detail.setProductionName(product.getProductionName());
					detail.setProductionType(product.getProductionType());
					detail.setIssuerId(dto.getlIssuerId());
					detail.setIssuerName(dto.getVcIssuerName());
					detail.setScanDetailValues(new ArrayList<RcScanRuleDetailValue>());
					computingResult = new ComputingResult();
					computingResult.setNumerator(new BigDecimal("0"));
					detail.setComputingResult(computingResult);
					issuerMap.put(issuerId, detail);
				}
				scanDetailValues = issuerMap.get(issuerId).getScanDetailValues();
				dValue = new RcScanRuleDetailValue();
				dValue.setStockId(dto.getVcInterCodeO32());
				dValue.setStockName(dto.getVcStockName());
				dValue.setComputeValue(this.getNumeratorValue(dto).toString());
				scanDetailValues.add(dValue);
			}
			
			// 计算汇总值
			StockValuePage page = new StockValuePage();
			page.setScanDatabaseDate(scanDatabaseDate);
			page.setSummaryType(summaryType);
			page.setNumeratorType(numeratorType);
			page.setProductList(productList);
			List<StockValueDto> stockTotalValues = o32ScanStockService.selectFundStockTotalValues(page);
			
			Iterator<Entry<String, HashMap<String, RcScanRuleDetail>>> iteratorAll = issuerAllMap.entrySet().iterator();
			Entry<String, HashMap<String, RcScanRuleDetail>> nextAll;
			HashMap<String, RcScanRuleDetail> valueAll;
			String prodCode;
			
			Iterator<Entry<String, RcScanRuleDetail>> iterator;
			Entry<String, RcScanRuleDetail> next;
			String key;
			RcScanRuleDetail value;
			BigDecimal totalValue;
			while (iteratorAll.hasNext()) {
				nextAll = iteratorAll.next();
				prodCode = nextAll.getKey();
				valueAll = nextAll.getValue();
				iterator = valueAll.entrySet().iterator();
				while (iterator.hasNext()) {
					next = iterator.next();
					key = next.getKey();
					value = next.getValue();
					totalValue = new BigDecimal("0");
					if(stockTotalValues!=null){
						for(StockValueDto dto: stockTotalValues){
							if(prodCode.equals(dto.getVcProdCode())&&key.equals(dto.getlIssuerId())){
								totalValue = dto.getTotalValue();
							}
						}
					}
					value.getComputingResult().setNumerator(totalValue);
				}
				this.addRcScanRuleDetail(prodCode, null, valueAll.values());
			}
		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)) {
			// 计算汇总值
			StockValuePage page = new StockValuePage();
			page.setScanDatabaseDate(scanDatabaseDate);
			page.setSummaryType(summaryType);
			page.setNumeratorType(numeratorType);
			page.setProductList(productList);
			page.setHisAddInnercodeSql(addInnercodeSql);
			page.setHisSubInnercodeSql(subInnercodeSql);
			List<StockValueDto> stockTotalValues = o32ScanStockService.selectFundStockTotalValues(page);
						
			// 一条RcScanRuleDetail记录
			BigDecimal totalValue;
			for(ProductModel product2:productList){
				detail = new RcScanRuleDetail();
				detail.setProductionId(product2.getProductionId());
				detail.setProductionName(product2.getProductionName());
				detail.setProductionType(product2.getProductionType());
				computingResult = new ComputingResult();
				computingResult.setNumerator(new BigDecimal("0"));
				detail.setComputingResult(computingResult);
				// 计算汇总值
				totalValue = new BigDecimal("0");
				if(stockTotalValues!=null){
					for(StockValueDto dto: stockTotalValues){
						if(product2.getProductionCode().equals(dto.getVcProdCode())){
							totalValue = dto.getTotalValue();
						}
					}
				}
				detail.getComputingResult().setNumerator(totalValue);
				this.addRcScanRuleDetail(product2.getProductionCode(), detail, null);
			}
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
			// 一个证券一条RcScanRuleDetail记录，对应产品集为RcScanRuleDetail的RcScanRuleDetailProdStock
			HashMap<String, RcScanRuleDetail> stockMap = new HashMap<String, RcScanRuleDetail>();
			String stockId;
			List<RcScanRuleDetailValue> scanDetailValues;
			RcScanRuleDetailValue dValue;
			ProductModel prod;
			for (StockValueDto dto : stockValues) {
				stockId = dto.getVcInterCodeO32();
				if (!stockMap.containsKey(stockId)) {
					detail = new RcScanRuleDetail();
					detail.setStockId(dto.getVcInterCodeO32());
					detail.setStockName(dto.getVcStockName());
					detail.setIssuerId(dto.getlIssuerId());
					detail.setIssuerName(dto.getVcIssuerName());
					detail.setScanDetailValues(new ArrayList<RcScanRuleDetailValue>());
					computingResult = new ComputingResult();
					computingResult.setNumerator(new BigDecimal("0"));
					detail.setComputingResult(computingResult);
					stockMap.put(stockId, detail);
				}
				scanDetailValues = stockMap.get(stockId).getScanDetailValues();
				dValue = new RcScanRuleDetailValue();
				prod = fundMap.get(dto.getVcProdCode());
				dValue.setProductionId(prod.getProductionId());
				dValue.setProductionName(prod.getProductionName());
				dValue.setProductionType(prod.getProductionType());
				dValue.setComputeValue(this.getNumeratorValue(dto).toString());
				scanDetailValues.add(dValue);
			}
			// 计算汇总值
			StockValuePage page = new StockValuePage();
			page.setScanDatabaseDate(scanDatabaseDate);
			page.setSummaryType(summaryType);
			page.setNumeratorType(numeratorType);
			page.setProductList(productList);
			List<StockValueDto> stockTotalValues = o32ScanStockService.selectFundStockTotalValues(page);
			
			Iterator<Entry<String, RcScanRuleDetail>> iterator = stockMap.entrySet().iterator();
			Entry<String, RcScanRuleDetail> next;
			String key;
			RcScanRuleDetail value;
			BigDecimal totalValue;
			while (iterator.hasNext()) {
				next = iterator.next();
				key = next.getKey();
				value = next.getValue();
				totalValue = new BigDecimal("0");
				if(stockTotalValues!=null){
					for(StockValueDto dto: stockTotalValues){
						if(key.equals(dto.getVcInterCodeO32())){
							totalValue = dto.getTotalValue();
						}
					}
				}
				value.getComputingResult().setNumerator(totalValue);
			}
			this.addRcScanRuleDetail(null, null, stockMap.values());
			
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
			// 一个公司一条RcScanRuleDetail记录，对应产品证券集为RcScanRuleDetail的RcScanRuleDetailProdStock
			HashMap<String, RcScanRuleDetail> issuerMap = new HashMap<String, RcScanRuleDetail>();
			String issuerId;
			List<RcScanRuleDetailValue> scanDetailValues;
			RcScanRuleDetailValue dValue;
			ProductModel prod;
			for (StockValueDto dto : stockValues) {
				issuerId = dto.getlIssuerId();
				if (!issuerMap.containsKey(issuerId)) {
					detail = new RcScanRuleDetail();
					detail.setIssuerId(dto.getlIssuerId());
					detail.setIssuerName(dto.getVcIssuerName());
					detail.setScanDetailValues(new ArrayList<RcScanRuleDetailValue>());
					computingResult = new ComputingResult();
					computingResult.setNumerator(new BigDecimal("0"));
					detail.setComputingResult(computingResult);
					issuerMap.put(issuerId, detail);
				}
				scanDetailValues = issuerMap.get(issuerId).getScanDetailValues();
				dValue = new RcScanRuleDetailValue();
				prod = fundMap.get(dto.getVcProdCode());
				dValue.setProductionId(prod.getProductionId());
				dValue.setProductionName(prod.getProductionName());
				dValue.setProductionType(prod.getProductionType());
				dValue.setStockId(dto.getVcInterCodeO32());
				dValue.setStockName(dto.getVcStockName());
				dValue.setComputeValue(this.getNumeratorValue(dto).toString());
				scanDetailValues.add(dValue);
			}
			// 计算汇总值
			StockValuePage page = new StockValuePage();
			page.setScanDatabaseDate(scanDatabaseDate);
			page.setSummaryType(summaryType);
			page.setNumeratorType(numeratorType);
			page.setProductList(productList);
			List<StockValueDto> stockTotalValues = o32ScanStockService.selectFundStockTotalValues(page);
			
			Iterator<Entry<String, RcScanRuleDetail>> iterator = issuerMap.entrySet().iterator();
			Entry<String, RcScanRuleDetail> next;
			String key;
			RcScanRuleDetail value;
			BigDecimal totalValue;
			while (iterator.hasNext()) {
				next = iterator.next();
				key = next.getKey();
				value = next.getValue();
				totalValue = new BigDecimal("0");
				if(stockTotalValues!=null){
					for(StockValueDto dto: stockTotalValues){
						if(key.equals(dto.getlIssuerId())){
							totalValue = dto.getTotalValue();
						}
					}
				}
				value.getComputingResult().setNumerator(totalValue);
			}
			this.addRcScanRuleDetail(null, null, issuerMap.values());
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
			
			// 计算汇总值
			StockValuePage page = new StockValuePage();
			page.setScanDatabaseDate(scanDatabaseDate);
			page.setSummaryType(summaryType);
			page.setNumeratorType(numeratorType);
			page.setHisAddInnercodeSql(addInnercodeSql);
			page.setHisSubInnercodeSql(subInnercodeSql);
			page.setProductList(productList);
			List<StockValueDto> stockTotalValues = o32ScanStockService.selectFundStockTotalValues(page);
			
			// 计算汇总值
			BigDecimal totalValue = new BigDecimal("0");
			if(stockTotalValues != null){
				for(StockValueDto dto: stockTotalValues){
					if(dto.getTotalValue() != null){
						totalValue = dto.getTotalValue();
					}
				}
			}
			// 一条RcScanRuleDetail记录
			detail = new RcScanRuleDetail();
			computingResult = new ComputingResult();
			computingResult.setNumerator(totalValue);
			detail.setComputingResult(computingResult);
			this.addRcScanRuleDetail(null, detail, null);
		}
	}

	// 获取当前指标查询的分子
	private BigDecimal getNumeratorValue(StockValueDto stockValue) {
		// 分子控制类型：0数量amount 1市值market value 2成本cost 3全价市值Full
		BigDecimal value = new BigDecimal("0");
		if (ParamConfig.NUMERATOR_TYPE_AMOUNT.equals(summaryType)) {
			return stockValue.getlAmount();
		} else if (ParamConfig.NUMERATOR_TYPE_MARKET.equals(summaryType)) {
			return stockValue.getEnAllValue();
		} else if (ParamConfig.NUMERATOR_TYPE_COST.equals(summaryType)) {
			return stockValue.getEnCost();
		} else if (ParamConfig.NUMERATOR_TYPE_FULL_MARKET.equals(summaryType)) {
			return stockValue.getEnAllValue();
		}
		return value;
	}

	/**
	 * 分子/分母的商与设置的阀值比较，校验是否达到触警阀值
	 * 
	 * @param ruleId
	 * @param fundId
	 * @param result
	 *            分子/分母的商
	 * @param operator
	 *            控制方向
	 * @param restraintField
	 *            保存计算结果model
	 * @return
	 */
	private boolean compareThreshold(RcRuleThreshold threshold, String rcType, BigDecimal result, String operator,
			ComputingResult computingResult) {
		boolean isTrig = false;
		// 1预警 2禁止 3申请指令审批,先计算禁止的阀值，然后申批，预警次之
		isTrig = this.resultCheckTrig(rcType, ParamConfig.TRIGGER_OPERATION_FORBID, threshold, result, operator,
				computingResult);
		if (!isTrig) {
			isTrig = this.resultCheckTrig(rcType, ParamConfig.TRIGGER_OPERATION_GRANT, threshold, result, operator,
					computingResult);
		}
		if (!isTrig) {
			isTrig = this.resultCheckTrig(rcType, ParamConfig.TRIGGER_OPERATION_WARNNING, threshold, result, operator,
					computingResult);
		}
		return isTrig;
	}

	/**
	 * 分别校验阀值1、阀值2、阀值3 是否触警
	 * 
	 * @param warnningActionCheck
	 *            当前进行计算的预警类型
	 * @param threshold
	 *            触警阀值
	 * @param result
	 *            分子除以分母的结果值
	 * @param operator
	 *            计算方向
	 * @param computingResult
	 *            触警参数Model，记录触警的各项参数
	 * @return
	 */
	private boolean resultCheckTrig(String rcType, String warnningActionCheck, RcRuleThreshold threshold,
			BigDecimal result, String operator, ComputingResult computingResult) {
		// 1预警 2禁止 3申请指令审批
		String warnningAction1 = threshold.getWarnningAction1();
		String warnningAction2 = threshold.getWarnningAction2();
		String warnningAction3 = threshold.getWarnningAction3();
		String threshold1 = threshold.getThreshold1();
		String threshold2 = threshold.getThreshold2();
		String threshold3 = threshold.getThreshold3();
		boolean isTrig = false;
		if (!StringUtils.isEmpty(threshold1)) {
			isTrig = this.isTriggerValue(rcType, warnningActionCheck, warnningAction1, result, operator, threshold1,
					computingResult);
		}
		if (!isTrig && !StringUtils.isEmpty(threshold2)) {
			isTrig = this.isTriggerValue(rcType, warnningActionCheck, warnningAction2, result, operator, threshold2,
					computingResult);
		}
		if (!isTrig && !StringUtils.isEmpty(threshold3)) {
			isTrig = this.isTriggerValue(rcType, warnningActionCheck, warnningAction3, result, operator, threshold3,
					computingResult);
		}
		return isTrig;
	}

	/**
	 * 计算当前阀值是否触警
	 * 
	 * @param warnningActionCheck
	 *            当前进行计算的预警类型
	 * @param warnningAction1
	 *            当前阀值的预警类型
	 * @param result
	 *            分子除以分母的结果值
	 * @param operator
	 *            计算方向
	 * @param threshold1
	 *            当前阀值
	 * @param computingResult
	 *            触警参数Model，记录触警的各项参数
	 * @return
	 */
	private boolean isTriggerValue(String rcType, String warnningActionCheck, String warnningAction1, BigDecimal result,
			String operator, String thresholdTemp, ComputingResult computingResult) {
		int flagValue = 0;
		boolean isTrig = false;
		BigDecimal threshold = null;
		if (warnningActionCheck.equals(warnningAction1)) {
			if (!StringUtils.isEmpty(thresholdTemp)) {
				threshold = new BigDecimal(thresholdTemp);
				if (ParamConfig.RULE_TYPE_RATIO.equals(rcType)) {
					threshold = threshold.divide(new BigDecimal("100"));
				}
				flagValue = result.compareTo(threshold);
				// 控制方向的值(大于/大于等于的加上计算，小于/小于等于的减去计算)：
				// 1--大于、2--大于等于、3--小于、4--小于等于
				if ("1".equals(operator)) {
					isTrig = (flagValue == 1);
				} else if ("2".equals(operator)) {
					isTrig = (flagValue == 1 || flagValue == 0);
				} else if ("3".equals(operator)) {
					isTrig = (flagValue == -1);
				} else if ("4".equals(operator)) {
					isTrig = (flagValue == -1 || flagValue == 0);
				}
			}
		}
		// 记录信息
		if (isTrig) {
			computingResult.setTriggerThreshold(threshold);
			computingResult.setWarnningAction(warnningAction1);
		}
		return isTrig;
	}

	/**
	 * 投资范围类指标查询是否触发风控
	 * 
	 * @param unit
	 */
	private void riskCheck() {
		// 关联性插入记录均为触警记录，不再作判断是否触警操作

	}

	/**
	 * 保存ScanRuleDetail记录
	 * 
	 * @param checkUnitList
	 */
	private void saveScanRuleDetail() {
		String numeratorType = ruleItem.getNumeratorType();

		if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_STOCK.equals(summaryType)) {
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			String prodCode;
			RcScanRule scanRule0;
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				prodCode = entry.getKey();
				scanRule0 = scanRuleMap.get(prodCode);
				for (RcScanRuleDetail detail : entry.getValue()) {
					if (ParamConfig.TRIGGER_OPERATION_NONE.equals(detail.getCheckResult())) {
						// 未触发风控的记录不作保存
						continue;
					}
					String scanSeqId = rcScanRuleDetail0Mapper.getScanSeqId();
					detail.setScanRuleDetailId(scanSeqId);
					detail.setScanRuleId(scanRule0.getScanRuleId());
					detail.setRuleId(ruleItem.getRuleId());
					detail.setRiskAccmulation(0);
					rcScanRuleDetail0Mapper.insertRcScanRuleDetailSelective(detail);
				}
			}
		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)) {
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			String prodCode;
			RcScanRule scanRule1;
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				prodCode = entry.getKey();
				scanRule1 = scanRuleMap.get(prodCode);
				for (RcScanRuleDetail detail : entry.getValue()) {
					if (ParamConfig.TRIGGER_OPERATION_NONE.equals(detail.getCheckResult())) {
						// 未触发风控的记录不作保存
						continue;
					}
					String scanSeqId = rcScanRuleDetail1Mapper.getScanSeqId();
					detail.setScanRuleDetailId(scanSeqId);
					detail.setScanRuleId(scanRule1.getScanRuleId());
					detail.setRuleId(ruleItem.getRuleId());
					detail.setRiskAccmulation(0);
					rcScanRuleDetail1Mapper.insertRcScanRuleDetailSelective(detail);
					// 添加明细
					List<RcScanRuleDetailValue> productStocks = o32ScanStockService.selectProductStocksBySameCompany(
							scanDatabaseDate, numeratorType, detail.getIssuerId(), productList);
					BigDecimal result;
					for (RcScanRuleDetailValue value : productStocks) {
						value.setScanRuleDetailId(detail.getScanRuleDetailId());
						result = new BigDecimal(value.getComputeValue())
								.divide(detail.getComputingResult().getDenominator(), 5, BigDecimal.ROUND_HALF_UP);
						value.setComputeValue(result.toString());
						value.setComupteDate(DateUtil.formatToDayInt(new Date()));
						value.setComputeTime(DateUtil.formatToTimeInt(new Date()));
						rcScanRuleDetail1StockMapper.insertRcScanRuleDetailValueSelective(value);
					}
				}
			}
			
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
			for (RcScanRuleDetail detail : detailList) {
				if (ParamConfig.TRIGGER_OPERATION_NONE.equals(detail.getCheckResult())) {
					// 未触发风控的记录不作保存
					continue;
				}
				String scanSeqId = rcScanRuleDetail3Mapper.getScanSeqId();
				detail.setScanRuleDetailId(scanSeqId);
				detail.setScanRuleId(scanRule.getScanRuleId());
				detail.setRuleId(ruleItem.getRuleId());
				detail.setRiskAccmulation(0);
				rcScanRuleDetail3Mapper.insertRcScanRuleDetailSelective(detail);
				// 添加明细
				BigDecimal result;
				for (RcScanRuleDetailValue value : detail.getScanDetailValues()) {
					value.setScanRuleDetailId(detail.getScanRuleDetailId());
					result = new BigDecimal(value.getComputeValue())
							.divide(detail.getComputingResult().getDenominator(), 5, BigDecimal.ROUND_HALF_UP);
					value.setComputeValue(result.toString());
					value.setComupteDate(DateUtil.formatToDayInt(new Date()));
					value.setComputeTime(DateUtil.formatToTimeInt(new Date()));
					rcScanRuleDetail3ProdMapper.insertRcScanRuleDetailValueSelective(value);
				}
			}
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
			for (RcScanRuleDetail detail : detailList) {
				if (ParamConfig.TRIGGER_OPERATION_NONE.equals(detail.getCheckResult())) {
					// 未触发风控的记录不作保存
					continue;
				}
				String scanSeqId = rcScanRuleDetail4Mapper.getScanSeqId();
				detail.setScanRuleDetailId(scanSeqId);
				detail.setScanRuleId(scanRule.getScanRuleId());
				detail.setRuleId(ruleItem.getRuleId());
				detail.setRiskAccmulation(0);
				rcScanRuleDetail4Mapper.insertRcScanRuleDetailSelective(detail);
				// 添加明细
				List<RcScanRuleDetailValue> productStocks = o32ScanStockService.selectProductStocksBySameCompany(
						scanDatabaseDate, numeratorType, detail.getIssuerId(), productList);
				BigDecimal result;
				ProductModel prod;
				for (RcScanRuleDetailValue value : productStocks) {
					value.setScanRuleDetailId(detail.getScanRuleDetailId());
					// 把productStocks查询出来的基金Code转换为基金Id保存
					prod = fundMap.get(value.getProductionId());
					value.setProductionId(prod.getProductionId());
					value.setProductionName(prod.getProductionName());
					value.setProductionType(prod.getProductionType());
					result = new BigDecimal(value.getComputeValue())
							.divide(detail.getComputingResult().getDenominator(), 5, BigDecimal.ROUND_HALF_UP);
					value.setComputeValue(result.toString());
					value.setComupteDate(DateUtil.formatToDayInt(new Date()));
					value.setComputeTime(DateUtil.formatToTimeInt(new Date()));
					rcScanRuleDetail4StockMapper.insertRcScanRuleDetailValueSelective(value);
				}
			}
			
		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)){
			Set<Entry<String, List<RcScanRuleDetail>>> entrySet = detailMap.entrySet();
			String prodCode;
			RcScanRule scanRule2;
			for(Entry<String, List<RcScanRuleDetail>> entry: entrySet){
				prodCode = entry.getKey();
				scanRule2 = scanRuleMap.get(prodCode);
				for (RcScanRuleDetail detail : entry.getValue()) {
					RcScanRule result = new RcScanRule();
					result.setScanRuleId(scanRule2.getScanRuleId());
					result.setCheckResult(detail.getCheckResult());
					result.setRiskAccmulation(0);
					if (!ParamConfig.TRIGGER_OPERATION_NONE.equals(detail.getCheckResult())) {
						result.setComputeValue(detail.getComputingResult().getResult().toString());
						result.setSetValue(detail.getComputingResult().getTriggerThreshold().toString());
					}
					rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
				}
			}
			
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
			for (RcScanRuleDetail detail : detailList) {
				RcScanRule result = new RcScanRule();
				result.setScanRuleId(scanRule.getScanRuleId());
				result.setCheckResult(detail.getCheckResult());
				result.setRiskAccmulation(0);
				if (!ParamConfig.TRIGGER_OPERATION_NONE.equals(detail.getCheckResult())) {
					result.setComputeValue(detail.getComputingResult().getResult().toString());
					result.setSetValue(detail.getComputingResult().getTriggerThreshold().toString());
				}
				rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
			}
		}
	}
}
