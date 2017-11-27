package com.cjhx.risk.o32scan.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AllStockInfoHis {
	//关联产品ID
	private String vcProdCode;
	
    private BigDecimal lDate;

    private String cMarketNo1;

    private String cMarketNo2;

    private String vcStockType;

    private String vcInterCodeWind1;

    private String vcInterCodeWind2;

    private String vcInterCodeO32;

    private String vcStockCode;

    private String vcStockName;

    private String vcStockNameFull;

    private String vcBondTypeWind1;

    private String vcBondTypeWind2;

    private String vcIssueType;

    private BigDecimal enExistLimite;

    private BigDecimal enIssueBalance;

    private String lIssuerId;

    private String vcIssuerName;

    private String vcIssuerNameFull;

    private String cIssuerProperty;

    private BigDecimal enIssuerNetValue;

    private String vcIssuerPunishInfo;

    private BigDecimal lIssuePostDate;

    private BigDecimal lIssueBeginDate;

    private BigDecimal lIssueEndDate;

    private String cBidType;

    private String cPayType;

    private BigDecimal lPayDate;

    private BigDecimal lPublishDate;

    private BigDecimal lDelistingDate;

    private BigDecimal lBegincalDate;

    private BigDecimal lEndincalDate;

    private BigDecimal lEndincalDays;

    private BigDecimal lLimiteLeft;

    private BigDecimal lTimeLimit;

    private String lGuarantorId;

    private String vcGuarantorName;

    private String vcGuarantorSummary;

    private String cAssureType;

    private String vcMainUnderwriterId;

    private String vcMainUnderwriter;

    private String vcDeputyUnderwriterId;

    private String vcDeputyUnderwriter;

    private String cUnderwriteType;

    private String vcGroupUnderwriter;

    private String cBondAppraise1;

    private String cBondAppraise2;

    private String cBondAppraise3;

    private String cBondAppraise4;

    private String cIssuerAppraise1;

    private String cIssuerAppraise2;

    private String cIssuerAppraise3;

    private String cIssuerAppraise4;

    private String vcBondAppraiseOrga;

    private String vcIssuerAppraiseOrga;

    private String cIssuerAppraiseForecast;

    private String cIssuerAppraiseCsrc;

    private String cMainUnderwriterAppraise;

    private String vcIndustryWind;

    private String vcIndustryWind1;

    private String vcIndustryWind2;

    private String vcIndustryWind3;

    private String vcIndustryWind4;

    private String vcIndustryCsrc;

    private String vcIndustryCsrc1;

    private String vcIndustryCsrc2;

    private String vcIndustryCsrc3;

    private String vcIndustryCsrc4;

    private String vcProvince;

    private String vcBchmkRate;

    private BigDecimal enParvalue;

    private BigDecimal enFaceRate;

    private String cIsFlatTrading;

    private String cInterestType;

    private String vcInterestRateDesc;

    private String cPayInterestType;

    private BigDecimal enPayInteval;

    private BigDecimal lNextPayintDate;

    private BigDecimal lNextPayintDays;

    private BigDecimal lNextExerciseDate;

    private String vcSpecialText;

    private String vcSpecialTextDesc;

    private String vcSpecialLimite;

    private BigDecimal lNInterestRateJumpPoints;

    private String cIsHaveSaleback;

    private BigDecimal enCbValueConvexity;

    private BigDecimal enCbValueMduratio;

    private BigDecimal enCbValueIncmRate;

    private BigDecimal enCbValueNetValue;

    private BigDecimal enCbValueAllValue;

    private BigDecimal enCzValueConvexity;

    private BigDecimal enCzValueMduratio;

    private BigDecimal enCzValueIncmRate;

    private BigDecimal enCzValueNetValue;

    private BigDecimal enCzValueAllValue;

    private String cIsStepMarket;

    private String vcStepMarketInterCode;

    private BigDecimal enExchangeConverRatio;

    private String vcExchangePledgeCode;

    private String vcPaymentPlace;

    private String vcLatestMaxInvestor;

    private String vcTenderInterval;

    private String cIsHaveRight;

    private String cNewCopBondType;

    private String cIsSmall;

    private String cIsCityBond;

    private String cIsSubprimeBond;

    private String cIsSecuredBond;

    private String cIsSepareteBond;

    private String cIsPerpetualBond;

    private String cIsPrivateBond;

    private String cIsSmallRaised;

    private String cCityLevel;

    private BigDecimal enTotalStocks;

    private BigDecimal enCirculationStocks;

    private BigDecimal enLatestIssue;

    private BigDecimal enLatestTotalStocks;

    private BigDecimal enLastPriceClose;

    private BigDecimal lFundSetDays;

    private String cFundType;

    private String vcMortgageStkCode;

    private String vcMortgageStkName;

    private BigDecimal lMortgageStkAmount;

    private BigDecimal lNextRepayDate;

    private BigDecimal lNextSalebackDate;

    private BigDecimal lNextRedeemDate;

    private String vcGroupUnderwriterId;

    private String cIsRaiseBond;

    private BigDecimal enVd01;

    private String cAbsType;

    private String cMktfundType;

    private String cStructfundType;

    private String cStocksectType;

    private String lIsPpn;

    private String cStructfundPerpetualtype;

    private BigDecimal enCzVd01;

    private String cjdt;

    private Date inserttime;

    public String getVcProdCode() {
		return vcProdCode;
	}

	public void setVcProdCode(String vcProdCode) {
		this.vcProdCode = vcProdCode;
	}

	public BigDecimal getlDate() {
        return lDate;
    }

    public void setlDate(BigDecimal lDate) {
        this.lDate = lDate;
    }

    public String getcMarketNo1() {
        return cMarketNo1;
    }

    public void setcMarketNo1(String cMarketNo1) {
        this.cMarketNo1 = cMarketNo1 == null ? null : cMarketNo1.trim();
    }

    public String getcMarketNo2() {
        return cMarketNo2;
    }

    public void setcMarketNo2(String cMarketNo2) {
        this.cMarketNo2 = cMarketNo2 == null ? null : cMarketNo2.trim();
    }

    public String getVcStockType() {
        return vcStockType;
    }

    public void setVcStockType(String vcStockType) {
        this.vcStockType = vcStockType == null ? null : vcStockType.trim();
    }

    public String getVcInterCodeWind1() {
        return vcInterCodeWind1;
    }

    public void setVcInterCodeWind1(String vcInterCodeWind1) {
        this.vcInterCodeWind1 = vcInterCodeWind1 == null ? null : vcInterCodeWind1.trim();
    }

    public String getVcInterCodeWind2() {
        return vcInterCodeWind2;
    }

    public void setVcInterCodeWind2(String vcInterCodeWind2) {
        this.vcInterCodeWind2 = vcInterCodeWind2 == null ? null : vcInterCodeWind2.trim();
    }

    public String getVcInterCodeO32() {
        return vcInterCodeO32;
    }

    public void setVcInterCodeO32(String vcInterCodeO32) {
        this.vcInterCodeO32 = vcInterCodeO32 == null ? null : vcInterCodeO32.trim();
    }

    public String getVcStockCode() {
        return vcStockCode;
    }

    public void setVcStockCode(String vcStockCode) {
        this.vcStockCode = vcStockCode == null ? null : vcStockCode.trim();
    }

    public String getVcStockName() {
        return vcStockName;
    }

    public void setVcStockName(String vcStockName) {
        this.vcStockName = vcStockName == null ? null : vcStockName.trim();
    }

    public String getVcStockNameFull() {
        return vcStockNameFull;
    }

    public void setVcStockNameFull(String vcStockNameFull) {
        this.vcStockNameFull = vcStockNameFull == null ? null : vcStockNameFull.trim();
    }

    public String getVcBondTypeWind1() {
        return vcBondTypeWind1;
    }

    public void setVcBondTypeWind1(String vcBondTypeWind1) {
        this.vcBondTypeWind1 = vcBondTypeWind1 == null ? null : vcBondTypeWind1.trim();
    }

    public String getVcBondTypeWind2() {
        return vcBondTypeWind2;
    }

    public void setVcBondTypeWind2(String vcBondTypeWind2) {
        this.vcBondTypeWind2 = vcBondTypeWind2 == null ? null : vcBondTypeWind2.trim();
    }

    public String getVcIssueType() {
        return vcIssueType;
    }

    public void setVcIssueType(String vcIssueType) {
        this.vcIssueType = vcIssueType == null ? null : vcIssueType.trim();
    }

    public BigDecimal getEnExistLimite() {
        return enExistLimite;
    }

    public void setEnExistLimite(BigDecimal enExistLimite) {
        this.enExistLimite = enExistLimite;
    }

    public BigDecimal getEnIssueBalance() {
        return enIssueBalance;
    }

    public void setEnIssueBalance(BigDecimal enIssueBalance) {
        this.enIssueBalance = enIssueBalance;
    }

    public String getlIssuerId() {
        return lIssuerId;
    }

    public void setlIssuerId(String lIssuerId) {
        this.lIssuerId = lIssuerId == null ? null : lIssuerId.trim();
    }

    public String getVcIssuerName() {
        return vcIssuerName;
    }

    public void setVcIssuerName(String vcIssuerName) {
        this.vcIssuerName = vcIssuerName == null ? null : vcIssuerName.trim();
    }

    public String getVcIssuerNameFull() {
        return vcIssuerNameFull;
    }

    public void setVcIssuerNameFull(String vcIssuerNameFull) {
        this.vcIssuerNameFull = vcIssuerNameFull == null ? null : vcIssuerNameFull.trim();
    }

    public String getcIssuerProperty() {
        return cIssuerProperty;
    }

    public void setcIssuerProperty(String cIssuerProperty) {
        this.cIssuerProperty = cIssuerProperty == null ? null : cIssuerProperty.trim();
    }

    public BigDecimal getEnIssuerNetValue() {
        return enIssuerNetValue;
    }

    public void setEnIssuerNetValue(BigDecimal enIssuerNetValue) {
        this.enIssuerNetValue = enIssuerNetValue;
    }

    public String getVcIssuerPunishInfo() {
        return vcIssuerPunishInfo;
    }

    public void setVcIssuerPunishInfo(String vcIssuerPunishInfo) {
        this.vcIssuerPunishInfo = vcIssuerPunishInfo == null ? null : vcIssuerPunishInfo.trim();
    }

    public BigDecimal getlIssuePostDate() {
        return lIssuePostDate;
    }

    public void setlIssuePostDate(BigDecimal lIssuePostDate) {
        this.lIssuePostDate = lIssuePostDate;
    }

    public BigDecimal getlIssueBeginDate() {
        return lIssueBeginDate;
    }

    public void setlIssueBeginDate(BigDecimal lIssueBeginDate) {
        this.lIssueBeginDate = lIssueBeginDate;
    }

    public BigDecimal getlIssueEndDate() {
        return lIssueEndDate;
    }

    public void setlIssueEndDate(BigDecimal lIssueEndDate) {
        this.lIssueEndDate = lIssueEndDate;
    }

    public String getcBidType() {
        return cBidType;
    }

    public void setcBidType(String cBidType) {
        this.cBidType = cBidType == null ? null : cBidType.trim();
    }

    public String getcPayType() {
        return cPayType;
    }

    public void setcPayType(String cPayType) {
        this.cPayType = cPayType == null ? null : cPayType.trim();
    }

    public BigDecimal getlPayDate() {
        return lPayDate;
    }

    public void setlPayDate(BigDecimal lPayDate) {
        this.lPayDate = lPayDate;
    }

    public BigDecimal getlPublishDate() {
        return lPublishDate;
    }

    public void setlPublishDate(BigDecimal lPublishDate) {
        this.lPublishDate = lPublishDate;
    }

    public BigDecimal getlDelistingDate() {
        return lDelistingDate;
    }

    public void setlDelistingDate(BigDecimal lDelistingDate) {
        this.lDelistingDate = lDelistingDate;
    }

    public BigDecimal getlBegincalDate() {
        return lBegincalDate;
    }

    public void setlBegincalDate(BigDecimal lBegincalDate) {
        this.lBegincalDate = lBegincalDate;
    }

    public BigDecimal getlEndincalDate() {
        return lEndincalDate;
    }

    public void setlEndincalDate(BigDecimal lEndincalDate) {
        this.lEndincalDate = lEndincalDate;
    }

    public BigDecimal getlEndincalDays() {
        return lEndincalDays;
    }

    public void setlEndincalDays(BigDecimal lEndincalDays) {
        this.lEndincalDays = lEndincalDays;
    }

    public BigDecimal getlLimiteLeft() {
        return lLimiteLeft;
    }

    public void setlLimiteLeft(BigDecimal lLimiteLeft) {
        this.lLimiteLeft = lLimiteLeft;
    }

    public BigDecimal getlTimeLimit() {
        return lTimeLimit;
    }

    public void setlTimeLimit(BigDecimal lTimeLimit) {
        this.lTimeLimit = lTimeLimit;
    }

    public String getlGuarantorId() {
        return lGuarantorId;
    }

    public void setlGuarantorId(String lGuarantorId) {
        this.lGuarantorId = lGuarantorId == null ? null : lGuarantorId.trim();
    }

    public String getVcGuarantorName() {
        return vcGuarantorName;
    }

    public void setVcGuarantorName(String vcGuarantorName) {
        this.vcGuarantorName = vcGuarantorName == null ? null : vcGuarantorName.trim();
    }

    public String getVcGuarantorSummary() {
        return vcGuarantorSummary;
    }

    public void setVcGuarantorSummary(String vcGuarantorSummary) {
        this.vcGuarantorSummary = vcGuarantorSummary == null ? null : vcGuarantorSummary.trim();
    }

    public String getcAssureType() {
        return cAssureType;
    }

    public void setcAssureType(String cAssureType) {
        this.cAssureType = cAssureType == null ? null : cAssureType.trim();
    }

    public String getVcMainUnderwriterId() {
        return vcMainUnderwriterId;
    }

    public void setVcMainUnderwriterId(String vcMainUnderwriterId) {
        this.vcMainUnderwriterId = vcMainUnderwriterId == null ? null : vcMainUnderwriterId.trim();
    }

    public String getVcMainUnderwriter() {
        return vcMainUnderwriter;
    }

    public void setVcMainUnderwriter(String vcMainUnderwriter) {
        this.vcMainUnderwriter = vcMainUnderwriter == null ? null : vcMainUnderwriter.trim();
    }

    public String getVcDeputyUnderwriterId() {
        return vcDeputyUnderwriterId;
    }

    public void setVcDeputyUnderwriterId(String vcDeputyUnderwriterId) {
        this.vcDeputyUnderwriterId = vcDeputyUnderwriterId == null ? null : vcDeputyUnderwriterId.trim();
    }

    public String getVcDeputyUnderwriter() {
        return vcDeputyUnderwriter;
    }

    public void setVcDeputyUnderwriter(String vcDeputyUnderwriter) {
        this.vcDeputyUnderwriter = vcDeputyUnderwriter == null ? null : vcDeputyUnderwriter.trim();
    }

    public String getcUnderwriteType() {
        return cUnderwriteType;
    }

    public void setcUnderwriteType(String cUnderwriteType) {
        this.cUnderwriteType = cUnderwriteType == null ? null : cUnderwriteType.trim();
    }

    public String getVcGroupUnderwriter() {
        return vcGroupUnderwriter;
    }

    public void setVcGroupUnderwriter(String vcGroupUnderwriter) {
        this.vcGroupUnderwriter = vcGroupUnderwriter == null ? null : vcGroupUnderwriter.trim();
    }

    public String getcBondAppraise1() {
        return cBondAppraise1;
    }

    public void setcBondAppraise1(String cBondAppraise1) {
        this.cBondAppraise1 = cBondAppraise1 == null ? null : cBondAppraise1.trim();
    }

    public String getcBondAppraise2() {
        return cBondAppraise2;
    }

    public void setcBondAppraise2(String cBondAppraise2) {
        this.cBondAppraise2 = cBondAppraise2 == null ? null : cBondAppraise2.trim();
    }

    public String getcBondAppraise3() {
        return cBondAppraise3;
    }

    public void setcBondAppraise3(String cBondAppraise3) {
        this.cBondAppraise3 = cBondAppraise3 == null ? null : cBondAppraise3.trim();
    }

    public String getcBondAppraise4() {
        return cBondAppraise4;
    }

    public void setcBondAppraise4(String cBondAppraise4) {
        this.cBondAppraise4 = cBondAppraise4 == null ? null : cBondAppraise4.trim();
    }

    public String getcIssuerAppraise1() {
        return cIssuerAppraise1;
    }

    public void setcIssuerAppraise1(String cIssuerAppraise1) {
        this.cIssuerAppraise1 = cIssuerAppraise1 == null ? null : cIssuerAppraise1.trim();
    }

    public String getcIssuerAppraise2() {
        return cIssuerAppraise2;
    }

    public void setcIssuerAppraise2(String cIssuerAppraise2) {
        this.cIssuerAppraise2 = cIssuerAppraise2 == null ? null : cIssuerAppraise2.trim();
    }

    public String getcIssuerAppraise3() {
        return cIssuerAppraise3;
    }

    public void setcIssuerAppraise3(String cIssuerAppraise3) {
        this.cIssuerAppraise3 = cIssuerAppraise3 == null ? null : cIssuerAppraise3.trim();
    }

    public String getcIssuerAppraise4() {
        return cIssuerAppraise4;
    }

    public void setcIssuerAppraise4(String cIssuerAppraise4) {
        this.cIssuerAppraise4 = cIssuerAppraise4 == null ? null : cIssuerAppraise4.trim();
    }

    public String getVcBondAppraiseOrga() {
        return vcBondAppraiseOrga;
    }

    public void setVcBondAppraiseOrga(String vcBondAppraiseOrga) {
        this.vcBondAppraiseOrga = vcBondAppraiseOrga == null ? null : vcBondAppraiseOrga.trim();
    }

    public String getVcIssuerAppraiseOrga() {
        return vcIssuerAppraiseOrga;
    }

    public void setVcIssuerAppraiseOrga(String vcIssuerAppraiseOrga) {
        this.vcIssuerAppraiseOrga = vcIssuerAppraiseOrga == null ? null : vcIssuerAppraiseOrga.trim();
    }

    public String getcIssuerAppraiseForecast() {
        return cIssuerAppraiseForecast;
    }

    public void setcIssuerAppraiseForecast(String cIssuerAppraiseForecast) {
        this.cIssuerAppraiseForecast = cIssuerAppraiseForecast == null ? null : cIssuerAppraiseForecast.trim();
    }

    public String getcIssuerAppraiseCsrc() {
        return cIssuerAppraiseCsrc;
    }

    public void setcIssuerAppraiseCsrc(String cIssuerAppraiseCsrc) {
        this.cIssuerAppraiseCsrc = cIssuerAppraiseCsrc == null ? null : cIssuerAppraiseCsrc.trim();
    }

    public String getcMainUnderwriterAppraise() {
        return cMainUnderwriterAppraise;
    }

    public void setcMainUnderwriterAppraise(String cMainUnderwriterAppraise) {
        this.cMainUnderwriterAppraise = cMainUnderwriterAppraise == null ? null : cMainUnderwriterAppraise.trim();
    }

    public String getVcIndustryWind() {
        return vcIndustryWind;
    }

    public void setVcIndustryWind(String vcIndustryWind) {
        this.vcIndustryWind = vcIndustryWind == null ? null : vcIndustryWind.trim();
    }

    public String getVcIndustryWind1() {
        return vcIndustryWind1;
    }

    public void setVcIndustryWind1(String vcIndustryWind1) {
        this.vcIndustryWind1 = vcIndustryWind1 == null ? null : vcIndustryWind1.trim();
    }

    public String getVcIndustryWind2() {
        return vcIndustryWind2;
    }

    public void setVcIndustryWind2(String vcIndustryWind2) {
        this.vcIndustryWind2 = vcIndustryWind2 == null ? null : vcIndustryWind2.trim();
    }

    public String getVcIndustryWind3() {
        return vcIndustryWind3;
    }

    public void setVcIndustryWind3(String vcIndustryWind3) {
        this.vcIndustryWind3 = vcIndustryWind3 == null ? null : vcIndustryWind3.trim();
    }

    public String getVcIndustryWind4() {
        return vcIndustryWind4;
    }

    public void setVcIndustryWind4(String vcIndustryWind4) {
        this.vcIndustryWind4 = vcIndustryWind4 == null ? null : vcIndustryWind4.trim();
    }

    public String getVcIndustryCsrc() {
        return vcIndustryCsrc;
    }

    public void setVcIndustryCsrc(String vcIndustryCsrc) {
        this.vcIndustryCsrc = vcIndustryCsrc == null ? null : vcIndustryCsrc.trim();
    }

    public String getVcIndustryCsrc1() {
        return vcIndustryCsrc1;
    }

    public void setVcIndustryCsrc1(String vcIndustryCsrc1) {
        this.vcIndustryCsrc1 = vcIndustryCsrc1 == null ? null : vcIndustryCsrc1.trim();
    }

    public String getVcIndustryCsrc2() {
        return vcIndustryCsrc2;
    }

    public void setVcIndustryCsrc2(String vcIndustryCsrc2) {
        this.vcIndustryCsrc2 = vcIndustryCsrc2 == null ? null : vcIndustryCsrc2.trim();
    }

    public String getVcIndustryCsrc3() {
        return vcIndustryCsrc3;
    }

    public void setVcIndustryCsrc3(String vcIndustryCsrc3) {
        this.vcIndustryCsrc3 = vcIndustryCsrc3 == null ? null : vcIndustryCsrc3.trim();
    }

    public String getVcIndustryCsrc4() {
        return vcIndustryCsrc4;
    }

    public void setVcIndustryCsrc4(String vcIndustryCsrc4) {
        this.vcIndustryCsrc4 = vcIndustryCsrc4 == null ? null : vcIndustryCsrc4.trim();
    }

    public String getVcProvince() {
        return vcProvince;
    }

    public void setVcProvince(String vcProvince) {
        this.vcProvince = vcProvince == null ? null : vcProvince.trim();
    }

    public String getVcBchmkRate() {
        return vcBchmkRate;
    }

    public void setVcBchmkRate(String vcBchmkRate) {
        this.vcBchmkRate = vcBchmkRate == null ? null : vcBchmkRate.trim();
    }

    public BigDecimal getEnParvalue() {
        return enParvalue;
    }

    public void setEnParvalue(BigDecimal enParvalue) {
        this.enParvalue = enParvalue;
    }

    public BigDecimal getEnFaceRate() {
        return enFaceRate;
    }

    public void setEnFaceRate(BigDecimal enFaceRate) {
        this.enFaceRate = enFaceRate;
    }

    public String getcIsFlatTrading() {
        return cIsFlatTrading;
    }

    public void setcIsFlatTrading(String cIsFlatTrading) {
        this.cIsFlatTrading = cIsFlatTrading == null ? null : cIsFlatTrading.trim();
    }

    public String getcInterestType() {
        return cInterestType;
    }

    public void setcInterestType(String cInterestType) {
        this.cInterestType = cInterestType == null ? null : cInterestType.trim();
    }

    public String getVcInterestRateDesc() {
        return vcInterestRateDesc;
    }

    public void setVcInterestRateDesc(String vcInterestRateDesc) {
        this.vcInterestRateDesc = vcInterestRateDesc == null ? null : vcInterestRateDesc.trim();
    }

    public String getcPayInterestType() {
        return cPayInterestType;
    }

    public void setcPayInterestType(String cPayInterestType) {
        this.cPayInterestType = cPayInterestType == null ? null : cPayInterestType.trim();
    }

    public BigDecimal getEnPayInteval() {
        return enPayInteval;
    }

    public void setEnPayInteval(BigDecimal enPayInteval) {
        this.enPayInteval = enPayInteval;
    }

    public BigDecimal getlNextPayintDate() {
        return lNextPayintDate;
    }

    public void setlNextPayintDate(BigDecimal lNextPayintDate) {
        this.lNextPayintDate = lNextPayintDate;
    }

    public BigDecimal getlNextPayintDays() {
        return lNextPayintDays;
    }

    public void setlNextPayintDays(BigDecimal lNextPayintDays) {
        this.lNextPayintDays = lNextPayintDays;
    }

    public BigDecimal getlNextExerciseDate() {
        return lNextExerciseDate;
    }

    public void setlNextExerciseDate(BigDecimal lNextExerciseDate) {
        this.lNextExerciseDate = lNextExerciseDate;
    }

    public String getVcSpecialText() {
        return vcSpecialText;
    }

    public void setVcSpecialText(String vcSpecialText) {
        this.vcSpecialText = vcSpecialText == null ? null : vcSpecialText.trim();
    }

    public String getVcSpecialTextDesc() {
        return vcSpecialTextDesc;
    }

    public void setVcSpecialTextDesc(String vcSpecialTextDesc) {
        this.vcSpecialTextDesc = vcSpecialTextDesc == null ? null : vcSpecialTextDesc.trim();
    }

    public String getVcSpecialLimite() {
        return vcSpecialLimite;
    }

    public void setVcSpecialLimite(String vcSpecialLimite) {
        this.vcSpecialLimite = vcSpecialLimite == null ? null : vcSpecialLimite.trim();
    }

    public BigDecimal getlNInterestRateJumpPoints() {
        return lNInterestRateJumpPoints;
    }

    public void setlNInterestRateJumpPoints(BigDecimal lNInterestRateJumpPoints) {
        this.lNInterestRateJumpPoints = lNInterestRateJumpPoints;
    }

    public String getcIsHaveSaleback() {
        return cIsHaveSaleback;
    }

    public void setcIsHaveSaleback(String cIsHaveSaleback) {
        this.cIsHaveSaleback = cIsHaveSaleback == null ? null : cIsHaveSaleback.trim();
    }

    public BigDecimal getEnCbValueConvexity() {
        return enCbValueConvexity;
    }

    public void setEnCbValueConvexity(BigDecimal enCbValueConvexity) {
        this.enCbValueConvexity = enCbValueConvexity;
    }

    public BigDecimal getEnCbValueMduratio() {
        return enCbValueMduratio;
    }

    public void setEnCbValueMduratio(BigDecimal enCbValueMduratio) {
        this.enCbValueMduratio = enCbValueMduratio;
    }

    public BigDecimal getEnCbValueIncmRate() {
        return enCbValueIncmRate;
    }

    public void setEnCbValueIncmRate(BigDecimal enCbValueIncmRate) {
        this.enCbValueIncmRate = enCbValueIncmRate;
    }

    public BigDecimal getEnCbValueNetValue() {
        return enCbValueNetValue;
    }

    public void setEnCbValueNetValue(BigDecimal enCbValueNetValue) {
        this.enCbValueNetValue = enCbValueNetValue;
    }

    public BigDecimal getEnCbValueAllValue() {
        return enCbValueAllValue;
    }

    public void setEnCbValueAllValue(BigDecimal enCbValueAllValue) {
        this.enCbValueAllValue = enCbValueAllValue;
    }

    public BigDecimal getEnCzValueConvexity() {
        return enCzValueConvexity;
    }

    public void setEnCzValueConvexity(BigDecimal enCzValueConvexity) {
        this.enCzValueConvexity = enCzValueConvexity;
    }

    public BigDecimal getEnCzValueMduratio() {
        return enCzValueMduratio;
    }

    public void setEnCzValueMduratio(BigDecimal enCzValueMduratio) {
        this.enCzValueMduratio = enCzValueMduratio;
    }

    public BigDecimal getEnCzValueIncmRate() {
        return enCzValueIncmRate;
    }

    public void setEnCzValueIncmRate(BigDecimal enCzValueIncmRate) {
        this.enCzValueIncmRate = enCzValueIncmRate;
    }

    public BigDecimal getEnCzValueNetValue() {
        return enCzValueNetValue;
    }

    public void setEnCzValueNetValue(BigDecimal enCzValueNetValue) {
        this.enCzValueNetValue = enCzValueNetValue;
    }

    public BigDecimal getEnCzValueAllValue() {
        return enCzValueAllValue;
    }

    public void setEnCzValueAllValue(BigDecimal enCzValueAllValue) {
        this.enCzValueAllValue = enCzValueAllValue;
    }

    public String getcIsStepMarket() {
        return cIsStepMarket;
    }

    public void setcIsStepMarket(String cIsStepMarket) {
        this.cIsStepMarket = cIsStepMarket == null ? null : cIsStepMarket.trim();
    }

    public String getVcStepMarketInterCode() {
        return vcStepMarketInterCode;
    }

    public void setVcStepMarketInterCode(String vcStepMarketInterCode) {
        this.vcStepMarketInterCode = vcStepMarketInterCode == null ? null : vcStepMarketInterCode.trim();
    }

    public BigDecimal getEnExchangeConverRatio() {
        return enExchangeConverRatio;
    }

    public void setEnExchangeConverRatio(BigDecimal enExchangeConverRatio) {
        this.enExchangeConverRatio = enExchangeConverRatio;
    }

    public String getVcExchangePledgeCode() {
        return vcExchangePledgeCode;
    }

    public void setVcExchangePledgeCode(String vcExchangePledgeCode) {
        this.vcExchangePledgeCode = vcExchangePledgeCode == null ? null : vcExchangePledgeCode.trim();
    }

    public String getVcPaymentPlace() {
        return vcPaymentPlace;
    }

    public void setVcPaymentPlace(String vcPaymentPlace) {
        this.vcPaymentPlace = vcPaymentPlace == null ? null : vcPaymentPlace.trim();
    }

    public String getVcLatestMaxInvestor() {
        return vcLatestMaxInvestor;
    }

    public void setVcLatestMaxInvestor(String vcLatestMaxInvestor) {
        this.vcLatestMaxInvestor = vcLatestMaxInvestor == null ? null : vcLatestMaxInvestor.trim();
    }

    public String getVcTenderInterval() {
        return vcTenderInterval;
    }

    public void setVcTenderInterval(String vcTenderInterval) {
        this.vcTenderInterval = vcTenderInterval == null ? null : vcTenderInterval.trim();
    }

    public String getcIsHaveRight() {
        return cIsHaveRight;
    }

    public void setcIsHaveRight(String cIsHaveRight) {
        this.cIsHaveRight = cIsHaveRight == null ? null : cIsHaveRight.trim();
    }

    public String getcNewCopBondType() {
        return cNewCopBondType;
    }

    public void setcNewCopBondType(String cNewCopBondType) {
        this.cNewCopBondType = cNewCopBondType == null ? null : cNewCopBondType.trim();
    }

    public String getcIsSmall() {
        return cIsSmall;
    }

    public void setcIsSmall(String cIsSmall) {
        this.cIsSmall = cIsSmall == null ? null : cIsSmall.trim();
    }

    public String getcIsCityBond() {
        return cIsCityBond;
    }

    public void setcIsCityBond(String cIsCityBond) {
        this.cIsCityBond = cIsCityBond == null ? null : cIsCityBond.trim();
    }

    public String getcIsSubprimeBond() {
        return cIsSubprimeBond;
    }

    public void setcIsSubprimeBond(String cIsSubprimeBond) {
        this.cIsSubprimeBond = cIsSubprimeBond == null ? null : cIsSubprimeBond.trim();
    }

    public String getcIsSecuredBond() {
        return cIsSecuredBond;
    }

    public void setcIsSecuredBond(String cIsSecuredBond) {
        this.cIsSecuredBond = cIsSecuredBond == null ? null : cIsSecuredBond.trim();
    }

    public String getcIsSepareteBond() {
        return cIsSepareteBond;
    }

    public void setcIsSepareteBond(String cIsSepareteBond) {
        this.cIsSepareteBond = cIsSepareteBond == null ? null : cIsSepareteBond.trim();
    }

    public String getcIsPerpetualBond() {
        return cIsPerpetualBond;
    }

    public void setcIsPerpetualBond(String cIsPerpetualBond) {
        this.cIsPerpetualBond = cIsPerpetualBond == null ? null : cIsPerpetualBond.trim();
    }

    public String getcIsPrivateBond() {
        return cIsPrivateBond;
    }

    public void setcIsPrivateBond(String cIsPrivateBond) {
        this.cIsPrivateBond = cIsPrivateBond == null ? null : cIsPrivateBond.trim();
    }

    public String getcIsSmallRaised() {
        return cIsSmallRaised;
    }

    public void setcIsSmallRaised(String cIsSmallRaised) {
        this.cIsSmallRaised = cIsSmallRaised == null ? null : cIsSmallRaised.trim();
    }

    public String getcCityLevel() {
        return cCityLevel;
    }

    public void setcCityLevel(String cCityLevel) {
        this.cCityLevel = cCityLevel == null ? null : cCityLevel.trim();
    }

    public BigDecimal getEnTotalStocks() {
        return enTotalStocks;
    }

    public void setEnTotalStocks(BigDecimal enTotalStocks) {
        this.enTotalStocks = enTotalStocks;
    }

    public BigDecimal getEnCirculationStocks() {
        return enCirculationStocks;
    }

    public void setEnCirculationStocks(BigDecimal enCirculationStocks) {
        this.enCirculationStocks = enCirculationStocks;
    }

    public BigDecimal getEnLatestIssue() {
        return enLatestIssue;
    }

    public void setEnLatestIssue(BigDecimal enLatestIssue) {
        this.enLatestIssue = enLatestIssue;
    }

    public BigDecimal getEnLatestTotalStocks() {
        return enLatestTotalStocks;
    }

    public void setEnLatestTotalStocks(BigDecimal enLatestTotalStocks) {
        this.enLatestTotalStocks = enLatestTotalStocks;
    }

    public BigDecimal getEnLastPriceClose() {
        return enLastPriceClose;
    }

    public void setEnLastPriceClose(BigDecimal enLastPriceClose) {
        this.enLastPriceClose = enLastPriceClose;
    }

    public BigDecimal getlFundSetDays() {
        return lFundSetDays;
    }

    public void setlFundSetDays(BigDecimal lFundSetDays) {
        this.lFundSetDays = lFundSetDays;
    }

    public String getcFundType() {
        return cFundType;
    }

    public void setcFundType(String cFundType) {
        this.cFundType = cFundType == null ? null : cFundType.trim();
    }

    public String getVcMortgageStkCode() {
        return vcMortgageStkCode;
    }

    public void setVcMortgageStkCode(String vcMortgageStkCode) {
        this.vcMortgageStkCode = vcMortgageStkCode == null ? null : vcMortgageStkCode.trim();
    }

    public String getVcMortgageStkName() {
        return vcMortgageStkName;
    }

    public void setVcMortgageStkName(String vcMortgageStkName) {
        this.vcMortgageStkName = vcMortgageStkName == null ? null : vcMortgageStkName.trim();
    }

    public BigDecimal getlMortgageStkAmount() {
        return lMortgageStkAmount;
    }

    public void setlMortgageStkAmount(BigDecimal lMortgageStkAmount) {
        this.lMortgageStkAmount = lMortgageStkAmount;
    }

    public BigDecimal getlNextRepayDate() {
        return lNextRepayDate;
    }

    public void setlNextRepayDate(BigDecimal lNextRepayDate) {
        this.lNextRepayDate = lNextRepayDate;
    }

    public BigDecimal getlNextSalebackDate() {
        return lNextSalebackDate;
    }

    public void setlNextSalebackDate(BigDecimal lNextSalebackDate) {
        this.lNextSalebackDate = lNextSalebackDate;
    }

    public BigDecimal getlNextRedeemDate() {
        return lNextRedeemDate;
    }

    public void setlNextRedeemDate(BigDecimal lNextRedeemDate) {
        this.lNextRedeemDate = lNextRedeemDate;
    }

    public String getVcGroupUnderwriterId() {
        return vcGroupUnderwriterId;
    }

    public void setVcGroupUnderwriterId(String vcGroupUnderwriterId) {
        this.vcGroupUnderwriterId = vcGroupUnderwriterId == null ? null : vcGroupUnderwriterId.trim();
    }

    public String getcIsRaiseBond() {
        return cIsRaiseBond;
    }

    public void setcIsRaiseBond(String cIsRaiseBond) {
        this.cIsRaiseBond = cIsRaiseBond == null ? null : cIsRaiseBond.trim();
    }

    public BigDecimal getEnVd01() {
        return enVd01;
    }

    public void setEnVd01(BigDecimal enVd01) {
        this.enVd01 = enVd01;
    }

    public String getcAbsType() {
        return cAbsType;
    }

    public void setcAbsType(String cAbsType) {
        this.cAbsType = cAbsType == null ? null : cAbsType.trim();
    }

    public String getcMktfundType() {
        return cMktfundType;
    }

    public void setcMktfundType(String cMktfundType) {
        this.cMktfundType = cMktfundType == null ? null : cMktfundType.trim();
    }

    public String getcStructfundType() {
        return cStructfundType;
    }

    public void setcStructfundType(String cStructfundType) {
        this.cStructfundType = cStructfundType == null ? null : cStructfundType.trim();
    }

    public String getcStocksectType() {
        return cStocksectType;
    }

    public void setcStocksectType(String cStocksectType) {
        this.cStocksectType = cStocksectType == null ? null : cStocksectType.trim();
    }

    public String getlIsPpn() {
        return lIsPpn;
    }

    public void setlIsPpn(String lIsPpn) {
        this.lIsPpn = lIsPpn == null ? null : lIsPpn.trim();
    }

    public String getcStructfundPerpetualtype() {
        return cStructfundPerpetualtype;
    }

    public void setcStructfundPerpetualtype(String cStructfundPerpetualtype) {
        this.cStructfundPerpetualtype = cStructfundPerpetualtype == null ? null : cStructfundPerpetualtype.trim();
    }

    public BigDecimal getEnCzVd01() {
        return enCzVd01;
    }

    public void setEnCzVd01(BigDecimal enCzVd01) {
        this.enCzVd01 = enCzVd01;
    }

    public String getCjdt() {
        return cjdt;
    }

    public void setCjdt(String cjdt) {
        this.cjdt = cjdt == null ? null : cjdt.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}