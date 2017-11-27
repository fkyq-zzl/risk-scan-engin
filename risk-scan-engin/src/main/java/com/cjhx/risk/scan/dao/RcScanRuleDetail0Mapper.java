package com.cjhx.risk.scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32scan.domain.AllStockInfoHis;
import com.cjhx.risk.scan.domain.RcScanRuleDetail0;
import com.cjhx.risk.scan.model.RcScanRuleDetail;

public interface RcScanRuleDetail0Mapper {

	/*
	 * 获取当前表序列ID
	 */
	String getScanSeqId();

	int deleteByPrimaryKey(String scanRuleDetailId);

	int insert(RcScanRuleDetail0 record);

	int insertSelective(RcScanRuleDetail0 record);

	/**
	 * 添加单基金同一证券的产品证券信息
	 * 
	 * @param record
	 * @return
	 */
	int insertRcScanRuleDetailSelective(RcScanRuleDetail detail);

	RcScanRuleDetail selectByPrimaryKey(String scanRuleDetailId);

	/**
	 * 查询触发风控的记录处理连续触发天数并提取最高告警到RcScanRule表
	 * 
	 * @param scanRuleId
	 * @return
	 */
	List<RcScanRuleDetail> selectTriggerRcScanRuleDetailByScanRuleId(String scanRuleId);

	int updateByPrimaryKeySelective(RcScanRuleDetail0 record);

	int updateByPrimaryKey(RcScanRuleDetail0 record);

	/**
	 * 更新记录
	 * 
	 * @param detail
	 * @return
	 */
	int updateRcScanRuleDetailByScanRuleDetailIdSelective(RcScanRuleDetail detail);

	/**
	 * 投资范围类更新不在可投资范围的证券为触发禁止 TODO 不引用
	 * 
	 * @return
	 */
	int updateInvestRangeTriggerStocks(@Param("scanRuleId") String scanRuleId,
			@Param("productStocks") List<AllStockInfoHis> productStocks, @Param("comupteDate") Integer comupteDate,
			@Param("computeTime") Integer computeTime);

	/**
	 * 单基金同一证券互斥
	 * 
	 * @return
	 */
	int excludeSingleFundSameStockByMutex(@Param("scanId") String scanId);

	/**
	 * 单基金同一证券联合
	 * 
	 * @return
	 */
	int excludeSingleFundSameStockByUnion(@Param("scanId") String scanId);

	/**
	 * 查询前一天触发风控的触发天数
	 * 
	 * @return
	 */
	RcScanRuleDetail selectRiskAccmulationOfTheDayBefore(@Param("scanId") String scanId,
			@Param("scanRuleDetailId") String canRuleDetailId, @Param("rowNum") int rowNum);
	
}