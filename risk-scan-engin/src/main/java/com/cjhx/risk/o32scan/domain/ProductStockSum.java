package com.cjhx.risk.o32scan.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ProductStockSum {
    private BigDecimal lDate;

    private String vcProdCode;

    private String vcKmdm;

    private String vcKmmc;

    private BigDecimal enValue;

    private Date insertTime;

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

    public String getVcKmdm() {
        return vcKmdm;
    }

    public void setVcKmdm(String vcKmdm) {
        this.vcKmdm = vcKmdm == null ? null : vcKmdm.trim();
    }

    public String getVcKmmc() {
        return vcKmmc;
    }

    public void setVcKmmc(String vcKmmc) {
        this.vcKmmc = vcKmmc == null ? null : vcKmmc.trim();
    }

    public BigDecimal getEnValue() {
        return enValue;
    }

    public void setEnValue(BigDecimal enValue) {
        this.enValue = enValue;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}