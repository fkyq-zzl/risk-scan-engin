package com.cjhx.risk.scan.riskcheck.impl.algorithm.special;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32scan.dao.AllStockInfoHisMapper;
import com.cjhx.risk.o32scan.dao.FmThgmortagageMapper;
import com.cjhx.risk.o32scan.domain.FmThgInfo;
import com.cjhx.risk.o32scan.domain.FmThgmortagage;
import com.cjhx.risk.scan.dao.RcRuleItemMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail0Mapper;
import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetail;
import com.cjhx.risk.scan.riskcheck.CheckUnit;
import com.cjhx.risk.system.SpringUtil;
import com.cjhx.risk.util.DateUtil;

/**
 * @function: 回购抵押品限制特殊类风控计算法则
 * @date: 2017年11月16日 下午2:42:15
 * @author: chencang
 */
public class BuybackCollateralRestrictio {

	private static Logger logger = LoggerFactory.getLogger(BuybackCollateralRestrictio.class);
	
	private Integer date;
	
	private RcRuleItem ruleItem;
	private List<ProductModel> products;
	
	private RcRuleItemMapper rcRuleItemMapper = (RcRuleItemMapper) SpringUtil.getBean("rcRuleItemMapper");
	private AllStockInfoHisMapper allStockInfoHisMapper = (AllStockInfoHisMapper) SpringUtil.getBean("allStockInfoHisMapper");
	private static RcScanRuleDetail0Mapper rcScanRuleDetail0Mapper = (RcScanRuleDetail0Mapper) SpringUtil.getBean("rcScanRuleDetail0Mapper");
	private static FmThgmortagageMapper fmThgmortagageMapper = (FmThgmortagageMapper) SpringUtil.getBean("fmThgmortagageMapper");
	
	public void rickCheck(Integer date,String scanId, CheckUnit checkUnit,List<RcScanRule> scanRules){
		this.date = date;
		this.ruleItem = checkUnit.getRuleItemBean();
		this.products = checkUnit.getProductList();
		
		//拿到指标id,基金ID,基金名称
		String ruleId = this.ruleItem.getRuleId();
		
		//没有选动态维度,干掉
		List<String> dimList = rcRuleItemMapper.selectDimsByRuleId(ruleId);
		if (null == dimList || dimList.isEmpty()){
			return;
		}
		
		//根据动态维度,得到相应的证券内码
		List<String> dimVcInterCodes = allStockInfoHisMapper.selectDimInterCodes(this.getVcInterCodeByDimSql(dimList));

		//查询交易对手类型
		List<String> tradeRivalIds = rcRuleItemMapper.selectTradeRivalId(ruleId);
		
		//拿到所有的产品ID
		List<String> fundIds = new ArrayList<String>();
		for (ProductModel prod : this.products){
			if (!fundIds.contains(prod.getProductionId())){
				fundIds.add(prod.getProductionId());
			}
		}
		
		List<FmThgInfo> thgs = allStockInfoHisMapper.selectBuyBackInterCodes(fundIds,date,tradeRivalIds);
		Map<String,List<FmThgInfo>> map = getFundThgMap(thgs);
		
		//循环scanRules,有错塞错
		String tmpFundId;
		List<FmThgInfo> tmpThgList;
		for (RcScanRule rsr : scanRules){
			tmpFundId = rsr.getProductionId();
			tmpThgList = map.get(tmpFundId);
			try {
				this.compute(rsr,tmpThgList,dimVcInterCodes);
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
	
	public void compute(RcScanRule scanRule,List<FmThgInfo> thgList,List<String> dimVcInterCodes){
		if (null != thgList && !thgList.isEmpty()){
			for (FmThgInfo thg : thgList){
				String vcInterCodes = thg.getVcInterCodes();
				
				RcScanRuleDetail detail = new RcScanRuleDetail(scanRule);
				detail.setRuleId(ruleItem.getRuleId());
				detail.setStockId(thg.getlSerialNo());
				detail.setStockName(thg.getVcInterCode());
				
				//如果动态维度sql查不出内码,则默认所有的记录都合规
				if (null == dimVcInterCodes || dimVcInterCodes.isEmpty()){
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					detail.setRemark(ParamConfig.DIM_VCINTERCODE_NULL);
					this.saveScanRuleDetail(detail);
					continue;
				}
				
				//如果当前回购抵押品为空,则默认也合规
				if (StringUtils.isEmpty(vcInterCodes)){
					detail.setCheckResult(ParamConfig.TRIGGER_OPERATION_NONE);
					detail.setRemark(ParamConfig.BUYBACK_VCINTERCODE_NULL);
					this.saveScanRuleDetail(detail);
					continue;
				}
				
				//存放不在自定义维度里面的内码
				List<String> outerCodes = new ArrayList<String>();
				
				//基金查出来的内码
				String[] codes = vcInterCodes.split(",");
				for (String code : codes){
					if (!dimVcInterCodes.contains(code)){
						outerCodes.add(code);
					}
				}
				
				//有不在自定义维度里面的内码,则触发风控
				boolean flag = false;
				if (!outerCodes.isEmpty()){
					flag = true;
					
					//添加详情
					detail.setRemark(this.getThgmortagages(scanRule.getProductionName(), thg.getlSerialNo(), outerCodes));
				}
				
				detail.setCheckResult(flag ? ParamConfig.TRIGGER_OPERATION_FORBID : ParamConfig.TRIGGER_OPERATION_NONE);
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
	private String getThgmortagages(String productName,String lSerialNo,List<String> vcInterCodes){
		List<FmThgmortagage> details = fmThgmortagageMapper.selectBuyBackDetails(lSerialNo);
		if (null != details && !details.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for (FmThgmortagage tag : details){
				if (vcInterCodes.contains(tag.getStockId())){
					sb.append("产品").append(productName).append(",");
					sb.append("券ID").append(tag.getStockId()).append(",");
					sb.append("券名称").append(tag.getStockName()).append(",");
					sb.append("质押数量").append(tag.getlMortagageAmount()).append("</br>");
				}
			}
			return sb.toString();
		}
		return "";
	}
	
	
	/**
	 * @author: chencang
	 * @description: 根据动态维度给的sql,查全品种表中的证券内码
	 * @createTime: 2017年11月16日 下午6:13:37
	 * @param dimSqls
	 * @return
	 */
	public String getVcInterCodeByDimSql(List<String> dimSqls){
		StringBuilder strb = new StringBuilder(" select asi.VC_INTER_CODE_O32 from ALL_STOCK_INFO_HIS asi where ( 1=0 ");
		if (dimSqls != null && dimSqls.size() > 0) {
			strb.append(" or(1=0 ");
			for (String sql : dimSqls) {
				//rangeItemLevel2 = rangeItemLevel2.replaceAll("(?i)VW_ALL_STOCK_INFO", "ALL_STOCK_INFO_HIS");
				strb.append(" or exists( select 'X' from (");
				strb.append(sql);
				strb.append(" AND L_DATE = '" + date + "' ");
				strb.append(")tem where tem.VC_STOCK_CODE = asi.VC_STOCK_CODE ");
				strb.append(" and tem.C_MARKET_NO_2 = asi.C_MARKET_NO_2 ");
				strb.append(" and tem.VC_STOCK_TYPE = asi.VC_STOCK_TYPE ");
				strb.append(" ) ");
			}
			strb.append(" ) ) AND L_DATE = '" + date + "' ");
		}
		
		String sbSql = strb.toString();
		//替换全品种表名ALL_STOCK_INFO_HIS
		sbSql = sbSql.replaceAll("VW_ALL_STOCK_INFO", "ALL_STOCK_INFO_HIS");
		return sbSql;
	}
	
	
}
