package com.cjhx.risk.o32prod.service;

import java.util.List;

import com.cjhx.risk.scan.riskcheck.CheckUnit;

/**
 * 查询O32数据云表记录Service
 *
 * @author lujinfu
 * @date 2017年10月18日
 */
public interface O32ProductStockService {

	/**
	 * 查询指标产品名称
	 * @param list
	 */
	void setCheckUnitProductionName(List<CheckUnit> list);

	/**
	 * 查询基金编码
	 * @param fundId
	 * @return
	 */
	String selectFundCodeByFundId(String fundId);

	/**
	 * 查询交易日
	 * @param l_date
	 * @param l_offset_days
	 * @param l_mode
	 * @param c_market_no
	 * @return
	 */
	Integer callTradingDay(int l_date, int l_offset_days, int l_mode, String c_market_no);

}
