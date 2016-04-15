package com.ehaoyao.logistics.jd.mapper.logisticscenter;

import java.util.List;
import java.util.Map;

import com.ehaoyao.logistics.jd.model.logisticscenter.WayBillInfo;
import com.ehaoyao.logistics.jd.vo.WayBillInfoVo;

public interface WayBillInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WayBillInfo record);

    int insertSelective(WayBillInfo record);

    WayBillInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayBillInfo record);

    int updateByPrimaryKey(WayBillInfo record);    
    /**
     * 批量保存运单信息
     * @param wayBillInfoList
     * @return
     */
    int insertWayBillInfoBatch(List<WayBillInfo> wayBillInfoList);
    /**
     * 
    * @Description:根据开始时间、结束时间、运单状态集合 查询运单号集合
    * @param @param billInfoVo
    * @param @return
    * @return List<String>
    * @throws
     */
	List<String> queryWayBillNumbers(WayBillInfoVo billInfoVo);
	/**
	 * 
	* @Description:根据运单号获取 运单Info
	* @param @param waybillNumber
	* @param @return
	* @return WayBillInfo
	* @throws
	 */
	WayBillInfo selectByWayBillNumber (String waybillNumber);
   
    /**
   	 * 根据条件获取物流信息集合
   	 * @param map	条件集合 	
   	 * @return
   	 * @throws Exception
   	 */
    List<WayBillInfo> selectWayBillInfoListByCondition(Map<String, Object> map) throws Exception;
       
}