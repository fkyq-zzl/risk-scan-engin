package com.cjhx.risk.scan.domain;

public class RcScanRuleDetail0 {
    private String scanRuleDetailId;

    private String scanRuleId;

    private String ruleId;

    private String productionId;

    private String productionName;

    private String productionType;

    private String stockId;

    private String stockName;

    private String issuerId;

    private String issuerName;

    private String checkResult;

    private Integer riskAccmulation;

    private String computeValue;

    private String setValue;

    private Integer comupteDate;

    private Integer computeTime;

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

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId == null ? null : productionId.trim();
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName == null ? null : productionName.trim();
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType == null ? null : productionType.trim();
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

    public String getComputeValue() {
        return computeValue;
    }

    public void setComputeValue(String computeValue) {
        this.computeValue = computeValue == null ? null : computeValue.trim();
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue == null ? null : setValue.trim();
    }

    public Integer getComupteDate() {
        return comupteDate;
    }

    public void setComupteDate(Integer comupteDate) {
        this.comupteDate = comupteDate;
    }

    public Integer getComputeTime() {
        return computeTime;
    }

    public void setComputeTime(Integer computeTime) {
        this.computeTime = computeTime;
    }
}