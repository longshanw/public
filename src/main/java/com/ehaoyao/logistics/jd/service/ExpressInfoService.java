package com.ehaoyao.logistics.jd.service;

import java.util.List;

import com.ehaoyao.logistics.jd.vo.OrderExpressVo;

public interface ExpressInfoService {

	/**
	 * 从订单中心根据条件获取已配送的订单/运单信息集合
	 * @param map	条件集合 	
	 * @return
	 * @throws Exception
	 */
    List<OrderExpressVo> selectExpressInfoList() throws Exception;
}
