package com.cjhx.risk.scan.dao;

import com.cjhx.risk.scan.domain.RcScanRuleDetail1Stock;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;

public interface RcScanRuleDetail1StockMapper {
    int insert(RcScanRuleDetail1Stock record);

    int insertSelective(RcScanRuleDetail1Stock record);
    
    /**
     * 保存明细记录
     * @return
     */
    int insertRcScanRuleDetailValueSelective(RcScanRuleDetailValue detail);
}