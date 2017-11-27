package com.cjhx.risk.scan.dao;

import java.util.List;

import com.cjhx.risk.scan.domain.RcScan;

public interface RcScanMapper {
	
	/*
	 * 获取当前表序列ID
	 */
	String getScanSeqId();
	
    int deleteByPrimaryKey(String scanId);

    int insert(RcScan record);

    /**
     * 新增扫描日志记录
     * @param record
     * @return
     */
    int insertRcScanSelective(RcScan record);

    RcScan selectByPrimaryKey(String scanId);

    /**
     * 查询小于当前序列号的R状态的记录数
     * @param scanId
     * @return
     */
    int validateRunningScan(String scanId);
    
    /**
     * 查询手动插入待事后风控扫描的记录ID
     * @return
     */
    List<String> selectWaitingScanTask();
    
    /**
     * 更新日志记录
     * @param record
     * @return
     */
    int updateRcScanByScanIdSelective(RcScan record);
    
    /**
     * 更新手动插入任务的日志记录
     * @param record
     * @return
     */
    int updateRcScanByScanIdAndWaitingStatusSelective(RcScan record);

    int updateByPrimaryKey(RcScan record);
}