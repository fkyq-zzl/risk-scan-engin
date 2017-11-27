package com.cjhx.risk.o32prod.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhx.risk.config.ParamConfig;
import com.cjhx.risk.o32prod.dao.FmTassetMapper;
import com.cjhx.risk.o32prod.dao.FmTcombiMapper;
import com.cjhx.risk.o32prod.dao.FmTfundinfoMapper;
import com.cjhx.risk.o32prod.domain.FmTfundinfo;
import com.cjhx.risk.o32prod.service.O32ProductStockService;
import com.cjhx.risk.scan.model.ProductModel;
import com.cjhx.risk.scan.riskcheck.CheckUnit;

/**
 * 查询O32数据云表记录Service实现类
 *
 * @author lujinfu
 * @date 2017年10月18日
 */
@Service("o32ProductStockService")
public class O32ProductStockServiceImpl implements O32ProductStockService {

	@Autowired
	private FmTfundinfoMapper fmTfundinfoMapper;
	@Autowired
	private FmTassetMapper fmTassetMapper;
	@Autowired
	private FmTcombiMapper fmTcombiMapper;

	@Override
	public void setCheckUnitProductionName(List<CheckUnit> list) {
		String productionId, productionType, productionCode = null, productionName = null;
		FmTfundinfo fund;
		//性能优化，已查询过的基金直接取不作重复查询 
		HashMap<String,FmTfundinfo> fundMap = new HashMap<String,FmTfundinfo>();
		for (CheckUnit unit : list) {
			for (ProductModel product : unit.getProductList()){
				productionId = product.getProductionId();
				productionType = product.getProductionType();
				if (ParamConfig.HIERARCHY_NONE.equals(productionType)) {
					productionName = "汇总控制";
				}else if (ParamConfig.HIERARCHY_FUND.equals(productionType)) {
					if(fundMap.containsKey(productionId)){
						fund = fundMap.get(productionId);
					}else{
						fund = fmTfundinfoMapper.selectFundInfoByFundId(productionId);
						if(fund == null || fund.getVcFundCode() == null){
							product.setIsError(true);
							continue;
						}
						fundMap.put(productionId, fund);
					}
					productionName = fund.getVcFundName();
					productionCode = fund.getVcFundCode();
					
				}else if (ParamConfig.HIERARCHY_ASSETS.equals(productionType)) {
					//productionName = fmTassetMapper.selectAssetNameByAssetId(productionId);
					// 业务暂无资产单元
					product.setIsError(true);
					continue;
				}else if (ParamConfig.HIERARCHY_COMBI.equals(productionType)) {
					//productionName = fmTcombiMapper.selectCombiNameByCombiNo(productionId);
					// 业务暂无资产组合
					product.setIsError(true);
					continue;
				}
				product.setProductionName(productionName);
				product.setProductionCode(productionCode);
			}
		}
	}
	
	@Override
	public String selectFundCodeByFundId(String fundId){
		return fmTfundinfoMapper.selectFundCodeByFundId(fundId);
	}

	@Override
	public Integer callTradingDay(int l_date,int l_offset_days,int l_mode,String c_market_no){
		return fmTfundinfoMapper.callTradingDay(l_date, l_offset_days, l_mode, c_market_no);
	}
	
}
