package com.cjhx.risk.o32scan.domain;

/**
 * @function: 回购信息:用于逆回购质押率水平监控计算
 * @date: 2017年11月15日 上午8:56:21
 * @author: chencang
 */
public class FmThgInfo {

	/*
	 * 逆回购序号
	 */
	private String lSerialNo;
	
	/*
	 * 基金ID
	 */
	private String lFundId;
	
	/*
	 * 公允价值:分子, ∑(质押券数量*质押券公允价值)
	 */
	private String fairValue;
	
	/*
	 * 成交金额:分母
	 */
	private String enDealBalance;
	
	/*
	 * 回购编号:质押券表
	 */
	private String vcInterCode;
	
	/*
	 * 所有自定义维度返回的证券内码
	 */
	private String vcInterCodes;
	
	
	
	public String getVcInterCodes() {
		return vcInterCodes;
	}

	public void setVcInterCodes(String vcInterCodes) {
		this.vcInterCodes = vcInterCodes;
	}

	public String getlSerialNo() {
		return lSerialNo;
	}

	public void setlSerialNo(String lSerialNo) {
		this.lSerialNo = lSerialNo;
	}

	public String getlFundId() {
		return lFundId;
	}

	public void setlFundId(String lFundId) {
		this.lFundId = lFundId;
	}

	public String getFairValue() {
		return fairValue;
	}

	public void setFairValue(String fairValue) {
		this.fairValue = fairValue;
	}

	public String getEnDealBalance() {
		return enDealBalance;
	}

	public void setEnDealBalance(String enDealBalance) {
		this.enDealBalance = enDealBalance;
	}

	public String getVcInterCode() {
		return vcInterCode;
	}

	public void setVcInterCode(String vcInterCode) {
		this.vcInterCode = vcInterCode;
	}
	
	
}

