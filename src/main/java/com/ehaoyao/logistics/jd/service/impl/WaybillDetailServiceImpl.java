/**
 * 
 */
package com.ehaoyao.logistics.jd.service.impl;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehaoyao.logistics.jd.mapper.logisticscenter.WayBillDetailMapper;
import com.ehaoyao.logistics.jd.mapper.logisticscenter.WayBillInfoMapper;
import com.ehaoyao.logistics.jd.model.logisticscenter.WayBillDetail;
import com.ehaoyao.logistics.jd.service.WaybillDetailService;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月12日 下午5:11:23
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
@Service(value="waybillDetailService")
public class WaybillDetailServiceImpl implements WaybillDetailService {
	private static final Logger logger = Logger.getLogger(WaybillInfoServiceImpl.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	WayBillInfoMapper waybillInfoMapper;
	@Autowired
	WayBillDetailMapper wayBillDetailMapper;
	/**
	* @Description:保存单个运单跟踪信息
	* @param @param waybillDetail
	* @param @return
	* @return 
	* @throws
	*/ 
	@Override
	public int insertWayBillDetail(WayBillDetail waybillDetail) throws Exception{	
		return wayBillDetailMapper.insert(waybillDetail);
	}
	
}
