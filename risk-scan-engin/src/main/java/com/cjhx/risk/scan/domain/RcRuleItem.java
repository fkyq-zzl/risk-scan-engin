package com.cjhx.risk.scan.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RcRuleItem {
    private String ruleId;

    private String ruleName;

    private Date startDate;

    private Date endDate;

    private String step;

    private String status;

    private String rcType;

    private String rcLevel;

    private String priority;

    private String numeratorType;

    private String summaryType;

    private String operator;

    private BigDecimal thresholdValue0;

    private BigDecimal thresholdValue1;

    private BigDecimal thresholdValue2;

    private String preLogNoRisk;

    private String preWarnningAction;

    private Date onStartTime;

    private Date onEndTime;

    private BigDecimal onGap;

    private BigDecimal onMaxRemind;

    private String onRemindWay;

    private BigDecimal afterNoRemindDays;

    private String createUser;

    private Date createTime;

    private String rechecked;

    private String modifyUser;

    private Date modifyTime;

    private String remark;

    private String outControlReason;

    private String preTime;
    //0 所有产品同一阀值 1 不同产品不同阀值
    private String isForAllThreshold;

    private String itemStatus;

    private String recheckUser;

    private Date recheckTime;

    //特殊类指标类型
    private String specialType;
    
    //逆回购质押率水平监控:预警值(reverseBuyBackPledgeRateMonitorValue1)
    private String rmv1;	
    
    //逆回购质押率水平监控:审批值(reverseBuyBackPledgeRateMonitorValue2)
    private String rmv2;
    
    //逆回购质押率水平监控:违规值(reverseBuyBackPledgeRateMonitorValue3)
    private String rmv3;
    
	public String getSpecialType() {
		return specialType;
	}

	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}

	public String getRmv1() {
		return rmv1;
	}

	public void setRmv1(String rmv1) {
		this.rmv1 = rmv1;
	}

	public String getRmv2() {
		return rmv2;
	}

	public void setRmv2(String rmv2) {
		this.rmv2 = rmv2;
	}

	public String getRmv3() {
		return rmv3;
	}

	public void setRmv3(String rmv3) {
		this.rmv3 = rmv3;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step == null ? null : step.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRcType() {
        return rcType;
    }

    public void setRcType(String rcType) {
        this.rcType = rcType == null ? null : rcType.trim();
    }

    public String getRcLevel() {
        return rcLevel;
    }

    public void setRcLevel(String rcLevel) {
        this.rcLevel = rcLevel == null ? null : rcLevel.trim();
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    public String getNumeratorType() {
        return numeratorType;
    }

    public void setNumeratorType(String numeratorType) {
        this.numeratorType = numeratorType == null ? null : numeratorType.trim();
    }

    public String getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType == null ? null : summaryType.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public BigDecimal getThresholdValue0() {
        return thresholdValue0;
    }

    public void setThresholdValue0(BigDecimal thresholdValue0) {
        this.thresholdValue0 = thresholdValue0;
    }

    public BigDecimal getThresholdValue1() {
        return thresholdValue1;
    }

    public void setThresholdValue1(BigDecimal thresholdValue1) {
        this.thresholdValue1 = thresholdValue1;
    }

    public BigDecimal getThresholdValue2() {
        return thresholdValue2;
    }

    public void setThresholdValue2(BigDecimal thresholdValue2) {
        this.thresholdValue2 = thresholdValue2;
    }

    public String getPreLogNoRisk() {
        return preLogNoRisk;
    }

    public void setPreLogNoRisk(String preLogNoRisk) {
        this.preLogNoRisk = preLogNoRisk == null ? null : preLogNoRisk.trim();
    }

    public String getPreWarnningAction() {
        return preWarnningAction;
    }

    public void setPreWarnningAction(String preWarnningAction) {
        this.preWarnningAction = preWarnningAction == null ? null : preWarnningAction.trim();
    }

    public Date getOnStartTime() {
        return onStartTime;
    }

    public void setOnStartTime(Date onStartTime) {
        this.onStartTime = onStartTime;
    }

    public Date getOnEndTime() {
        return onEndTime;
    }

    public void setOnEndTime(Date onEndTime) {
        this.onEndTime = onEndTime;
    }

    public BigDecimal getOnGap() {
        return onGap;
    }

    public void setOnGap(BigDecimal onGap) {
        this.onGap = onGap;
    }

    public BigDecimal getOnMaxRemind() {
        return onMaxRemind;
    }

    public void setOnMaxRemind(BigDecimal onMaxRemind) {
        this.onMaxRemind = onMaxRemind;
    }

    public String getOnRemindWay() {
        return onRemindWay;
    }

    public void setOnRemindWay(String onRemindWay) {
        this.onRemindWay = onRemindWay == null ? null : onRemindWay.trim();
    }

    public BigDecimal getAfterNoRemindDays() {
        return afterNoRemindDays;
    }

    public void setAfterNoRemindDays(BigDecimal afterNoRemindDays) {
        this.afterNoRemindDays = afterNoRemindDays;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRechecked() {
        return rechecked;
    }

    public void setRechecked(String rechecked) {
        this.rechecked = rechecked == null ? null : rechecked.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOutControlReason() {
        return outControlReason;
    }

    public void setOutControlReason(String outControlReason) {
        this.outControlReason = outControlReason == null ? null : outControlReason.trim();
    }

    public String getPreTime() {
        return preTime;
    }

    public void setPreTime(String preTime) {
        this.preTime = preTime == null ? null : preTime.trim();
    }

    public String getIsForAllThreshold() {
        return isForAllThreshold;
    }

    public void setIsForAllThreshold(String isForAllThreshold) {
        this.isForAllThreshold = isForAllThreshold == null ? null : isForAllThreshold.trim();
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus == null ? null : itemStatus.trim();
    }

    public String getRecheckUser() {
        return recheckUser;
    }

    public void setRecheckUser(String recheckUser) {
        this.recheckUser = recheckUser == null ? null : recheckUser.trim();
    }

    public Date getRecheckTime() {
        return recheckTime;
    }

    public void setRecheckTime(Date recheckTime) {
        this.recheckTime = recheckTime;
    }
}