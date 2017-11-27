package com.cjhx.risk.o32scan.domain;

/**
 * @function: 逆回购质押券信息
 * @date: 2017年11月15日 上午8:45:54
 * @author: chencang
 */
public class FmThgmortagage {
	
	//产品
	private String prodName;
	
	//券ID
	private String stockId;
	
	//券名称
	private String stockName;
	
	//质押数量
	private String lMortagageAmount;
	
	//中证/中债
	private String type;
	
	//中证/中债公允价值
	private String netValue;

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getlMortagageAmount() {
		return lMortagageAmount;
	}

	public void setlMortagageAmount(String lMortagageAmount) {
		this.lMortagageAmount = lMortagageAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}
	
	
}

