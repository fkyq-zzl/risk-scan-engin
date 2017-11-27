package com.cjhx.risk.o32scan.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ProductStockDetail {
    private BigDecimal lDate;

    private String vcProdCode;

    private String vcProdName;

    private String cStockType;

    private String cMarketNo;

    private String vcStockCode;

    private String cPositionFlag;

    private String cInvestType;

    private String cStatus;

    private BigDecimal lAmount;

    private BigDecimal enNetValue;

    private BigDecimal enAllValue;

    private BigDecimal enCost;

    private Date insertTime;

    private BigDecimal lZqnm;

    private String vcZqjc;

    public BigDecimal getlDate() {
        return lDate;
    }

    public void setlDate(BigDecimal lDate) {
        this.lDate = lDate;
    }

    public String getVcProdCode() {
        return vcProdCode;
    }

    public void setVcProdCode(String vcProdCode) {
        this.vcProdCode = vcProdCode == null ? null : vcProdCode.trim();
    }

    public String getVcProdName() {
        return vcProdName;
    }

    public void setVcProdName(String vcProdName) {
        this.vcProdName = vcProdName == null ? null : vcProdName.trim();
    }

    public String getcStockType() {
        return cStockType;
    }

    public void setcStockType(String cStockType) {
        this.cStockType = cStockType == null ? null : cStockType.trim();
    }

    public String getcMarketNo() {
        return cMarketNo;
    }

    public void setcMarketNo(String cMarketNo) {
        this.cMarketNo = cMarketNo == null ? null : cMarketNo.trim();
    }

    public String getVcStockCode() {
        return vcStockCode;
    }

    public void setVcStockCode(String vcStockCode) {
        this.vcStockCode = vcStockCode == null ? null : vcStockCode.trim();
    }

    public String getcPositionFlag() {
        return cPositionFlag;
    }

    public void setcPositionFlag(String cPositionFlag) {
        this.cPositionFlag = cPositionFlag == null ? null : cPositionFlag.trim();
    }

    public String getcInvestType() {
        return cInvestType;
    }

    public void setcInvestType(String cInvestType) {
        this.cInvestType = cInvestType == null ? null : cInvestType.trim();
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus == null ? null : cStatus.trim();
    }

    public BigDecimal getlAmount() {
        return lAmount;
    }

    public void setlAmount(BigDecimal lAmount) {
        this.lAmount = lAmount;
    }

    public BigDecimal getEnNetValue() {
        return enNetValue;
    }

    public void setEnNetValue(BigDecimal enNetValue) {
        this.enNetValue = enNetValue;
    }

    public BigDecimal getEnAllValue() {
        return enAllValue;
    }

    public void setEnAllValue(BigDecimal enAllValue) {
        this.enAllValue = enAllValue;
    }

    public BigDecimal getEnCost() {
        return enCost;
    }

    public void setEnCost(BigDecimal enCost) {
        this.enCost = enCost;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public BigDecimal getlZqnm() {
        return lZqnm;
    }

    public void setlZqnm(BigDecimal lZqnm) {
        this.lZqnm = lZqnm;
    }

    public String getVcZqjc() {
        return vcZqjc;
    }

    public void setVcZqjc(String vcZqjc) {
        this.vcZqjc = vcZqjc == null ? null : vcZqjc.trim();
    }
}