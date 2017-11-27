package com.cjhx.risk.scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.scan.domain.RcScanRuleDetail4;
import com.cjhx.risk.scan.model.RcScanRuleDetail;

public interface RcScanRuleDetail4Mapper {
	
	/*
	 * 获取当前表序列ID
	 */
	String getScanSeqId();
	
    int deleteByPrimaryKey(String scanRuleDetailId);

    int insert(RcScanRuleDetail4 record);

    int insertSelective(RcScanRuleDetail4 record);

	/**
	 * 添加所有基金同一公司的产品证券信息
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
	
    int updateByPrimaryKeySelective(RcScanRuleDetail4 record);

    int updateByPrimaryKey(RcScanRuleDetail4 record);
    
    
	/**
	 * 所有基金同一公司互斥
	 * 
	 * @return
	 */
	int excludeAllFundSameCompanyByMutex(@Param("scanId") String scanId);

	/**
	 * 所有基金同一公司联合
	 * 
	 * @return
	 */
	int excludeAllFundSameCompanyByUnion(@Param("scanId") String scanId);
	
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