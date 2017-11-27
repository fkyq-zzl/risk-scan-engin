package com.cjhx.risk.scan.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32prod.service.O32ProductStockService;
import com.cjhx.risk.scan.dao.RcRuleItemMapper;
import com.cjhx.risk.scan.dao.RcScanMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail0Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail1Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail3Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail4Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleMapper;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcScan;
import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetail;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.scan.service.AfterwardsScanService;
import com.cjhx.risk.util.DateUtil;

/**
 * 事后扫描风控Service实现类
 *
 * @author lujinfu
 * @date 2017年10月17日
 */
@Service("afterwardsScanService")
@Transactional(rollbackFor = Exception.class)
public class AfterwardsScanServiceImpl implements AfterwardsScanService {
	private static Logger logger = LoggerFactory.getLogger(AfterwardsScanServiceImpl.class);

	@Autowired
	private O32ProductStockService o32ProductStockService;

	@Autowired
	private RcRuleItemMapper rcRuleItemMapper;

	@Autowired
	private RcScanMapper rcScanMapper;
	@Autowired
	private RcScanRuleMapper rcScanRuleMapper;
	@Autowired
	private RcScanRuleDetail0Mapper rcScanRuleDetail0Mapper;
	@Autowired
	private RcScanRuleDetail1Mapper rcScanRuleDetail1Mapper;
	@Autowired
	private RcScanRuleDetail3Mapper rcScanRuleDetail3Mapper;
	@Autowired
	private RcScanRuleDetail4Mapper rcScanRuleDetail4Mapper;

	// 加同步锁，避免插入相同的序列 RC_SCAN.SEQUENCE
	// 不需要事务，可集群同步
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public synchronized String startScanLog() {
		RcScan scan = new RcScan();
		String scanSeqId = rcScanMapper.getScanSeqId();
		scan.setScanId(scanSeqId);
		scan.setStartDate(DateUtil.formatToDayInt(new Date()));
		scan.setStartTime(DateUtil.formatToTimeInt(new Date()));
		scan.setStatus("R");// P 等待; R 正在扫描; C 扫描完成; E 扫描出错
		// 序列号:sql查询插入
		scan.setSource("0");// 0日终定时; 1手动触发

		int i = rcScanMapper.insertRcScanSelective(scan);
		if (i < 1) {
			return null;
		}
		return scanSeqId;
	}

	@Override
	public boolean endScanLog(String scanId, String status) {
		RcScan scan = new RcScan();
		scan.setScanId(scanId);
		scan.setEndDate(DateUtil.formatToDayInt(new Date()));
		scan.setEndTime(DateUtil.formatToTimeInt(new Date()));
		// P 等待; R 正在扫描; C 扫描完成; E 扫描出错; N 跳过扫描; F 非交易日
		scan.setStatus(status);
		int i = rcScanMapper.updateRcScanByScanIdSelective(scan);
		if (i < 1) {
			return false;
		}
		return true;
	}

	@Override
	public List<String> selectWaitingScanTask() {
		List<String> scanIds = rcScanMapper.selectWaitingScanTask();
		if (scanIds == null || scanIds.size() < 1) {
			return null;
		}
		return scanIds;
	}

	// 加同步锁，避免插入相同的序列 RC_SCAN.SEQUENCE
	// 不需要事务，可集群同步
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public synchronized boolean updateWaitingScanTask(String scanId) {
		RcScan scan = new RcScan();
		scan.setScanId(scanId);
		scan.setStartDate(DateUtil.formatToDayInt(new Date()));
		scan.setStartTime(DateUtil.formatToTimeInt(new Date()));
		scan.setStatus("R");// P 等待; R 正在扫描; C 扫描完成; E 扫描出错
		// 序列号:sql查询插入
		scan.setSource("1");// 0日终定时; 1手动触发
		int i = rcScanMapper.updateRcScanByScanIdAndWaitingStatusSelective(scan);
		if (i < 1) {
			return false;
		}
		return true;
	}

	@Override
	public Map<RcRuleItem, List<CheckUnit>> scanRule(int scanDatabaseDate,String scanId) {
		// key:指标, value:对应的产品指标集
		Map<RcRuleItem, List<CheckUnit>> scanMap = new HashMap<RcRuleItem, List<CheckUnit>>();

		// 查询日终指标
		List<RcRuleItem> ruleList = rcRuleItemMapper.selectAfterwardsRcRuleItem();
		if (ruleList == null || ruleList.size() < 1) {
			return null;
		}
		// 查询指标产品集
		List<String> ids;
		List<CheckUnit> list;
		CheckUnit unit;
		String summaryType;
		for (RcRuleItem rule : ruleList) {
			list = new ArrayList<CheckUnit>();
			scanMap.put(rule, list);
			// 基金id集
			ids = rcRuleItemMapper.selectFundIdsFromManageAndFundProd(rule.getRuleId());
			summaryType = rule.getSummaryType();
			// 以指标为单位封装CheckUnit
			if (StringUtils.isEmpty(rule.getSummaryType())) {
				// 为空则默认为单基金同一证券
				rule.setSummaryType(ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_STOCK);
			}
			unit = new CheckUnit(rule);
			for (String id : ids) {
				unit.getProductList().add(new ProductModel(id, ParamConfig.HIERARCHY_FUND));
			}
			list.add(unit);
		}

		for (List<CheckUnit> sList : scanMap.values()) {
			// 查询产品Code、产品名称
			o32ProductStockService.setCheckUnitProductionName(sList);
		}
		
		//Map<RcRuleItem, List<CheckUnit>> scanMap
		//过滤信息异常的产品
		Iterator<Entry<RcRuleItem, List<CheckUnit>>> itMap = scanMap.entrySet().iterator();
		Entry<RcRuleItem, List<CheckUnit>> nextMap;
		RcRuleItem key;
		List<CheckUnit> value;
		Iterator<CheckUnit> itList;
		CheckUnit nextList;
		RcRuleItem ruleItemBean;
		List<ProductModel> productList;
		Iterator<ProductModel> itProduct;
		ProductModel productModel;
		while(itMap.hasNext()){
			nextMap = itMap.next();
			key = nextMap.getKey();
			value = nextMap.getValue();
			
			itList = value.iterator();
			while(itList.hasNext()){
				nextList = itList.next();
				ruleItemBean = nextList.getRuleItemBean();
				productList = nextList.getProductList();
				itProduct = productList.iterator();
				while(itProduct.hasNext()){
					productModel = itProduct.next();
					if(productModel.getIsError()){
						this.saveRcScanRuleOfErrorProduct(scanDatabaseDate, scanId, ruleItemBean, productModel);
						itProduct.remove();
					}
				}
				if(productList.size()<1){
					itList.remove();
				}
			}
			if(value.size()<1){
				itMap.remove();
			}
		}
		
		if(scanMap.size()<1){
			return null;
		}
		return scanMap;
	}

	private void saveRcScanRuleOfErrorProduct(int scanDatabaseDate,String scanId,RcRuleItem rule,ProductModel product) {
		Integer startDate = DateUtil.formatToDayInt(new Date());
		Integer startTime = DateUtil.formatToTimeInt(new Date());
		String scanSeqId = rcScanRuleMapper.getScanSeqId();
		
		RcScanRule scanRule = new RcScanRule(rule);
		scanRule.setScanId(scanId);
		scanRule.setScanRuleId(scanSeqId);
		scanRule.setProductionId(product.getProductionId());
		scanRule.setProductionType(product.getProductionType());
		scanRule.setProductionName(product.getProductionName());
		scanRule.setCreateDate(startDate);
		scanRule.setCreateTime(startTime);
		scanRule.setComupteDate(scanDatabaseDate);
		scanRule.setStatus(ParamConfig.STATUS_ERROR);
		scanRule.setErrorLog("查询产品信息为空或产品关联信息缺失");
		rcScanRuleMapper.insertRcScanRuleSelective(scanRule);
	}

	@Override
	public boolean valitateRunningScan(String scanId) {
		boolean flag = false;
		int i = rcScanMapper.validateRunningScan(scanId);
		if (i < 1) {
			flag = true;
		}
		return flag;
	}

	// 处理最后指标触发结果并记录
	@Override
	public void handleScanResult(String scanId) throws ParseException {
		List<RcScanRule> resultList = rcScanRuleMapper.selectComuptedRcScanRuleListByScanId(scanId);
		if (resultList == null || resultList.size() < 1) {
			return;
		}
		String summaryType, scanRuleId;
		for (RcScanRule srule : resultList) {
			summaryType = srule.getSummaryType();
			scanRuleId = srule.getScanRuleId();
			// 查询触警详情，（单基金多基金直接更新ScanRule表记录）
			List<RcScanRuleDetail> scanDetailList = null;
			if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
				scanDetailList = rcScanRuleDetail3Mapper
						.selectTriggerRcScanRuleDetailByScanRuleId(scanRuleId);
			} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
				scanDetailList = rcScanRuleDetail4Mapper
						.selectTriggerRcScanRuleDetailByScanRuleId(scanRuleId);
			} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)) {
				scanDetailList = rcScanRuleDetail1Mapper
						.selectTriggerRcScanRuleDetailByScanRuleId(scanRuleId);
			} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)
					||ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
				RcScanRule result = new RcScanRule();
				result.setScanRuleId(srule.getScanRuleId());
				int accmulation = 0;
				Integer comupteDate, today;
				RcScanRule accmulationModel = null;
				if (ParamConfig.TRIGGER_OPERATION_NONE.equals(srule.getCheckResult())) {
					accmulation = 0;
				} else {
					//从状态正常的rc_scan关联出上一次触警记录，如果有计算异常的记录则递归到上上一次触警记录
					String status;
					int rnum = 1;
					while(true){
						accmulationModel = rcScanRuleMapper.selectRiskAccmulationAndRiskCommentsOfTheDayBefore(
								srule,rnum);
						if(accmulationModel == null){
							accmulation = 1;
							break;
						}else{
							status = accmulationModel.getStatus();
							if("0".equals(status)){
								comupteDate = accmulationModel.getComupteDate();
								today = DateUtil.formatToDayInt(new Date());
								if(comupteDate.intValue() == today.intValue()){
									accmulation = accmulationModel.getRiskAccmulation();
								}else{
									accmulation = accmulationModel.getRiskAccmulation() + 1;
								}
								result.setRiskComments(accmulationModel.getRiskComments());
								break;
							}else{
								rnum += 1;
							}
						}
					}
				}
				result.setRiskAccmulation(accmulation);
				// 获取前一序列号的触警手工批注
//				String comments = rcScanRuleMapper.selectRiskCommentsOfTheDayBefore(srule);		
				rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
				continue;
			} else {
				scanDetailList = rcScanRuleDetail0Mapper
						.selectTriggerRcScanRuleDetailByScanRuleId(scanRuleId);
			}
			
			// 记录主表记录为未触发风控
			if (scanDetailList == null || scanDetailList.size() < 1) {
				this.updateRcScanRuleResult(srule, null);
				continue;
			}
			// 设置连续触发天数
			// int beforeDate;
			int accmulation;
			Integer comupteDate, today;
			RcScanRuleDetail accmulationModel = null;
			RcScanRuleDetail triggerDetail = null;
			for (RcScanRuleDetail detail : scanDetailList) {
				// beforeDate =
				// DateUtil.getTheDayBeforeOfIntDate(detail.getComupteDate());
				// 查询前一序列号记录的触发天数
				//从状态正常的rc_scan关联出上一次触警记录，如果有计算异常的记录则递归到上上一次触警记录
				accmulation = 0;
				String status;
				int rnum = 1;
				while(true){
					if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
						accmulationModel = rcScanRuleDetail3Mapper.selectRiskAccmulationOfTheDayBefore(
								srule.getScanId(), detail.getScanRuleDetailId(),rnum);
					} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
						accmulationModel = rcScanRuleDetail4Mapper.selectRiskAccmulationOfTheDayBefore(
								srule.getScanId(), detail.getScanRuleDetailId(),rnum);
					} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {
						break;
					} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)) {
						accmulationModel = rcScanRuleDetail1Mapper.selectRiskAccmulationOfTheDayBefore(
								srule.getScanId(), detail.getScanRuleDetailId(),rnum);
					} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)) {
						break;
					} else {
						accmulationModel = rcScanRuleDetail0Mapper.selectRiskAccmulationOfTheDayBefore(
								srule.getScanId(), detail.getScanRuleDetailId(),rnum);
					}
					if(accmulationModel == null){
						accmulation = 1;
						break;
					}else{
						status = accmulationModel.getStatus();
						if("0".equals(status)){
							comupteDate = accmulationModel.getComupteDate();
							today = DateUtil.formatToDayInt(new Date());
							if(comupteDate.intValue() == today.intValue()){
								accmulation = accmulationModel.getRiskAccmulation();
							}else{
								accmulation = accmulationModel.getRiskAccmulation() + 1;
							}
							break;
						}else{
							rnum += 1;
						}
					}
				}
				detail.setRiskAccmulation(accmulation);
				// 更新连续触发天数
				this.updateRcScanRuleDetailResult(summaryType, detail);
			}
			triggerDetail = this.handleTriggerDetail(scanDetailList);
			this.updateRcScanRuleResult(srule, triggerDetail);
		}
	}

	// 更新获取最高告警记录处理后的RcScanRule结果
	private void updateRcScanRuleResult(RcScanRule scanRule, RcScanRuleDetail detail) throws ParseException {
		String summaryType = scanRule.getSummaryType();
		RcScanRule result = new RcScanRule();
		result.setScanRuleId(scanRule.getScanRuleId());
		if (detail != null) {
			result.setCheckResult(detail.getCheckResult());
			if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)
					||ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)
					||ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)
					||ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)
					||ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)) {
				result.setComputeValue(detail.getTotalValue());
				result.setRealValue(detail.getTotalValue());
			} else {
				result.setComputeValue(detail.getComputeValue());	
				result.setRealValue(detail.getComputeValue());
			}
			result.setSetValue(detail.getSetValue());
			result.setRiskAccmulation(detail.getRiskAccmulation());
			// 获取前一序列号的触警手工批注
			//从状态正常的rc_scan关联出上一次触警记录，如果有计算异常的记录则递归到上上一次触警记录
			String status;
			RcScanRule commentsModel;
			int rnum = 1;
			while(true){
				commentsModel = rcScanRuleMapper.selectRiskCommentsOfTheDayBefore(scanRule,rnum);
				if(commentsModel == null){
					break;
				}else{
					status = commentsModel.getStatus();
					if("0".equals(status)){
						result.setRiskComments(commentsModel.getRiskComments());
						break;
					}else{
						rnum += 1;
					}
				}
			}
		} else {
			result.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
			result.setRiskAccmulation(0);
		}
		rcScanRuleMapper.updateRcScanRuleByScanRuleIdSelective(result);
	}

	// 更新连续触发天数处理后的RcScanRuleDetail结果
	private void updateRcScanRuleDetailResult(String summaryType, RcScanRuleDetail detail) {
		RcScanRuleDetail result = new RcScanRuleDetail();
		result.setScanRuleDetailId(detail.getScanRuleDetailId());
		result.setRiskAccmulation(detail.getRiskAccmulation());
		if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_STOCK.equals(summaryType)) {
			rcScanRuleDetail3Mapper.updateRcScanRuleDetailByScanRuleDetailIdSelective(result);
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND_SAME_COMPANY.equals(summaryType)) {
			rcScanRuleDetail4Mapper.updateRcScanRuleDetailByScanRuleDetailIdSelective(result);
		} else if (ParamConfig.SUMMARY_TYPE_ALL_FUND.equals(summaryType)) {

		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY.equals(summaryType)) {
			rcScanRuleDetail1Mapper.updateRcScanRuleDetailByScanRuleDetailIdSelective(result);
		} else if (ParamConfig.SUMMARY_TYPE_SINGLE_FUND.equals(summaryType)) {
			
		} else {
			rcScanRuleDetail0Mapper.updateRcScanRuleDetailByScanRuleDetailIdSelective(result);
		}
	}

	// 取出最高级别告警最大触发天数的detail记录
	private RcScanRuleDetail handleTriggerDetail(List<RcScanRuleDetail> scanDetailList) {
		// 取出最高级别的告警
		List<String> checkList = new ArrayList<String>();
		String checkResult;
		for (RcScanRuleDetail detail : scanDetailList) {
			checkResult = detail.getCheckResult();
			checkList.add(checkResult);
		}
		String highTrigger = null;
		if (checkList.contains(ParamConfig.TRIGGER_OPERATION_FORBID)) {
			highTrigger = ParamConfig.TRIGGER_OPERATION_FORBID;
		} else if (checkList.contains(ParamConfig.TRIGGER_OPERATION_GRANT)) {
			highTrigger = ParamConfig.TRIGGER_OPERATION_GRANT;
		} else if (checkList.contains(ParamConfig.TRIGGER_OPERATION_WARNNING)) {
			highTrigger = ParamConfig.TRIGGER_OPERATION_WARNNING;
		}
		// 取出最高告警的最大触发天数的记录
		RcScanRuleDetail trgger = null;
		Integer num = 0, accmulation;
		for (RcScanRuleDetail detail0 : scanDetailList) {
			checkResult = detail0.getCheckResult();
			accmulation = detail0.getRiskAccmulation();
			if (highTrigger.equals(checkResult) && accmulation > num) {
				trgger = detail0;
			}
		}
		return trgger;
	}
}
