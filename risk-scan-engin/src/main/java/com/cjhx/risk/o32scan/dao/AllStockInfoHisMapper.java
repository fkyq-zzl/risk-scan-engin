package com.cjhx.risk.o32scan.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32scan.domain.FmThgInfo;
import com.cjhx.risk.scan.model.RuleDenominatorDto;

public interface AllStockInfoHisMapper {
	
	List<String> selectDimInterCodes(@Param("sql") String sql);
	
	List<FmThgInfo> selectBuyBackInterCodes(@Param("fundIds") List<String> fundIds,@Param("date") Integer date,@Param("tradeRivalIds") List<String> tradeRivalIds);
	
	/**
	 * 统计分子为数量的单个证券或同一公司的分母（总资产、流通股本）
	 * @param denominatorItems
	 * @param interCode
	 * @param issuerId
	 * @return
	 */
	BigDecimal selectStockTotalSum(@Param("scanDatabaseDate") int scanDatabaseDate,
			@Param("denominatorItems") List<RuleDenominatorDto> denominatorItems,
			@Param("interCode") String interCode, @Param("issuerId") String issuerId);
	
	/**
	 * 过去10交易日平均份额
	 * @param addInnercodeSql
	 * @param subInnercodeSql
	 * @return
	 */
	BigDecimal selectEnTotalStocksAvg10(@Param("scanDatabaseDate") int scanDatabaseDate,
			@Param("addInnercodeSql") String addInnercodeSql, @Param("subInnercodeSql") String subInnercodeSql);
}