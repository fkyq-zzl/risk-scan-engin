package com.cjhx.risk.o32scan.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhx.risk.o32scan.dao.AllStockInfoHisMapper;
import com.cjhx.risk.o32scan.dao.ProductStockDetailMapper;
import com.cjhx.risk.o32scan.dao.ProductStockSumMapper;
import com.cjhx.risk.o32scan.domain.AllStockInfoHis;
import com.cjhx.risk.o32scan.service.O32ScanStockService;
import com.cjhx.risk.scan.domain.RcRuleNumerator;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;
import com.cjhx.risk.scan.model.RuleDenominatorDto;
import com.cjhx.risk.scan.model.StockValueDto;
import com.cjhx.risk.scan.model.StockValuePage;

/**
 * 查询事后持仓表记录Service实现类
 *
 * @author lujinfu
 * @date 2017年10月23日
 */
@Service("o32ScanStockService")
public class O32ScanStockServiceImpl implements O32ScanStockService {

	@Autowired
	private AllStockInfoHisMapper allStockInfoHisMapper;

	@Autowired
	private ProductStockDetailMapper productStockDetailMapper;

	@Autowired
	private ProductStockSumMapper productStockSumMapper;

	@Override
	public List<AllStockInfoHis> selectRelatedProductStocks(int scanDatabaseDate, List<ProductModel> productList,
			String addInnercodeSql, String subInnercodeSql) {
		return productStockDetailMapper.selectRelatedProductStocks(scanDatabaseDate, productList, addInnercodeSql,
				subInnercodeSql);
	}

	@Override
	public List<AllStockInfoHis> selectRangeRelatedProductStocks(int scanDatabaseDate, List<ProductModel> productList,
			String allAddInnercodeSql, String allSubInnercodeSql, String addInnercodeSql, String subInnercodeSql) {
		return productStockDetailMapper.selectRangeRelatedProductStocks(scanDatabaseDate, productList,
				allAddInnercodeSql, allSubInnercodeSql, addInnercodeSql, subInnercodeSql);
	}

	@Override
	public List<StockValueDto> selectDenominatorSumValue(int scanDatabaseDate, String summaryType, List<ProductModel> productList,
			List<RuleDenominatorDto> denominatorItems) {
		return productStockSumMapper.selectDenominatorSumValue(scanDatabaseDate, summaryType, productList, denominatorItems);
	}

	@Override
	public List<StockValueDto> selectSpecialMoleculeSumValue(int scanDatabaseDate, String summaryType, List<ProductModel> productList,
			List<RcRuleNumerator> numeratorList) {
		return productStockSumMapper.selectSpecialMoleculeSumValue(scanDatabaseDate, summaryType, productList, numeratorList);
	}

	@Override
	public BigDecimal selectEnTotalStocksAvg10(int scanDatabaseDate,String addInnercodeSql, String subInnercodeSql) {
		return allStockInfoHisMapper.selectEnTotalStocksAvg10(scanDatabaseDate,addInnercodeSql, subInnercodeSql);
	}

	@Override
	public List<StockValueDto> selectRelatedProductStockValues(int scanDatabaseDate, List<ProductModel> productList,
			String addInnercodeSql, String subInnercodeSql) {
		StockValuePage page = new StockValuePage();
		page.setScanDatabaseDate(scanDatabaseDate);
		page.setProductList(productList);
		page.setHisAddInnercodeSql(addInnercodeSql);
		page.setHisSubInnercodeSql(subInnercodeSql);
		return productStockDetailMapper.selectRelatedProductStockValues(page);
	}

	@Override
	public List<RcScanRuleDetailValue> selectProductStocksBySameCompany(int scanDatabaseDate, String numeratorType,
			String issuerId, List<ProductModel> productList) {
		StockValuePage page = new StockValuePage();
		page.setScanDatabaseDate(scanDatabaseDate);
		page.setNumeratorType(numeratorType);
		page.setIssuerId(issuerId);
		page.setProductList(productList);
		return productStockDetailMapper.selectProductStocksBySameCompany(page);
	}

	@Override
	public List<StockValueDto> selectFundStockTotalValues(StockValuePage page) {
		return productStockDetailMapper.selectFundStockTotalValues(page);
	}

	@Override
	public BigDecimal selectStockTotalSum(int scanDatabaseDate,List<RuleDenominatorDto> denominatorItems, String interCode,
			String issuerId) {
		return allStockInfoHisMapper.selectStockTotalSum(scanDatabaseDate,denominatorItems, interCode, issuerId);
	}
}
