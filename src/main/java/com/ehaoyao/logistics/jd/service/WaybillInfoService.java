
package com.ehaoyao.logistics.jd.service;

import java.util.List;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:07:46
 * 类说明
 */
public interface WaybillInfoService {
	/**
	 * 
	* @Description:查询 date天内、waybillSource 、waybillStatus多个状态的运单号集合
	* @param @return
	* @return List<String>
	* @throws
	 */
	public List<String> getWaybillNumbers(int date,String waybillSource,List<String> waybillStatusList)throws Exception;
	/**
	 * 
	* @Description:调用JDAPI,更新运单WayBillInfo和WayBillDetail到物流中心数据库
	* @param @param waybillNumber
	* @param @return
	* @return 
	* @throws
	 */
	public int updateWaybillByJD(String waybillNumber) throws Exception;
}
