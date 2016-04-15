package com.ehaoyao.logistics.jd.service;

/**
 * 订单中心-物流信息表数据业务接口
 * @date 2016-04-12
 */
public interface ToLogisticsCenterService {

	/**
	 * 根据条件获取物流信息集合
	 * @param map	条件集合 	
	 * @return
	 * @throws Exception
	 */
    Object insertWayBill() throws Exception;

}
