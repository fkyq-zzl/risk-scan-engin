package com.cjhx.risk.scan.riskcheck.impl.algorithm.special;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32scan.dao.FmThgmortagageMapper;
import com.cjhx.risk.o32scan.dao.FmThgregisterMapper;
import com.cjhx.risk.o32scan.domain.FmThgInfo;
import com.cjhx.risk.o32scan.domain.FmThgmortagage;
import com.cjhx.risk.scan.dao.RcScanRuleDetail0Mapper;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetail;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.system.SpringUtil;
import com.cjhx.risk.util.DateUtil;

/**
 * @function: 逆回购质押率水平监控特殊类风控计算法则
 * @date: 2017年11月16日 上午10:13:43
 * @author: chencang
 */
public class ReverseBuybackPledgeRateMonitor {
	
	private static Logger logger = LoggerFactory.getLogger(ReverseBuybackPledgeRateMonitor.class);
	
	private boolean isTrigger = false;
	private Integer date;
	
	private RcRuleItem ruleItem;
	private List<ProductModel> products;
	
	private static RcScanRuleDetail0Mapper rcScanRuleDetail0Mapper = (RcScanRuleDetail0Mapper) SpringUtil.getBean("rcScanRuleDetail0Mapper");
	private static FmThgregisterMapper fmThgregisterMapper = (FmThgregisterMapper) SpringUtil.getBean("fmThgregisterMapper");
	private static FmThgmortagageMapper fmThgmortagageMapper = (FmThgmortagageMapper) SpringUtil.getBean("fmThgmortagageMapper");
	
	/**
	 * @author: chencang
	 * @description: 逆回购质押率水平监控
	 * @createTime: 2017年11月16日 上午10:38:32
	 * @param date 检查日期
	 * @param scanId  扫描ID
	 * @param checkUnit  检查单元
	 * @param scanRule  汇总记录
	 */
	public void rickCheck(Integer date,String scanId, CheckUnit checkUnit,List<RcScanRule> scanRules){
		this.date = date;
		this.ruleItem = checkUnit.getRuleItemBean();
		this.products = checkUnit.getProductList();
		
		List<String> fundIds = new ArrayList<String>();
		for (ProductModel prod : this.products){
			if (!fundIds.contains(prod.getProductionId())){
				fundIds.add(prod.getProductionId());
			}
		}
		
		//查询逆回购数据
		List<FmThgInfo> thgList = fmThgregisterMapper.selectThgInfos(fundIds,date);
		Map<String,List<FmThgInfo>> map = getFundThgMap(thgList);
		
		//循环scanRules,有错塞错
		String tmpFundId;
		List<FmThgInfo> tmpThgList;
		for (RcScanRule rsr : scanRules){
			tmpFundId = rsr.getProductionId();
			tmpThgList = map.get(tmpFundId);
			try {
				this.compute(rsr,tmpThgList);
				rsr.setEndDate(DateUtil.formatToDayInt(new Date()));
				rsr.setEndTime(DateUtil.formatToTimeInt(new Date()));
			} catch (Exception e) {
				rsr.setStatus(ParamConfig.STATUS_ERROR);
				rsr.setErrorLog(e.getMessage());
				logger.error("RuleId(" + ruleItem.getRuleId() + ") scanId(" + scanId + ") execute()计算异常：" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void compute(RcScanRule scanRule,List<FmThgInfo> thgList){
		//每个产品,会有多条明细 
		if (null != thgList && !thgList.isEmpty()){
			for (FmThgInfo thg : thgList){
				//每次进来,都默认没有触发风控
				this.isTrigger = false;
				
				//预警值,预警审批和违规值
				String rmv1 = ruleItem.getRmv1();
				String rmv2 = ruleItem.getRmv2();
				String rmv3 = ruleItem.getRmv3();
				
				RcScanRuleDetail detail = new RcScanRuleDetail(scanRule);
				detail.setRuleId(ruleItem.getRuleId());
				detail.setStockId(thg.getlSerialNo());
				detail.setStockName(thg.getVcInterCode());
				
				//默认正常场景: 有预警,审批,违规值为空;没有分子,或者分子为0;没有分母或者分母为0:理论上讲不可能出现
				if ((StringUtils.isEmpty(rmv1) || StringUtils.isEmpty(rmv2) || StringUtils.isEmpty(rmv3))
						|| (null == thg.getFairValue() || "0".equals(thg.getFairValue()))
						|| (null == thg.getEnDealBalance() || "0".equals(thg.getEnDealBalance()))){
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					this.saveScanRuleDetail(detail);
					continue;
				} 
				
				//分子,分母
				BigDecimal numerator = new BigDecimal(thg.getFairValue());
				BigDecimal denominator = new BigDecimal(thg.getEnDealBalance());
				
				//分子分母比
				BigDecimal result = numerator.divide(denominator,5,BigDecimal.ROUND_HALF_UP);
				BigDecimal rmvNumber1 = new BigDecimal(rmv1);  //预警
				BigDecimal rmvNumber2 = new BigDecimal(rmv2);  //审批
				BigDecimal rmvNumber3 = new BigDecimal(rmv3);  //禁止
				
				if (result.compareTo(rmvNumber3) >= 0){
					this.isTrigger = true;
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_FORBID);
					detail.setComputeValue(result + "");
					detail.setSetValue(rmv3);
				} else if (result.compareTo(rmvNumber2) >= 0 ){
					this.isTrigger = true;
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_GRANT);
					detail.setComputeValue(result + "");
					detail.setSetValue(rmv2);
				} else if (result.compareTo(rmvNumber1) >= 0 ){
					this.isTrigger = true;
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_WARNNING);
					detail.setComputeValue(result + "");
					detail.setSetValue(rmv1);
				} else {
					this.isTrigger = false;
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
				}
				
				//如果触发了风控,则添加逆回购质押券明细
				if(this.isTrigger){
					detail.setRemark(this.getThgmortagages(scanRule.getProductionName(),thg.getlSerialNo()));
				}
				
				this.saveScanRuleDetail(detail);
			}
		}
	}
	
	
	/**
	 * @author: chencang
	 * @description:根据fundId,找出对应的list<FmThgInfo>
	 * @createTime: 2017年11月17日 下午6:21:38
	 * @param thgList
	 * @return
	 */
	private Map<String,List<FmThgInfo>> getFundThgMap(List<FmThgInfo> thgList){
		Map<String,List<FmThgInfo>> fundThgMap = new HashMap<>();
		String fundId;
		List<FmThgInfo> curThgs;
		for (FmThgInfo thg : thgList){
			fundId = thg.getlFundId();
			if (fundThgMap.containsKey(fundId)){
				curThgs = fundThgMap.get(fundId);
			} else {
				curThgs = new ArrayList<FmThgInfo>();
			}
			
			curThgs.add(thg);
			fundThgMap.put(fundId, curThgs);
		}
		return fundThgMap;
	}
	
	
	/**
	 * 添加风控触发明细
	 */
	private void saveScanRuleDetail(RcScanRuleDetail detail) {
		String scanSeqId = rcScanRuleDetail0Mapper.getScanSeqId();
		detail.setScanRuleDetailId(scanSeqId);
		detail.setRiskAccmulation(0);  
		detail.setComupteDate(DateUtil.formatToDayInt(new Date()));
		detail.setComputeTime(DateUtil.formatToTimeInt(new Date()));
		rcScanRuleDetail0Mapper.insertRcScanRuleDetailSelective(detail);
	}
	
	
	/**
	 * @author: chencang
	 * @description: 根据流水号查询明细
	 * @createTime: 2017年11月15日 下午5:40:19
	 * @param lSerialNo 在途回购表流水号
	 */
	private String getThgmortagages(String productName,String lSerialNo){
		List<FmThgmortagage> details = fmThgmortagageMapper.selectDetails(lSerialNo,date);
		if (null != details && !details.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for (FmThgmortagage tag : details){
				sb.append("产品").append(productName).append(",");
				sb.append("券ID").append(tag.getStockId()).append(",");
				sb.append("券名称").append(tag.getStockName()).append(",");
				sb.append("质押数量").append(tag.getlMortagageAmount()).append(",");
				sb.append(tag.getType()).append(",");
				sb.append("公允价值 ").append(tag.getNetValue()).append("</br>");
			}
			return sb.toString();
		}
		return "";
	}
	
}
