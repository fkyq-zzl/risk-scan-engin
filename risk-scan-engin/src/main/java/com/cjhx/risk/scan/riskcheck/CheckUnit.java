package com.cjhx.risk.scan.riskcheck;

import java.util.ArrayList;
import java.util.List;

import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.model.ProductModel;

/**
 * 检查单元
 *
 * @author lujinfu
 * @date 2017年10月19日
 */
public class CheckUnit {

	private RcRuleItem ruleItemBean;
 
	private List<ProductModel> productList = new ArrayList<ProductModel>();
	
	/*
	 * 指标是否相关联
	 */
	protected boolean isCorrelative = false;

	/*
	 * 是否被互斥排除
	 */
	private boolean isExcludeByMutex = false;

	/*
	 * 是否触发风控
	 */
	private boolean isTriggerRisk = false;

	/*
	 * 是否被联合排除
	 */
	private boolean isExcludeByUnion = false;

	/*
	 * 是否计算异常(默认正常)
	 */
	private boolean isError = false;

	/*
	 * 异常信息
	 */
	private String errorMsg;

	public CheckUnit(RcRuleItem ruleItemBean) {
		super();
		this.ruleItemBean = ruleItemBean;
	}

	public RcRuleItem getRuleItemBean() {
		return ruleItemBean;
	}

	public void setRuleItemBean(RcRuleItem ruleItemBean) {
		this.ruleItemBean = ruleItemBean;
	}

	public List<ProductModel> getProductList() {
		return productList;
	}

	public boolean getIsCorrelative() {
		return isCorrelative;
	}

	public void setIsCorrelative(boolean isCorrelative) {
		this.isCorrelative = isCorrelative;
	}

	public boolean getIsExcludeByMutex() {
		return isExcludeByMutex;
	}

	public void setIsExcludeByMutex(boolean isExcludeByMutex) {
		this.isExcludeByMutex = isExcludeByMutex;
	}

	public boolean getIsTriggerRisk() {
		return isTriggerRisk;
	}

	public void setIsTriggerRisk(boolean isTriggerRisk) {
		this.isTriggerRisk = isTriggerRisk;
	}

	public boolean getIsExcludeByUnion() {
		return isExcludeByUnion;
	}

	public void setIsExcludeByUnion(boolean isExcludeByUnion) {
		this.isExcludeByUnion = isExcludeByUnion;
	}

	public boolean getIsError() {
		return isError;
	}

	public void setIsError(boolean isError) {
		this.isError = isError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
