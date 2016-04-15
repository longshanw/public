package com.ehaoyao.logistics.jd.mapper.logisticscenter;

import java.util.List;

import com.ehaoyao.logistics.jd.model.logisticscenter.WayBillDetail;

public interface WayBillDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WayBillDetail record);

    int insertSelective(WayBillDetail record);

    WayBillDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayBillDetail record);

    int updateByPrimaryKey(WayBillDetail record);
    
    /**
     * 批量保存运单明细信息
     * @param wayBillDetailList
     * @return
     */
    int insertWayBillDetailBatch(List<WayBillDetail> wayBillDetailList);
}