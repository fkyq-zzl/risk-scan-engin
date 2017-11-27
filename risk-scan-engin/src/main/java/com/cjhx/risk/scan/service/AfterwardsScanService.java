package com.cjhx.risk.scan.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.cjhx.risk.scan.domain.RcRuleItem;
import com.cjhx.risk.scan.riskcheck.CheckUnit;

/**
 * 事后扫描风控Service
 *
 * @author lujinfu
 * @date 2017年10月17日
 */
public interface AfterwardsScanService {

	/**
	 * 记录开始日终扫描日志到RcScan表
	 * @return 记录成功则返回ScanId，记录失败则返回null
	 */
	String startScanLog();

	/**
	 * 记录结束日终扫描日志到RcScan表
	 * @param scanId
	 * @param isError
	 * @return
	 */
	boolean endScanLog(String scanId, String status);

	/**
	 * 扫描指标并根据产品个数保存记录到RcScanRule表
	 * @param scanId
	 * @return
	 */
	Map<RcRuleItem, List<CheckUnit>> scanRule(int scanDatabaseDate,String scanId);

	/**
	 * 处理最后指标触发结果并记录
	 * @param scanId
	 * @throws ParseException
	 */
	void handleScanResult(String scanId) throws ParseException;

	/**
	 * 查询手动插入待事后风控扫描的记录ID
	 * @return
	 */
	List<String> selectWaitingScanTask();

	/**
	 * 更新手动插入任务的日志记录为开始扫描
	 * @param scanId
	 * @return
	 */
	boolean updateWaitingScanTask(String scanId);

	/**
	 * 校验当前是否有未完成的任务
	 * @param scanId
	 * @return
	 */
	boolean valitateRunningScan(String scanId);
	
}
