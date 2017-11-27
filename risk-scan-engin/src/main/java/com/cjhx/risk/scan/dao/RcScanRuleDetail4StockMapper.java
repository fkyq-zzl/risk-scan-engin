package com.cjhx.risk.scan.dao;

import com.cjhx.risk.scan.domain.RcScanRuleDetail4Stock;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;

public interface RcScanRuleDetail4StockMapper {
    int insert(RcScanRuleDetail4Stock record);

    int insertSelective(RcScanRuleDetail4Stock record);
    
    /**
     * 保存明细记录
     * @return
     */
    int insertRcScanRuleDetailValueSelective(RcScanRuleDetailValue detail);
    
}