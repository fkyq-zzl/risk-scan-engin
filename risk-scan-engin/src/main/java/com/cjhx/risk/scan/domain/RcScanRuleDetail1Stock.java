package com.cjhx.risk.scan.domain;

public class RcScanRuleDetail1Stock {
    private String scanRuleDetailId;

    private String stockId;

    private String stockName;

    private String computeValue;

    private Integer comupteDate;

    private Integer computeTime;

    public String getScanRuleDetailId() {
        return scanRuleDetailId;
    }

    public void setScanRuleDetailId(String scanRuleDetailId) {
        this.scanRuleDetailId = scanRuleDetailId == null ? null : scanRuleDetailId.trim();
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