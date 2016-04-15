package com.ehaoyao.logistics.jd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehaoyao.logistics.common.utils.DateUtil;
import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.ehaoyao.logistics.jd.mapper.logisticscenter.WayBillDetailMapper;
import com.ehaoyao.logistics.jd.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.jd.mapper.ordercenter.ExpressInfoMapper;
import com.ehaoyao.logistics.jd.model.logisticscenter.WayBillDetail;
import com.ehaoyao.logistics.jd.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.jd.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.jd.vo.OrderExpressVo;

@Transactional(value="transactionManagerLogisticsCenter")
@Service(value="toLogisticsCenterService")
public class ToLogisticsCenterServiceImpl implements ToLogisticsCenterService {
	
	private static final Logger logger = Logger.getLogger(ToLogisticsCenterServiceImpl.class);
	private static final ReadConfigs jdConfig = new ReadConfigs("jdconfig");
	private static ResourceBundle bundle = ResourceBundle.getBundle("express");
	private static ResourceBundle bundle1 = ResourceBundle.getBundle("orderCodeToKD100Code");
	private static ResourceBundle bundle2 = ResourceBundle.getBundle("expressIDtoKD100Code");
	@Autowired
	ExpressInfoMapper expressInfoMapper;
	
	@Autowired
	WayBillInfoMapper wayBillInfoMapper;
	
	@Autowired
	WayBillDetailMapper wayBillDetailMapper;
	
	@Resource(name="sqlSessionTemplateOrderCenter")
	private SqlSessionTemplate orderSqlSessionTemplate;
	
	@Resource(name="sqlSessionTemplateLogisticsCenter")
	private SqlSessionTemplate logisticsSqlSessionTemplate;
	
	
	@Override
	public Object insertWayBill() throws Exception {
		List<OrderExpressVo> orderExpressList;
		int insWayBillInfoCount = 0;
		
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
		for(int i = 0;i<orderExpressList.size();i+=100){
		    List  newlist = orderExpressList.subList(i,i+99);
		    insertLogisticsCenter(newlist);
		}
		//2,	将订单中心查询到的已配送的订单及运单号等信息插入物流中心
		insWayBillInfoCount = (int) insertLogisticsCenter(orderExpressList);
		return insWayBillInfoCount;
	}
	
	private static AtomicInteger line = new AtomicInteger(0);
    static ExecutorService pool = Executors.newFixedThreadPool(10);
	    
	public void dealSchedule(final List<OrderExpressVo> orderExpressList){
		
		for (int i = 0;i<10;i++){
			Thread thread = new Thread(){
				Integer num;

				@Override
				public void run() {
					  System.out.println("线程:" + Thread.currentThread().getName());
					try {
						num = (Integer) insertLogisticsCenter(orderExpressList);
					} catch (Exception e) {
						e.printStackTrace();
					}
					  System.out.println("startline = " +(num-1000)+",endline = " + num);
					  
				}
				
			};
            pool.execute(thread);
        }
        pool.shutdown();
	}
	
	/**
	 * 将订单中心查询到的已配送的订单及运单号等信息插入物流中心
	 * @param orderExpressList
	 * @return
	 * @throws Exception
	 */
	public Object insertLogisticsCenter(List<OrderExpressVo> orderExpressList) throws Exception{
		WayBillInfo wayBillInfo;
		WayBillDetail wayBillDetail;
		List<WayBillInfo> wayBillInfoList ;
		List<WayBillInfo> wayBillInfoTempList;
		List<WayBillDetail> wayBillDetailList;
		String wayBillSource; //运单来源标识
		String repeatOrderNums = "";//从订单获取的已配送的初始订单，但在物流中心库中已存在的订单拼串
		String wayBillSourceIsNull = "";//运单来源为空或未配置的订单拼串
		int insWayBillInfoCount = 0;
		Date currDate = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		wayBillInfoList = new ArrayList<WayBillInfo>();
		wayBillDetailList = new ArrayList<WayBillDetail>();
		
		for(OrderExpressVo orderExpress : orderExpressList){
			//1,获取快递来源
			wayBillSource = getSource(orderExpress.getExpressComName(),orderExpress.getExpressComCode(), orderExpress.getExpressComId());
			if(wayBillSource==null || wayBillSource.trim().length()==0){
				wayBillSourceIsNull += (orderExpress.getOrderFlag()+orderExpress.getOrderNumber());
				continue;
			}

			//2,判断订单在物流中心库中是否重复订单
			map.put("orderFlag", orderExpress.getOrderFlag());
			map.put("orderNumber", orderExpress.getOrderNumber());
			map.put("waybillSource", wayBillSource);
			map.put("waybillNumber", orderExpress.getExpressId());
			wayBillInfoTempList = wayBillInfoMapper.selectWayBillInfoListByCondition(map);
			if(wayBillInfoTempList!=null&&!wayBillInfoTempList.isEmpty()){
				repeatOrderNums += orderExpress.getOrderNumber()+",";
				continue;
			}
			//3,保存物流主表
			wayBillInfo = new WayBillInfo();
			wayBillInfo.setOrderFlag(orderExpress.getOrderFlag());
			wayBillInfo.setOrderNumber(orderExpress.getOrderNumber());
			wayBillInfo.setWaybillSource(wayBillSource);
			wayBillInfo.setWaybillNumber(orderExpress.getExpressId());
			wayBillInfo.setIsWriteback(0);
			wayBillInfo.setWaybillStatus(WayBillInfo.WAYBILL_INFO_STATUS_INIT);
			wayBillInfo.setCreateTime(currDate);
//			wayBillInfo.setLastTime(currDate);
			wayBillInfoList.add(wayBillInfo);
			
			//4,保存物流明细表
			wayBillDetail = new WayBillDetail();
			wayBillDetail.setWaybillSource(wayBillSource);
			wayBillDetail.setWaybillNumber(orderExpress.getExpressId());
			wayBillDetail.setWaybillStatus(WayBillInfo.WAYBILL_INFO_STATUS_INIT);
			wayBillDetail.setCreateTime(currDate);
			wayBillDetailList.add(wayBillDetail);
		}
		if(wayBillSourceIsNull!=null && wayBillSourceIsNull.trim().length()>0){
			logger.info("【从订单中心抓取初始订单至物流中心任务，运单来源为空或未配置的订单拼串信息："+repeatOrderNums+"】");
		}
		if(repeatOrderNums!=null && repeatOrderNums.trim().length()>0){
			logger.info("【从订单中心抓取初始订单至物流中心任务，重复订单号："+repeatOrderNums+"】");
		}
		
		if(wayBillInfoList!=null && !wayBillInfoList.isEmpty()){
			insWayBillInfoCount = wayBillInfoMapper.insertWayBillInfoBatch(wayBillInfoList);
		}
		if(wayBillDetailList!=null && !wayBillDetailList.isEmpty()){
			wayBillDetailMapper.insertWayBillDetailBatch(wayBillDetailList);
		}
		return insWayBillInfoCount;
	}

	/**
	 * 获取运单来源标识
	 * @param expressComName
	 * @param expressComCode
	 * @param expressComID
	 * @return
	 */
	public String getSource(String expressComName, String expressComCode, String expressComID) throws Exception{
		String source ;
		/*
		 * logger.info("快递公司ID：" + order.getExpressComId());
		 */
		if (null != expressComID && !"".equals(expressComID.trim())) {
			try {
				source = bundle2.getString(expressComID.trim());
				if(source!=null && source.trim().length()>0){
					return source;
				}
			} catch (Exception e) {
			}
		}
		/*
		 * logger.info("快递公司编码：" + order.getExpressComCode());
		 */
		if (null != expressComCode && !"".equals(expressComCode.trim())) {
			try {
				source = bundle1.getString(expressComCode.trim());
				if(source!=null && source.trim().length()>0){
					return source;
				}
			} catch (Exception e) {
			}
		}
		/*
		 * logger.info("快递公司名称：" + order.getExpressComName());
		 */
		if (null != expressComName && !"".equals(expressComName.trim())) {
			try {
				source = bundle.getString(expressComName.trim());
				if(source!=null && source.trim().length()>0){
					return source;
				}
			} catch (Exception e) {
			}
		} 
		source = "unknow";
		return source;
	}
	
	
	

}
