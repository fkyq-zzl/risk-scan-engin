package com.cjhx.risk.scan.model;

import java.math.BigDecimal;
import java.util.List;

import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.riskcheck.CheckUnit;

/**
 * 查询证券信息参数条件Model
 *
 * @author lujinfu
 * @date 2017年11月7日
 */
public class StockValuePage {
	private int scanDatabaseDate;
	private String numeratorType;
	private String summaryType;
	private String interCode;
	private String hisAddInnercodeSql;
	private String hisSubInnercodeSql;
	private String issuerId;
	
	private List<ProductModel> productList;
	// 控制方向的值(大于/大于等于的加上计算，小于/小于等于的减去计算)： 1--大于、2--大于等于、3--小于、4--小于等于
	private String operator;
	// 大于方向的最小阀值或小于方向的最大阀值，过滤出触警的证券
	private BigDecimal threshold;

	public StockValuePage() {
		super();
	}

	public StockValuePage(CheckUnit checkUnit) {
		super();
		RcRuleItem ruleItemBean = checkUnit.getRuleItemBean();
		this.numeratorType = ruleItemBean.getNumeratorType();
	}

	public int getScanDatabaseDate() {
		return scanDatabaseDate;
	}

	public void setScanDatabaseDate(int scanDatabaseDate) {
		this.scanDatabaseDate = scanDatabaseDate;
	}

	public String getSummaryType() {
		return summaryType;
	}

	public void setSummaryType(String summaryType) {
		this.summaryType = summaryType;
	}

	public String getInterCode() {
		return interCode;
	}

	public void setInterCode(String interCode) {
		this.interCode = interCode;
	}

	public String getHisAddInnercodeSql() {
		return hisAddInnercodeSql;
	}

	public void setHisAddInnercodeSql(String hisAddInnercodeSql) {
		this.hisAddInnercodeSql = hisAddInnercodeSql;
	}

	public String getHisSubInnercodeSql() {
		return hisSubInnercodeSql;
	}

	public void setHisSubInnercodeSql(String hisSubInnercodeSql) {
		this.hisSubInnercodeSql = hisSubInnercodeSql;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public List<ProductModel> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductModel> productList) {
		this.productList = productList;
	}

	public String getNumeratorType() {
		return numeratorType;
	}

	public void setNumeratorType(String numeratorType) {
		this.numeratorType = numeratorType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getThreshold() {
		return threshold;
	}

	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}

}
