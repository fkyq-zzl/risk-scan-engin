package com.cjhx.risk.scan.model;

import java.math.BigDecimal;

/**
 * 查询证券信息结果Model
 *
 * @author lujinfu
 * @date 2017年11月7日
 */
public class StockValueDto {

	private String vcProdCode;
	private String vcInterCodeO32;
	private String vcStockName;
	private String lIssuerId;
	private String vcIssuerName;

	// 数量 L_AMOUNT
	private BigDecimal lAmount;
	// 市值 EN_NET_VALUE
	private BigDecimal enNetValue;
	// 全价市值 EN_ALL_VALUE
	private BigDecimal enAllValue;
	// 成本 EN_COST
	private BigDecimal enCost;

	// 汇总值
	private BigDecimal totalValue;
	
	public String getVcProdCode() {
		return vcProdCode;
	}

	public void setVcProdCode(String vcProdCode) {
		this.vcProdCode = vcProdCode;
	}

	public String getVcInterCodeO32() {
		return vcInterCodeO32;
	}

	public void setVcInterCodeO32(String vcInterCodeO32) {
		this.vcInterCodeO32 = vcInterCodeO32;
	}

	public String getVcStockName() {
		return vcStockName;
	}

	public void setVcStockName(String vcStockName) {
		this.vcStockName = vcStockName;
	}

	public String getlIssuerId() {
		return lIssuerId;
	}

	public void setlIssuerId(String lIssuerId) {
		this.lIssuerId = lIssuerId;
	}

	public String getVcIssuerName() {
		return vcIssuerName;
	}

	public void setVcIssuerName(String vcIssuerName) {
		this.vcIssuerName = vcIssuerName;
	}

	public BigDecimal getlAmount() {
		return lAmount;
	}

	public void setlAmount(BigDecimal lAmount) {
		this.lAmount = lAmount;
	}

	public BigDecimal getEnNetValue() {
		return enNetValue;
	}

	public void setEnNetValue(BigDecimal enNetValue) {
		this.enNetValue = enNetValue;
	}

	public BigDecimal getEnAllValue() {
		return enAllValue;
	}

	public void setEnAllValue(BigDecimal enAllValue) {
		this.enAllValue = enAllValue;
	}

	public BigDecimal getEnCost() {
		return enCost;
	}

	public void setEnCost(BigDecimal enCost) {
		this.enCost = enCost;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

}
