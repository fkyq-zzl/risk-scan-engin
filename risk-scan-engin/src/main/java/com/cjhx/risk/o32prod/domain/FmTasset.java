package com.cjhx.risk.o32prod.domain;

import java.math.BigDecimal;

public class FmTasset {
	private BigDecimal lAssetId;

	private BigDecimal lFundId;

	private String vcAssetName;

	public BigDecimal getlAssetId() {
		return lAssetId;
	}

	public void setlAssetId(BigDecimal lAssetId) {
		this.lAssetId = lAssetId;
	}

	public BigDecimal getlFundId() {
		return lFundId;
	}

	public void setlFundId(BigDecimal lFundId) {
		this.lFundId = lFundId;
	}

	public String getVcAssetName() {
		return vcAssetName;
	}

	public void setVcAssetName(String vcAssetName) {
		this.vcAssetName = vcAssetName == null ? null : vcAssetName.trim();
	}

}