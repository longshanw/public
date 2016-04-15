package com.ehaoyao.logistics.jd.mapper.ordercenter;

import java.util.List;
import java.util.Map;

import com.ehaoyao.logistics.jd.model.ordercenter.ExpressInfo;
import com.ehaoyao.logistics.jd.vo.OrderExpressVo;

/**
 * 订单中心-物流信息表数据持久化接口
 * @date 2016-04-12
 */
public interface ExpressInfoMapper{
    int deleteByPrimaryKey(Long id) throws Exception;

    int insert(ExpressInfo record) throws Exception;

    int insertSelective(ExpressInfo record) throws Exception;

    ExpressInfo selectByPrimaryKey(Long id) throws Exception;

    int updateByPrimaryKeySelective(ExpressInfo record) throws Exception;

    int updateByPrimaryKey(ExpressInfo record) throws Exception;
    
    /**
	 * 根据条件获取物流信息集合
	 * @param map	条件集合 	
	 * @return
	 * @throws Exception
	 */
    List<ExpressInfo> selectExpressInfoListByCondition(Map<String, Object> map) throws Exception;
    
    /**
	 * 根据条件获取物流信息集合
	 * @param map	条件集合 	
	 * @return
	 * @throws Exception
	 */
    List<OrderExpressVo> selectHasShipByCondition(Map<String, Object> map) throws Exception;
    
}