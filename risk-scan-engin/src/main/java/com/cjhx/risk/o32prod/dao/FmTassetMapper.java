package com.cjhx.risk.o32prod.dao;

public interface FmTassetMapper {

	/*
	 * 根据id查询资产单元名称
	 */
	String selectAssetNameByAssetId(String assetId);
}