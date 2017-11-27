package com.cjhx.risk.o32scan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjhx.risk.o32scan.domain.FmThgInfo;


public interface FmThgregisterMapper {

	/**
	 * @author: chencang
	 * @description: 查询符合要求的逆回购信息
	 * @createTime: 2017年11月15日 上午10:24:15
	 * @return
	 */
	List<FmThgInfo> selectThgInfos(@Param("fundIds") List<String> fundIds,@Param("date") Integer date);
}
