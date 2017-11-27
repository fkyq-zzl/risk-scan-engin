package com.cjhx.risk.o32scan.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32scan.domain.ProductStockSum;
import com.cjhx.risk.scan.domain.RcRuleNumerator;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RuleDenominatorDto;
import com.cjhx.risk.scan.model.StockValueDto;

public interface ProductStockSumMapper {

	/**
	 * 查询分母的总值（各项累加）：每产品每项取最后更新日期l_date的记录
	 * 
	 * @return
	 */
	List<StockValueDto> selectDenominatorSumValue(@Param("scanDatabaseDate") int scanDatabaseDate,
			@Param("summaryType") String summaryType,
			@Param("productList") List<ProductModel> productList,
			@Param("denominatorItems") List<RuleDenominatorDto> denominatorItems);


	/**
	 * 查询特殊分子的总值（各项累加）：每产品每项取最后更新日期l_date的记录
	 * 
	 * @return
	 */
	List<StockValueDto> selectSpecialMoleculeSumValue(@Param("scanDatabaseDate") int scanDatabaseDate,
			@Param("summaryType") String summaryType,
			@Param("productList") List<ProductModel> productList,
			@Param("numeratorList") List<RcRuleNumerator> numeratorList);

}