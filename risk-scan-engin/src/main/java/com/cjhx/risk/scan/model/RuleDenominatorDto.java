package com.cjhx.risk.scan.model;

/**
 * 限制类指标分母Model
 *
 * @author lujinfu
 * @date 2017年11月7日
 */
public class RuleDenominatorDto {
	/*
	 * 1加上 2减去
	 */
	private String opeartor;
	// 项值：0净资产 1总资产 2股票市值 3债券市值 4总股本 5流通股本 6前一日净值（取T-1日净资产） 7过去10交易日平均份额 
	private String denominator;
	// 项值：01净资产  02总资产 03股票市值 04债券市值                        01前一日净值                                  EN_TOTAL_STOCKS_AVG10过去10交易日平均份额    
	private String kmdmCode;
	
	public String getOpeartor() {
		return opeartor;
	}

	public void setOpeartor(String opeartor) {
		this.opeartor = opeartor;
	}

	public String getDenominator() {
		return denominator;
	}

	public void setDenominator(String denominator) {
		// 项值：0净资产 1总资产 2股票市值 3债券市值 4总股本 5流通股本
//		private String denominator;
		// 项值：01净资产  02总资产 03股票市值 04债券市值
//		private String kmdmCode;
		this.denominator = denominator;
		if("0".equals(this.denominator)){
			this.kmdmCode = "01";
		}else if("1".equals(this.denominator)){
			this.kmdmCode = "02";
		}else if("2".equals(this.denominator)){
			this.kmdmCode = "03";
		}else if("3".equals(this.denominator)){
			this.kmdmCode = "04";
		}else{
			this.kmdmCode = null;
		}
	}

	public String getKmdmCode() {
		return kmdmCode;
	}

}
