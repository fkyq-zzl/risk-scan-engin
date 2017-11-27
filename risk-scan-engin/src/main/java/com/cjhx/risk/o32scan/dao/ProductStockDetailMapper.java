package com.cjhx.risk.o32scan.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32scan.domain.AllStockInfoHis;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;
import com.cjhx.risk.scan.model.StockValueDto;
import com.cjhx.risk.scan.model.StockValuePage;

public interface ProductStockDetailMapper {

	/**
	 * 查询关联持仓证券集
	 * 
	 * @return
	 */
	List<AllStockInfoHis> selectRelatedProductStocks(@Param("scanDatabaseDate") int scanDatabaseDate,
			@Param("productList") List<ProductModel> productList, @Param("addInnercodeSql") String addInnercodeSql,
			@Param("subInnercodeSql") String subInnercodeSql);

	/**
	 * 投资范围类指标查询关联持仓证券集
	 * 
	 * @return
	 */
	List<AllStockInfoHis> selectRangeRelatedProductStocks(@Param("scanDatabaseDate") int scanDatabaseDate,
			@Param("productList") List<ProductModel> productList,
			@Param("allAddInnercodeSql") String allAddInnercodeSql,
			@Param("allSubInnercodeSql") String allSubInnercodeSql, @Param("addInnercodeSql") String addInnercodeSql,
			@Param("subInnercodeSql") String subInnercodeSql);

	/**
	 * 查询分子关联持仓证券集的证券
	 * 
	 * @param page
	 * @return
	 */
	List<StockValueDto> selectRelatedProductStockValues(StockValuePage page);

	/**
	 * 查询关联持仓证券集的分子汇总计算值
	 * 
	 * @param page
	 * @return
	 */
	List<StockValueDto> selectFundStockTotalValues(StockValuePage page);

	/**
	 * 查询同一公司关联的证券
	 * 
	 * @param page
	 * @return
	 */
	List<RcScanRuleDetailValue> selectProductStocksBySameCompany(StockValuePage page);

}