
package com.ehaoyao.logistics.jd.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.ehaoyao.logistics.jd.service.ExpressInfoService;
import com.ehaoyao.logistics.jd.service.ToLogisticsCenterService;
import com.ehaoyao.logistics.jd.service.WaybillDetailService;
import com.ehaoyao.logistics.jd.service.WaybillInfoService;
import com.ehaoyao.logistics.jd.vo.OrderExpressVo;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 上午11:04:41
 * 类说明
 */
@Component("jDExpressTask")
public class JDExpressTask {
	private static final Logger logger = Logger.getLogger(JDExpressTask.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ReadConfigs configs = new ReadConfigs("jdconfig");
	//属性注入
	@Autowired
	WaybillInfoService waybillInfoService;
	@Autowired
	WaybillDetailService waybillDetailService;
	
	@Autowired
	ToLogisticsCenterService toLogisticsService;
	
	@Autowired
	ExpressInfoService expressInfoService;
	
	/**
	 * 将订单中心的运单写入到物流中心
	 */
	public void insertWayBill(){
		int insCount = 0 ;//每次最终成功插入条数
		int totalInsCount = 0;//累计最终成功插入条数
		int dealCount = configs.getInteger("commitCount");//每次处理条数
		int count = 1;//当前处理次数
		long startTime = System.currentTimeMillis();
		logger.info("【从订单中心抓取订单至物流中心，开始时间："+sdf.format(startTime)+"】");
		try {
			long queryStartTime = System.currentTimeMillis();
			//从订单中心获取已配送的订单信息集合
			List<OrderExpressVo> orderExpressList = expressInfoService.selectExpressInfoList();
			long queryEndTime = System.currentTimeMillis();
			logger.info("【从订单中心获取已配送的订单信息集合，共耗时："+(queryEndTime-queryStartTime)/1000+"s】");
			for(int i = 0;i<orderExpressList.size();i+=dealCount){
				List  subList = null;
				if((i+dealCount)>orderExpressList.size()){
					subList = orderExpressList.subList(i,orderExpressList.size());
				}else{
					subList = orderExpressList.subList(i,i+dealCount);
				}
				System.out.println(i+"---"+orderExpressList.size()+"--------"+(i+subList.size()));
				//	将订单中心查询到的已配送的订单及运单号等信息插入物流中心
				if(subList!=null&&!subList.isEmpty()){
					insCount = (Integer) toLogisticsService.insertLogisticsCenter(subList);
					logger.info("【共从订单中心获取"+orderExpressList.size()+"条记录，每次处理"+dealCount+"条，共需处理"+((orderExpressList.size()-1)/dealCount+1)+"次，当前处理第"+count+"次，当前成功插入"+insCount+"条，累计成功插入"+totalInsCount+"条】");
					totalInsCount+=insCount;
					count++;//每次处理后加1
				}
			}
		} catch (Exception e) {
			logger.error("【从订单中心抓取订单至物流中心，异常！异常信息：】"+e);
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		logger.info("【从订单中心抓取订单至物流中心，结束时间："+sdf.format(endTime)+",一共耗时："+(endTime-startTime)/1000.0+"s，本次任务共成功插入"+totalInsCount+"条记录】");
	}
	/**
	 * 调用京东的API,更新未妥投的运单信息
	 */
	@Test
	public void updateWayBillToLogisticsDB(){
		Date startDate=new Date();
		logger.info("开始更新京东的未妥投、未拒收运单！开始时间："+sdf.format(startDate));
		/*1、抓取创建日期5天内、物流中心的未妥投、未拒收运单号集合*/
		int dt=configs.getInteger("NormalStartDate");
		String waybillSource="jd";
		ArrayList<String> waybillStatusList = new ArrayList<String>();
		//1.1'运单状态  s00:初始 s01:揽件 s02:配送中 s03:拒收 s04:妥投'
		waybillStatusList.add("s00");
		waybillStatusList.add("s01");
		waybillStatusList.add("s02");
		List<String> waybillNumbers=new ArrayList<String>();
		try {
			waybillNumbers = waybillInfoService.getWaybillNumbers(dt,waybillSource, waybillStatusList);
		} catch (Exception e) {
			logger.error("抓取京东的未妥投、未拒收运单程序异常！！",e);
		}
		logger.info("本次从物流中心抓取未妥投、未拒收单子个数："+waybillNumbers.size()+"个!!");
		/*2、遍历运单号集合,调用京东API获取每个运单的物流信息，并更新到物流中心*/
		int successCount=0;
		for (String waybillNumber : waybillNumbers) {
			int updateWaybillByJD=0;
			try {
				updateWaybillByJD = waybillInfoService.updateWaybillByJD(waybillNumber);
			} catch (Exception e) {
				logger.error("更新京东的未妥投、未拒收运单的物流信息程序异常！！ 运单号："+waybillNumber,e);
			}				
			if(updateWaybillByJD==1){
				successCount++;
			}
		}
		Date endDate=new Date();
		logger.info("更新京东的未妥投、未拒收运单结束！结束时间："+sdf.format(startDate)+",一共耗时："+(endDate.getTime()-startDate.getTime())/1000.0+"s/n"
				+ "从物流中心抓取未妥投、未拒收单子个数："+waybillNumbers.size()+"个!!"+"成功更新物流跟踪信息运单数："+successCount+"个");
	}
	
	public void dealLogic(){
		System.out.println(sdf.format(new Date()));
	}
}
