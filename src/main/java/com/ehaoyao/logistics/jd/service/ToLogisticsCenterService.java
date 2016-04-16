package com.ehaoyao.logistics.jd.service;

import java.util.List;

import com.ehaoyao.logistics.jd.vo.OrderExpressVo;

/**
 * 订单中心-物流信息表数据业务接口
 * @date 2016-04-12
 */
public interface ToLogisticsCenterService {

    /**
     * 保存订单/运单信息至物流中心
     * @param expressVoList
     * @return
     * @throws Exception
     */
    Object insertLogisticsCenter(List<OrderExpressVo> orderExpressList) throws Exception;

}
