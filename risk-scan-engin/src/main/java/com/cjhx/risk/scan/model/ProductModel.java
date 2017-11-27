package com.cjhx.risk.scan.model;

/**
 * 指标的产品Model
 *
 * @author lujinfu
 * @date 2017年10月24日
 */
public class ProductModel {
	//是否产品异常，例如关联不上产品信息则异常
	private boolean isError = false;
	
	private String productionId;

	private String productionCode;

	private String productionName;

	private String productionType;

	public ProductModel() {
		super();
	}

	public ProductModel(String productionId, String productionType) {
		this.productionId = productionId;
		this.productionType = productionType;
	}

	public boolean getIsError() {
		return isError;
	}

	public void setIsError(boolean isError) {
		this.isError = isError;
	}

	public String getProductionId() {
		return productionId;
	}

	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}

	public String getProductionCode() {
		return productionCode;
	}

	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}

	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	public String getProductionType() {
		return productionType;
	}

	public void setProductionType(String productionType) {
		this.productionType = productionType;
	}

}
