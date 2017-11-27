package com.cjhx.risk.scan.model;

import java.math.BigDecimal;

import com.cjhx.risk.scan.domain.RcRuleThreshold;

/**
 * 各业务项计算结果
 *
 * @author lujinfu
 * @date 2017年11月9日
 */
public class ComputingResult {

	// 分子统计值(数量/成本/市值/全价市值)
	private BigDecimal numerator = new BigDecimal("0");

	// 分母统计值(净资产、总资产、股票市值、债券市值/总股本、流通股本) 数量 统计值
	private BigDecimal denominator;

	// 分子除以分母的商
	private BigDecimal result;
	//阀值集
	private RcRuleThreshold threshold;
	//触发预警的阀值
	private BigDecimal triggerThreshold;
	//触发预警的动作
	private String WarnningAction;
	
	public BigDecimal getNumerator() {
		return numerator;
	}

	public void setNumerator(BigDecimal numerator) {
		this.numerator = numerator;
	}

	public BigDecimal getDenominator() {
		return denominator;
	}

	public void setDenominator(BigDecimal denominator) {
		this.denominator = denominator;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

	public RcRuleThreshold getThreshold() {
		return threshold;
	}

	public void setThreshold(RcRuleThreshold threshold) {
		this.threshold = threshold;
	}

	public BigDecimal getTriggerThreshold() {
		return triggerThreshold;
	}

	public void setTriggerThreshold(BigDecimal triggerThreshold) {
		this.triggerThreshold = triggerThreshold;
	}

	public String getWarnningAction() {
		return WarnningAction;
	}

	public void setWarnningAction(String warnningAction) {
		WarnningAction = warnningAction;
	}

}
