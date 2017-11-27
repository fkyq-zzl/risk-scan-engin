package com.cjhx.risk.scan.riskcheck;

public abstract class CorrelativeFactory {

	/**
	 * 检查是否相关联，具体类型指标作具体实现(如：范围类指标查询日终持仓表在总范围内的证券集)
	 * @throws Exception 
	 */
	public abstract void correlateCheck() throws Exception;
}
