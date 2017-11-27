package com.cjhx.risk.scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.domain.RcRuleNumerator;
import com.cjhx.risk.scan.domain.RcRuleThreshold;
import com.cjhx.risk.scan.model.RuleDenominatorDto;

public interface RcRuleItemMapper {
	
	List<String> selectTradeRivalId(@Param("ruleId") String ruleId);
	
	/**
	 * @author: chencang
	 * @description: 根据指标ID,查出关联的自定义维度sql
	 * @createTime: 2017年11月16日 下午5:17:45
	 * @param ruleId
	 * @return
	 */
	List<String> selectDimsByRuleId(@Param("ruleId") String ruleId);
	
	/**
	 * 查询事后指标集
	 * @return
	 */
	List<RcRuleItem> selectAfterwardsRcRuleItem();
	
	/**
	 * 根据指标ID查询指标的管理组层或基金层的基金ID集
	 * @param ruleId
	 * @return
	 */
	List<String> selectFundIdsFromManageAndFundProd(String ruleId);
	
	/**
	 * 根据指标ID查询指标的资产单元层的资产单元ID集
	 * @param ruleId
	 * @return
	 */
	List<String> selectAssetsIdsFromAssetsProd(String ruleId);
	
	/**
	 * 根据指标ID查询指标的组合层的组合Code集
	 * @param ruleId
	 * @return
	 */
	List<String> selectCombiCodesFromCombiProd(String ruleId);
	
	/**
	 * 根据字段编码和指标ID查询字段值
	 * 
	 * @param param
	 * @return
	 */
	String selectCharValueByCharCodeAndRuleId(@Param("charCode")String charCode,@Param("ruleId")String ruleId);
	
	/**
	 * 查询scan_quartz_cron定时任务执行表达式
	 * @return
	 */
	String selectScanQuartzCronFromDomainValue();
	
	/**
	 * 查询scan_database_day 事后扫描读取持仓数据的日期偏移天数
	 * @return
	 */
	String selectScanDatabaseDayFromDomainValue();
	
	/**
	 * 查询single_scan_quartz_cron查询手动任务频率表达式
	 * @return
	 */
	String selectSingleScanQuartzCronFromDomainValue();
	
	/**
	 * 查询指标的固定分母
	 * @param ruleId
	 * @return
	 */
	List<RuleDenominatorDto> selectDenominatorItemFromRcRuleDenominator(String ruleId);
	
	/**
	 * 根据基金ID查询限制类指标阀值
	 * 
	 * @param ruleId
	 * @return
	 */
	RcRuleThreshold selectRcRuleThresholdByFundId(@Param("ruleId")String ruleId,
			@Param("summaryType")String summaryType, @Param("fundId")String fundId);

	/**
	 * 查询指标的分子投资范围记录（特殊分子）
	 * @param ruleId
	 * @return
	 */
	List<RcRuleNumerator> selectNumeratorItemFromRcRuleNumerator(String ruleId);
	
	
    RcRuleItem selectByPrimaryKey(String ruleId);

}