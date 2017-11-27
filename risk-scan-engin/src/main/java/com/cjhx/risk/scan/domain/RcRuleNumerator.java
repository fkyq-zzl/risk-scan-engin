package com.cjhx.risk.scan.domain;

public class RcRuleNumerator {
	private String ruleId;
	
	/*
	 * 1加上 2减去
	 */
	private String opeartor;

	private String numeratorCategory;

	private String numeratorItemLevel1;

	// 特殊分子： 0 净资产 1 总资产 2 融资回购 
	// 4逆回购金额 5单位净值 6股指期货保证金 7股指期货多头价值 8股指期货空头价值
	private String numeratorItemLevel2;

	// 01(净资产) 02(总资产) 06(融资回购 ) 07(逆回购金额) 12(单位净值) 
	// 13(股指期货保证金) 14(股指期货多头价值) 15(股指期货空头价值)
	private String kmdmCode;
	
	private String numeratorItemLevel3;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId == null ? null : ruleId.trim();
	}

	public String getOpeartor() {
		return opeartor;
	}

	public void setOpeartor(String opeartor) {
		this.opeartor = opeartor == null ? null : opeartor.trim();
	}

	public String getNumeratorCategory() {
		return numeratorCategory;
	}

	public void setNumeratorCategory(String numeratorCategory) {
		this.numeratorCategory = numeratorCategory == null ? null : numeratorCategory.trim();
	}

	public String getNumeratorItemLevel1() {
		return numeratorItemLevel1;
	}

	public void setNumeratorItemLevel1(String numeratorItemLevel1) {
		this.numeratorItemLevel1 = numeratorItemLevel1 == null ? null : numeratorItemLevel1.trim();
	}

	public String getNumeratorItemLevel2() {
		return numeratorItemLevel2;
	}

	public void setNumeratorItemLevel2(String numeratorItemLevel2) {
		this.numeratorItemLevel2 = numeratorItemLevel2 == null ? null : numeratorItemLevel2.trim();
		// 特殊分子： 0 净资产 1 总资产   2 融资回购 4逆回购金额 5单位净值 
		// 6股指期货保证金 7股指期货多头价值 8股指期货空头价值

		// 01(净资产) 02(总资产) 06(融资回购) 07(逆回购金额) 12(单位净值) 
		// 13(股指期货保证金) 14(股指期货多头价值) 15(股指期货空头价值)
		if("0".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "01";
		}else if("1".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "02";
		}else if("2".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "06";
		}else if("4".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "07";
		}else if("5".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "12";
		}else if("6".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "13";
		}else if("7".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "14";
		}else if("8".equals(this.numeratorItemLevel2)){
			this.kmdmCode = "15";
		}else{
			this.kmdmCode = null;
		}
	
	}

	public String getKmdmCode() {
		return kmdmCode;
	}

	public String getNumeratorItemLevel3() {
		return numeratorItemLevel3;
	}

	public void setNumeratorItemLevel3(String numeratorItemLevel3) {
		this.numeratorItemLevel3 = numeratorItemLevel3 == null ? null : numeratorItemLevel3.trim();
	}

}