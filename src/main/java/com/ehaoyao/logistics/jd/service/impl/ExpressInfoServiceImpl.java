package com.ehaoyao.logistics.jd.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.ehaoyao.logistics.jd.mapper.ordercenter.ExpressInfoMapper;
import com.ehaoyao.logistics.jd.service.ExpressInfoService;
import com.ehaoyao.logistics.jd.vo.OrderExpressVo;

@Transactional(value="transactionManagerOrderCenter")
@Service(value="expressInfoService")
public class ExpressInfoServiceImpl implements ExpressInfoService {

	private static final Logger logger = Logger.getLogger(ExpressInfoServiceImpl.class);
	private static final ReadConfigs jdConfig = new ReadConfigs("jdconfig");
	
	@Autowired
	ExpressInfoMapper expressInfoMapper;
	
	
	@Resource(name="sqlSessionTemplateOrderCenter")
	private SqlSessionTemplate orderSqlSessionTemplate;
	
	
	@Override
	public List<OrderExpressVo> selectExpressInfoList() throws Exception {
		List<OrderExpressVo> orderExpressList;
		
		//1,	从订单中心获取已配送的初始订单
		int orderIntervalTime = jdConfig.getInteger("jd_normal_tologistics_minute");
		String startTime=DateUtil.getDate(DateUtil.getPreMinute(orderIntervalTime),2,null);//当前时间向前推迟xxx分钟
		String endTime=DateUtil.getDate(new Date(), 2, null);
		
		Map<String,Object> map = new HashMap<String,Object>();
		String[] orderStatusArr = {"s01","s02"};
		map.put("orderStatusArr", orderStatusArr);
		map.put("wayBillTimeStart", startTime);
		map.put("wayBillTimeEnd", endTime);
		logger.info("【从订单中心获取已配送的初始订单，查询条件:"+map+"】");
		orderExpressList = expressInfoMapper.selectHasShipByCondition(map);
		
		return orderExpressList;
	}

}
