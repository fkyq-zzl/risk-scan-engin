package com.cjhx.risk.scan.domain;

public class RcScanRuleDetail3Prod {
    private String scanRuleDetailId;

    private String productionId;

    private String productionName;

    private String productionType;

    private String computeValue;

    private Integer comupteDate;

    private Integer computeTime;

    public String getScanRuleDetailId() {
        return scanRuleDetailId;
    }

    public void setScanRuleDetailId(String scanRuleDetailId) {
        this.scanRuleDetailId = scanRuleDetailId == null ? null : scanRuleDetailId.trim();
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

    public String getComputeValue() {
        return computeValue;
    }

    public void setComputeValue(String computeValue) {
        this.computeValue = computeValue == null ? null : computeValue.trim();
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