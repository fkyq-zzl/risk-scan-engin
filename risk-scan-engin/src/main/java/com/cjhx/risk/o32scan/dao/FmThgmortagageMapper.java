package com.cjhx.risk.o32scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32scan.domain.FmThgmortagage;

public interface FmThgmortagageMapper {

	List<FmThgmortagage> selectBuyBackDetails(@Param("lSerialNo") String lSerialNo);
	
	List<FmThgmortagage> selectDetails(@Param("lSerialNo") String lSerialNo,@Param("lDate") Integer lDate);
	
}
