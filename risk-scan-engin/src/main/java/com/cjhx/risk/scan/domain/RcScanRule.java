package com.cjhx.risk.scan.domain;

import com.cjhx.risk.scan.model.ProductModel;

public class RcScanRule {
	
    private String scanRuleId;

    private String scanId;

    private String ruleId;

    private String ruleName;

    private String productionId;

    private String productionName;

    private String productionType;

    private String checkResult;

    private String summaryType;

    private Integer createDate;

    private Integer createTime;

    private Integer startDate;

    private Integer startTime;

    private Integer endDate;

    private Integer endTime;
    
    private String computeValue;

    private String setValue;

    private String realValue;

    private Integer comupteDate;

    private Integer computeTime;

    private Integer riskAccmulation;

    private String riskComments;

    private String remark;

    private String status;

    private String errorLog;

    public RcScanRule() {
		super();
	}

	public RcScanRule(RcRuleItem rule) {
		super();
		this.ruleId = rule.getRuleId();
		this.ruleName = rule.getRuleName();
		this.summaryType = rule.getSummaryType();
	}
	
	public RcScanRule(RcRuleItem rule, ProductModel product) {
		super();
		this.ruleId = rule.getRuleId();
		this.ruleName = rule.getRuleName();
		this.summaryType = rule.getSummaryType();
		this.productionId = product.getProductionId();
		this.productionName = product.getProductionName();
		this.productionType = product.getProductionType();
	}

	public String getScanRuleId() {
        return scanRuleId;
    }

    public void setScanRuleId(String scanRuleId) {
        this.scanRuleId = scanRuleId == null ? null : scanRuleId.trim();
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId == null ? null : scanId.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
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

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }

    public String getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType == null ? null : summaryType.trim();
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getComputeValue() {
        return computeValue;
    }

    public void setComputeValue(String computeValue) {
        this.computeValue = computeValue == null ? null : computeValue.trim();
    }

    public Integer getStartDate() {
		return startDate;
	}

	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue == null ? null : setValue.trim();
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue == null ? null : realValue.trim();
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

    public Integer getRiskAccmulation() {
        return riskAccmulation;
    }

    public void setRiskAccmulation(Integer riskAccmulation) {
        this.riskAccmulation = riskAccmulation;
    }

    public String getRiskComments() {
        return riskComments;
    }

    public void setRiskComments(String riskComments) {
        this.riskComments = riskComments == null ? null : riskComments.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog == null ? null : errorLog.trim();
    }
}