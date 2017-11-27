package com.cjhx.risk.config;

/**
 * 参数配置
 *
 * @author lujinfu
 * @date 2017年10月18日
 */
public class ParamConfig {
	
	// 事后读取证券持仓表数据l_date日期的偏移天数，（例：0为当天，-1为昨天，-2为前天）
	public static final String SCAN_DATABASE_DAY = "scan_database_day";
	
	// select DOMAIN_VALUE from RC_DOMAIN_VALUE t where
	// t.domain_id = 'control_hierarchy' 产品类型
	// 控制层次：0空() 1管理组manage 2基金fund 3资产单元Assets 4组合combi
	public static final String HIERARCHY_NONE = "0";
	public static final String HIERARCHY_MANAGE = "1";
	public static final String HIERARCHY_FUND = "2";
	public static final String HIERARCHY_ASSETS = "3";
	public static final String HIERARCHY_COMBI = "4";
	
	// 汇总类型:0单只基金同一证券 1单只基金同一公司 2单只基金 3所有基金同一证券 4所有基金同一公司 5所有基金
	public static final String SUMMARY_TYPE_SINGLE_FUND_SAME_STOCK = "0";
	public static final String SUMMARY_TYPE_SINGLE_FUND_SAME_COMPANY = "1";
	public static final String SUMMARY_TYPE_SINGLE_FUND = "2";
	public static final String SUMMARY_TYPE_ALL_FUND_SAME_STOCK = "3";
	public static final String SUMMARY_TYPE_ALL_FUND_SAME_COMPANY = "4";
	public static final String SUMMARY_TYPE_ALL_FUND = "5";

	// 指标类型RC_RULE_ITEM.RC_TYPE: 1 投资范围 2 禁止投资 3 特殊类 4不能控制类
	// 5投资限制类比例控制 6投资限制类绝对控制
	// public static final String RULE_TYPE_RESTRAINT = "0";
	public static final String RULE_TYPE_RANGE = "1";
	public static final String RULE_TYPE_FORBID = "2";
	public static final String RULE_TYPE_SPECIAL = "3";
	public static final String RULE_TYPE_UNGOVERNED = "4";
	public static final String RULE_TYPE_RATIO = "5";
	public static final String RULE_TYPE_ABSOLUTE = "6";
	
	// 查询分子范围证券的内码sql:分子加上方向的内码sql
	public static final String HIS_ADD_INNERCODE_SQL = "his_add_innercode_sql";
	// 查询分子范围证券的内码sql:分子减去方向的内码sql
	public static final String HIS_SUB_INNERCODE_SQL = "his_sub_innercode_sql";

	// 查询分子范围证券的内码sql:分子加上方向的内码sql
	public static final String ALL_HIS_ADD_INNERCODE_SQL = "all_his_add_innercode_sql";
	// 查询分子范围证券的内码sql:分子减去方向的内码sql
	public static final String ALL_HIS_SUB_INNERCODE_SQL = "all_his_sub_innercode_sql";
	
	//RC_SCAN_RULE.STATUS:0 正常; 1 错误;
	public static final String STATUS_NORMAL = "0";
	public static final String STATUS_ERROR = "1";
	
	// 0 无 1 预警 3 指令审批 2 禁止
	public static final String TRIGGER_OPERATION_NONE = "0";
	public static final String TRIGGER_OPERATION_WARNNING = "1";
	public static final String TRIGGER_OPERATION_GRANT = "3";
	public static final String TRIGGER_OPERATION_FORBID = "2";
	
	// 分子控制类型：0数量amount 1市值market value 2成本cost 3全价市值Full
	public static final String NUMERATOR_TYPE_AMOUNT = "0";
	public static final String NUMERATOR_TYPE_MARKET = "1";
	public static final String NUMERATOR_TYPE_COST = "2";
	public static final String NUMERATOR_TYPE_FULL_MARKET = "3";

	// 特殊分子： 0 净资产 1 总资产 2 融资回购 
	// 3正回购金额 4逆回购金额 5单位净值 6股指期货保证金 7股指期货多头价值 8股指期货空头价值
	public static final String SPECIAL_MOLECULE_TYPE_NET_ASSETS = "0";
	public static final String SPECIAL_MOLECULE_TYPE_TOTAL_ASSETS = "1";
	public static final String SPECIAL_MOLECULE_TYPE_BUY_BACK = "2";
	public static final String SPECIAL_MOLECULE_TYPE_REPURCHASE_AMOUNT = "3";
	public static final String SPECIAL_MOLECULE_TYPE_REVERSE_AMOUNT = "4";
	public static final String SPECIAL_MOLECULE_TYPE_NET_ASSET_VALUE = "5";
	public static final String SPECIAL_MOLECULE_TYPE_STOCK_MARGIN = "6";
	public static final String SPECIAL_MOLECULE_TYPE_MULTI_VALUE = "7";
	public static final String SPECIAL_MOLECULE_TYPE_BEARISH_VALUE = "8";
	
	// 项值：0净资产Net assets 1总资产total assets 2股票市值Stock market
	// 3债券市值Bond market 4总股本Total share 5流通股本Flow share
	// 6前一日净值（取T-1日净资产） 7过去10交易日平均份额
	public static final String ASSET_TYPE_NET_ASSETS = "0";
	public static final String ASSET_TYPE_TOTAL_ASSETS = "1";
	public static final String ASSET_TYPE_STOCK_MARKET = "2";
	public static final String ASSET_TYPE_BOND_MARKET = "3";
	public static final String ASSET_TYPE_TOTAL_SHARE = "4";
	public static final String ASSET_TYPE_FLOW_SHARE = "5";
	public static final String ASSET_TYPE_BEFORE_NET = "6";
	public static final String ASSET_TYPE_AVERAGE_SHARE = "7";
	
	//特殊类指标类型:逆回购质押率水平监控
	public static final String SPECIAL_RULE_REVERSEBUYBACK_PLEDGERATE_MONITOR = "special_rule_reversebuyback_pledgerate_monitor";

	// P 等待; R 正在扫描; C 扫描完成; E 扫描出错; N 跳过扫描; F 非交易日
	public static final String RC_SCAN_STATUS_P = "P";
	public static final String RC_SCAN_STATUS_R = "R";
	public static final String RC_SCAN_STATUS_C = "C";
	public static final String RC_SCAN_STATUS_E = "E";
	public static final String RC_SCAN_STATUS_N = "N";
	public static final String RC_SCAN_STATUS_F = "F";

	//特殊类指标类型: 回购抵押品限制
	public static final String SPECIAL_RULE_BUYBACK_COLLATERAL_RESTRICTIO = "special_rule_buyback_collateral_restrictio";

	//特殊类指标类型: 投资者持仓份额集中度控制
	public static final String SPECIAL_RULE_INVESTORS_SHARE_CONCENTRATION_CONTROL = "special_rule_investors_share_concentration_control";

	//动态维度范围和计算日期查出来的内码为空
	public static final String DIM_VCINTERCODE_NULL = "动态维度范围没有符合计算条件的内码!";
	
	
	public static final String BUYBACK_VCINTERCODE_NULL = "逆回购没有符合计算条件的明细!";
	
	
}
