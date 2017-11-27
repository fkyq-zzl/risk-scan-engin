package com.cjhx.risk.o32scan.service;

import java.math.BigDecimal;
import java.util.List;

import com.cjhx.risk.o32scan.domain.AllStockInfoHis;
import com.cjhx.risk.scan.domain.RcRuleNumerator;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;
import com.cjhx.risk.scan.model.RuleDenominatorDto;
import com.cjhx.risk.scan.model.StockValueDto;
import com.cjhx.risk.scan.model.StockValuePage;

public interface O32ScanStockService {

	/**
	 * 查询相关联的持仓证券集信息
	 * @param fundCode 基金编码
	 * @param addInnercodeSql 加上的HIS表sql
	 * @param subInnercodeSql 减去的HIS表sql
	 * @return
	 */
	List<AllStockInfoHis> selectRelatedProductStocks(int scanDatabaseDate, List<ProductModel> productList, String addInnercodeSql, String subInnercodeSql);

	/**
	 * 查询分子关联持仓证券集的证券
	 * @param productList
	 * @param addInnercodeSql
	 * @param subInnercodeSql
	 * @return
	 */
	List<StockValueDto> selectRelatedProductStockValues(int scanDatabaseDate, List<ProductModel> productList, String addInnercodeSql,
			String subInnercodeSql);

	/**
	 * 统计分母
	 * @param productList
	 * @param denominatorItems
	 * @return
	 */
	List<StockValueDto> selectDenominatorSumValue(int scanDatabaseDate, String numeratorType, List<ProductModel> productList, List<RuleDenominatorDto> denominatorItems);

	/**
	 * 统计分子为数量的单个证券或同一公司的分母（总资产、流通股本）
	 * @param denominatorItems
	 * @param interCode
	 * @param issuerId
	 * @return
	 */
	BigDecimal selectStockTotalSum(int scanDatabaseDate,List<RuleDenominatorDto> denominatorItems, String interCode,
			String issuerId);

	/**
	 * 查询关联持仓证券集的分子汇总计算值
	 * @param page
	 * @return
	 */
	List<StockValueDto> selectFundStockTotalValues(StockValuePage page);

	/**
	 * 查询单基金同一公司关联的证券
	 * 
	 * @param numeratorType
	 * @param issuerId
	 * @param productList
	 * @return
	 */
	List<RcScanRuleDetailValue> selectProductStocksBySameCompany(int scanDatabaseDate, String numeratorType, String issuerId,
			List<ProductModel> productList);

	/**
	 * 查询特殊分子的总值（各项累加）
	 * 
	 * @param productList
	 * @param numeratorList
	 * @return
	 */
	List<StockValueDto> selectSpecialMoleculeSumValue(int scanDatabaseDate, String summaryType, List<ProductModel> productList, List<RcRuleNumerator> numeratorList);

	/**
	 * 过去10交易日平均份额
	 * @param addInnercodeSql
	 * @param subInnercodeSql
	 * @return
	 */
	BigDecimal selectEnTotalStocksAvg10(int scanDatabaseDate,String addInnercodeSql, String subInnercodeSql);

	/**
	 * 投资范围类指标查询关联持仓证券集
	 * @param scanDatabaseDate
	 * @param productList
	 * @param allAddInnercodeSql
	 * @param allSubInnercodeSql
	 * @param addInnercodeSql
	 * @param subInnercodeSql
	 * @return
	 */
	List<AllStockInfoHis> selectRangeRelatedProductStocks(int scanDatabaseDate, List<ProductModel> productList,
			String allAddInnercodeSql, String allSubInnercodeSql, String addInnercodeSql, String subInnercodeSql);

}
