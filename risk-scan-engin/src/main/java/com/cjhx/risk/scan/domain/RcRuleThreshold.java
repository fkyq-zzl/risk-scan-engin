package com.cjhx.risk.scan.domain;

public class RcRuleThreshold {
    private String ruleId;

    private String productionId;

    private String threshold1;

    private String threshold2;

    private String threshold3;

    private String warnningAction1;

    private String warnningAction2;

    private String warnningAction3;

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

    public String getThreshold1() {
        return threshold1;
    }

    public void setThreshold1(String threshold1) {
        this.threshold1 = threshold1;
    }

    public String getThreshold2() {
        return threshold2;
    }

    public void setThreshold2(String threshold2) {
        this.threshold2 = threshold2;
    }

    public String getThreshold3() {
        return threshold3;
    }

    public void setThreshold3(String threshold3) {
        this.threshold3 = threshold3;
    }

    public String getWarnningAction1() {
        return warnningAction1;
    }

    public void setWarnningAction1(String warnningAction1) {
        this.warnningAction1 = warnningAction1 == null ? null : warnningAction1.trim();
    }

    public String getWarnningAction2() {
        return warnningAction2;
    }

    public void setWarnningAction2(String warnningAction2) {
        this.warnningAction2 = warnningAction2 == null ? null : warnningAction2.trim();
    }

    public String getWarnningAction3() {
        return warnningAction3;
    }

    public void setWarnningAction3(String warnningAction3) {
        this.warnningAction3 = warnningAction3 == null ? null : warnningAction3.trim();
    }
}