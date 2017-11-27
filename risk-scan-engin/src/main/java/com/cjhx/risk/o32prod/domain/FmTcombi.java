package com.cjhx.risk.o32prod.domain;

import java.math.BigDecimal;

public class FmTcombi {
	private BigDecimal lCombiId;

	private BigDecimal lFundId;

	private String vcCombiNo;

	private String vcCombiName;

	private BigDecimal lAssetId;

	public BigDecimal getlCombiId() {
		return lCombiId;
	}

	public void setlCombiId(BigDecimal lCombiId) {
		this.lCombiId = lCombiId;
	}

	public BigDecimal getlFundId() {
		return lFundId;
	}

	public void setlFundId(BigDecimal lFundId) {
		this.lFundId = lFundId;
	}

	public String getVcCombiNo() {
		return vcCombiNo;
	}

	public void setVcCombiNo(String vcCombiNo) {
		this.vcCombiNo = vcCombiNo == null ? null : vcCombiNo.trim();
	}

	public String getVcCombiName() {
		return vcCombiName;
	}

	public void setVcCombiName(String vcCombiName) {
		this.vcCombiName = vcCombiName == null ? null : vcCombiName.trim();
	}

	public BigDecimal getlAssetId() {
		return lAssetId;
	}

	public void setlAssetId(BigDecimal lAssetId) {
		this.lAssetId = lAssetId;
	}

}