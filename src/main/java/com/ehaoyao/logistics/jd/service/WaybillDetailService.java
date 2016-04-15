/**
 * 
 */
package com.ehaoyao.logistics.jd.service;

import com.ehaoyao.logistics.jd.model.logisticscenter.WayBillDetail;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:10:28
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public interface WaybillDetailService{
	/**
	 * 
	* @Description:保存单个运单跟踪信息
	* @param @param waybillDetail
	* @param @return
	* @return int
	* @throws
	 */
	int insertWayBillDetail (WayBillDetail waybillDetail)throws Exception;
}
