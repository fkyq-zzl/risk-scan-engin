package com.cjhx.risk.scan.riskcheck;

public class ExceptionUtil {

	private boolean isSaveRcScanRuleError = false;

	public boolean getIsSaveRcScanRuleError() {
		return isSaveRcScanRuleError;
	}

	public void setSaveRcScanRuleError() {
		if(this.isSaveRcScanRuleError){
			return;
		}
		synchronized(this){
			this.isSaveRcScanRuleError = true;
		}
	}
	
}
