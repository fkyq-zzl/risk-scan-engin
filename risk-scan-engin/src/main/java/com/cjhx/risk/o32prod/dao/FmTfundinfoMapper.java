package com.cjhx.risk.o32prod.dao;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32prod.domain.FmTfundinfo;

public interface FmTfundinfoMapper {

	/**
	 * 交易日
	 * create or replace function sf_get_date(
     * l_date        in number,--日期 
     * l_offset_days in number,--日期偏移天数
     * l_mode        in number,--模式:0-按自然日算,1-按自然日,若目标日期是非工作日则顺延,2-按交易日算,3-取最近的下一个交易日,4-取最近的上一个交易日
     * c_market_no   in char)----市场:1-上交所,2-深交所,5-银行间,n-沪港通,o-深港通
  	 * return integer is
	 */
	Integer callTradingDay(@Param("l_date")int l_date,@Param("l_offset_days")int l_offset_days,@Param("l_mode")int l_mode,@Param("c_market_no")String c_market_no);
	
	/*
	 * 根据id查询基金信息
	 */
	FmTfundinfo selectFundInfoByFundId(String fundId);
	
	/*
	 * 根据id查询基金编码
	 */
	String selectFundCodeByFundId(String fundId);
	
}