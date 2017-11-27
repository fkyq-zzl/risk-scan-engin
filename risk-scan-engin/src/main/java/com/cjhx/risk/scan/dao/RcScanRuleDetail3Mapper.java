package com.cjhx.risk.scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.scan.domain.RcScanRuleDetail3;
import com.cjhx.risk.scan.model.RcScanRuleDetail;

public interface RcScanRuleDetail3Mapper {
	
	/*
	 * 获取当前表序列ID
	 */
	String getScanSeqId();
	
    int deleteByPrimaryKey(String scanRuleDetailId);

    int insert(RcScanRuleDetail3 record);

    int insertSelective(RcScanRuleDetail3 record);
    
	/**
	 * 添加所有基金同一证券的产品证券信息
	 * 
	 * @param record
	 * @return
	 */
	int insertRcScanRuleDetailSelective(RcScanRuleDetail detail);
	
	RcScanRuleDetail selectByPrimaryKey(String scanRuleDetailId);

	/**
	 * 更新记录
	 * 
	 * @param detail
	 * @return
	 */
	int updateRcScanRuleDetailByScanRuleDetailIdSelective(RcScanRuleDetail detail);
	
    int updateByPrimaryKeySelective(RcScanRuleDetail3 record);

    int updateByPrimaryKey(RcScanRuleDetail3 record);
    
	/**
	 * 所有基金同一证券互斥
	 * 
	 * @return
	 */
	int excludeAllFundSameStockByMutex(@Param("scanId") String scanId);

	/**
	 * 所有基金同一证券联合
	 * 
	 * @return
	 */
	int excludeAllFundSameStockByUnion(@Param("scanId") String scanId);
	
	/**
	 * 查询触发风控的记录处理连续触发天数并提取最高告警到RcScanRule表
	 * 
	 * @param scanRuleId
	 * @return
	 */
	List<RcScanRuleDetail> selectTriggerRcScanRuleDetailByScanRuleId(String scanRuleId);
	
	/**
	 * 查询前一天触发风控的触发天数
	 * 
	 * @return
	 */
	RcScanRuleDetail selectRiskAccmulationOfTheDayBefore(@Param("scanId") String scanId,
			@Param("scanRuleDetailId") String canRuleDetailId, @Param("rowNum") int rowNum);
	
}