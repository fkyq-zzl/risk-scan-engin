package com.cjhx.risk.o32prod.domain;

import java.math.BigDecimal;

public class FmTfundinfo {
	private BigDecimal lFundId;

	private String vcFundCode;

	private String vcFundName;

	public BigDecimal getlFundId() {
		return lFundId;
	}

	public void setlFundId(BigDecimal lFundId) {
		this.lFundId = lFundId;
	}

	public String getVcFundCode() {
		return vcFundCode;
	}

	public void setVcFundCode(String vcFundCode) {
		this.vcFundCode = vcFundCode == null ? null : vcFundCode.trim();
	}

	public String getVcFundName() {
		return vcFundName;
	}

	public void setVcFundName(String vcFundName) {
		this.vcFundName = vcFundName == null ? null : vcFundName.trim();
	}

}