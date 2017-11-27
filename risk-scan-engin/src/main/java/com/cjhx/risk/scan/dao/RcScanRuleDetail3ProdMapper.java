package com.cjhx.risk.scan.dao;

import com.cjhx.risk.scan.domain.RcScanRuleDetail3Prod;
import com.cjhx.risk.scan.model.RcScanRuleDetailValue;

public interface RcScanRuleDetail3ProdMapper {
    int insert(RcScanRuleDetail3Prod record);

    int insertSelective(RcScanRuleDetail3Prod record);
    
    /**
     * 保存明细记录
     * @return
     */
    int insertRcScanRuleDetailValueSelective(RcScanRuleDetailValue detail);
    
}