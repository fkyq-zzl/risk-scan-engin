package com.cjhx.risk.scan.domain;

public class RcScanRuleDetail3 {
    private String scanRuleDetailId;

    private String scanRuleId;

    private String ruleId;

    private String stockId;

    private String stockName;

    private String issuerId;

    private String issuerName;

    private String checkResult;

    private Integer riskAccmulation;

    private String totalValue;

    private String setValue;

    public String getScanRuleDetailId() {
        return scanRuleDetailId;
    }

    public void setScanRuleDetailId(String scanRuleDetailId) {
        this.scanRuleDetailId = scanRuleDetailId == null ? null : scanRuleDetailId.trim();
    }

    public String getScanRuleId() {
        return scanRuleId;
    }

    public void setScanRuleId(String scanRuleId) {
        this.scanRuleId = scanRuleId == null ? null : scanRuleId.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId == null ? null : issuerId.trim();
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName == null ? null : issuerName.trim();
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }

    public Integer getRiskAccmulation() {
        return riskAccmulation;
    }

    public void setRiskAccmulation(Integer riskAccmulation) {
        this.riskAccmulation = riskAccmulation;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue == null ? null : totalValue.trim();
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue == null ? null : setValue.trim();
    }
}