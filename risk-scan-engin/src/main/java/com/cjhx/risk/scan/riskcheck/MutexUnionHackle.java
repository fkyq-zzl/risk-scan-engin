package com.cjhx.risk.scan.riskcheck;

import com.cjhx.risk.scan.dao.RcRuleItemMapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail0Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail1Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail3Mapper;
import com.cjhx.risk.scan.dao.RcScanRuleDetail4Mapper;
import com.cjhx.risk.system.SpringUtil;

/**
 * 互斥联合检查类
 *
 * @author lujinfu
 * @date 2017年10月23日
 */
public class MutexUnionHackle {
	
	private static RcScanRuleDetail0Mapper rcScanRuleDetail0Mapper = (RcScanRuleDetail0Mapper) SpringUtil
			.getBean("rcScanRuleDetail0Mapper");
	private static RcScanRuleDetail1Mapper rcScanRuleDetail1Mapper = (RcScanRuleDetail1Mapper) SpringUtil
			.getBean("rcScanRuleDetail1Mapper");
	private static RcScanRuleDetail3Mapper rcScanRuleDetail3Mapper = (RcScanRuleDetail3Mapper) SpringUtil
			.getBean("rcScanRuleDetail3Mapper");
	private static RcScanRuleDetail4Mapper rcScanRuleDetail4Mapper = (RcScanRuleDetail4Mapper) SpringUtil
			.getBean("rcScanRuleDetail4Mapper");
	
	private static RcRuleItemMapper rcRuleItemMapper = (RcRuleItemMapper) SpringUtil.getBean("rcRuleItemMapper");
	
	/**
	 * 互斥检查
	 * (刚业务说相同产品下，单基金可以互斥单基金xxx，其它只互斥相同汇总类型的)
	 * @param unitList
	 * @param rcRuleItemDao
	 *            查询互斥指标集
	 */
	public static void mutexHackle(String scanId) {
		//单基金同一证券互斥 :相同证券在不同指标相同产品之间互斥               TODO :  执行时间为33秒，待优化
		rcScanRuleDetail0Mapper.excludeSingleFundSameStockByMutex(scanId);
		
		//(汇总类型之间互斥)
		//单基金同一公司互斥 :相同公司在不同指标相同产品之间互斥
		rcScanRuleDetail1Mapper.excludeSingleFundSameCompanyByMutex(scanId);
		//所有基金同一证券互斥 :相同证券在不同指标之间互斥
		rcScanRuleDetail3Mapper.excludeAllFundSameStockByMutex(scanId);
		//所有基金同一公司互斥 :相同公司在不同指标之间互斥
		rcScanRuleDetail4Mapper.excludeAllFundSameCompanyByMutex(scanId);

	}

	/**
	 * 联合检查
	 * 
	 * @param unitList
	 * @param rcRuleItemDao
	 *            查询联合指标集
	 */
	public static void unionHackle(String scanId) {
		//如果有联合排除，则可能会对已通过联合的记录产生影响，需要重复执行，直到没有影响的记录
		
		int i = 1;
		//单基金同一证券联合:
		while(i>0){
			i = 0;
			i = rcScanRuleDetail0Mapper.excludeSingleFundSameStockByUnion(scanId);
		}

		//(汇总类型之间联合)
		i = 1;
		//单基金同一公司联合:与相同汇总类型指标联合
		while(i>0){
			i = 0;
			i = rcScanRuleDetail1Mapper.excludeSingleFundSameCompanyByUnion(scanId);
		}
		i = 1;
		//所有基金同一证券联合:与相同汇总类型指标联合
		while(i>0){
			i = 0;
			i = rcScanRuleDetail3Mapper.excludeAllFundSameStockByUnion(scanId);
		}
		i = 1;
		//所有基金同一公司联合:与相同汇总类型指标联合
		while(i>0){
			i = 0;
			i = rcScanRuleDetail4Mapper.excludeAllFundSameCompanyByUnion(scanId);
		}

	}
}
