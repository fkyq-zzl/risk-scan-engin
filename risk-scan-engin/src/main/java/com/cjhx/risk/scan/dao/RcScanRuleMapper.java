package com.cjhx.risk.scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.scan.domain.RcScanRule;
import com.cjhx.risk.scan.model.RcScanRuleDetail;

public interface RcScanRuleMapper {
	
	/*
	 * 获取当前表序列ID
	 */
	String getScanSeqId();
	
    int deleteByPrimaryKey(String scanRuleId);

    int insert(RcScanRule record);

    /**
     * 新增RcScanRule表记录
     * @param record
     * @return
     */
    int insertRcScanRuleSelective(RcScanRule record);

    RcScanRule selectByPrimaryKey(String scanRuleId);

    /**
     * 查询计算风控并互斥联合后待处理CheckResult的记录集
     * @param scanId
     * @return
     */
    List<RcScanRule> selectComuptedRcScanRuleListByScanId(String scanId);
    /**
     * 更新表记录
     * @param record
     * @return
     */
    int updateRcScanRuleByScanRuleIdSelective(RcScanRule record);

    int updateByPrimaryKeyWithBLOBs(RcScanRule record);

    int updateByPrimaryKey(RcScanRule record);
    
    /**
     * 查询前一天触发风控的批注日志
     * @return
     */
    RcScanRule selectRiskCommentsOfTheDayBefore(@Param("scanRule")RcScanRule scanRule,@Param("rowNum") int rowNum);
    
    /**
	 * 查询前一天触发风控的触发天数
	 * 
	 * @return
	 */
	RcScanRule selectRiskAccmulationAndRiskCommentsOfTheDayBefore(
			@Param("scanRule")RcScanRule scanRule,@Param("rowNum") int rowNum);
	
}