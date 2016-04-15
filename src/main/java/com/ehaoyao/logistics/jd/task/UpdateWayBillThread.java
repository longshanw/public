
package com.ehaoyao.logistics.jd.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.logistics.jd.service.WaybillInfoService;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月15日 下午3:22:26
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public class UpdateWayBillThread extends Thread {
	private static final Logger logger = Logger.getLogger(UpdateWayBillThread.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<String> waybillNumberList;
	private  WaybillInfoService  waybillInfoService;
	public void run(){
		Date startDate = new Date();
		logger.info(Thread.currentThread().getName()+"本次更新京东的未妥投、未拒收运单的跟踪信息线程开始！开始时间："+sdf.format(startDate)+"抓取未妥投、未拒收运单数"+waybillNumberList.size());
		int successCount=0;
		for (String waybillNumber : waybillNumberList) {
			int updateWaybillByJD=0;
			try {
				updateWaybillByJD = waybillInfoService.updateWaybillByJD(waybillNumber);
			} catch (Exception e) {
				logger.error(Thread.currentThread().getName()+"更新京东的未妥投、未拒收运单的物流信息程序异常！！ 运单号："+waybillNumber,e);
			}				
			if(updateWaybillByJD==1){
				successCount++;
			}
		}
		Date endDate = new Date();
		logger.info(Thread.currentThread().getName()+"线程结束");
		logger.info(Thread.currentThread().getName()+"本次线程更新京东的未妥投、未拒收运单结束！结束时间："+sdf.format(endDate)+",一共耗时："+(endDate.getTime()-startDate.getTime())/1000.0+"s"
				+ "从物流中心抓取未妥投、未拒收单子个数："+waybillNumberList.size()+"个!!"+"成功更新物流跟踪信息运单数："+successCount+"个");
	}
	public List<String> getWaybillNumberList() {
		return waybillNumberList;
	}
	public void setWaybillNumberList(List<String> waybillNumberList) {
		this.waybillNumberList = waybillNumberList;
	}
	public WaybillInfoService getWaybillInfoService() {
		return waybillInfoService;
	}
	public void setWaybillInfoService(WaybillInfoService waybillInfoService) {
		this.waybillInfoService = waybillInfoService;
	}
}
