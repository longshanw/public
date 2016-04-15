/**
 * 
 */
package com.ehaoyao.logistics.jd.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xushunxing 
 * @version 创建时间：2016年4月13日 上午10:25:44
 * 类说明
 */
/**
 * @author xushunxing
 *
 */
public class WayBillInfoVo {
   private Long id;

    private String orderFlag;

    private String orderNumber;

    private String waybillSource;

    private String waybillNumber;

    private String waybillStatus;

    private Integer isWriteback;

    private Date lastTime;

    private Date createTime;
    
    private Date startTime;
    
    private Date endTime;
    

	private List<String> waybillStatusList;


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getWaybillSource() {
        return waybillSource;
    }

    public void setWaybillSource(String waybillSource) {
        this.waybillSource = waybillSource == null ? null : waybillSource.trim();
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber == null ? null : waybillNumber.trim();
    }

    public String getWaybillStatus() {
        return waybillStatus;
    }

    public void setWaybillStatus(String waybillStatus) {
        this.waybillStatus = waybillStatus == null ? null : waybillStatus.trim();
    }

    public Integer getIsWriteback() {
        return isWriteback;
    }

    public void setIsWriteback(Integer isWriteback) {
        this.isWriteback = isWriteback;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getWaybillStatusList() {
		return waybillStatusList;
	}

	public void setWaybillStatusList(List<String> waybillStatusList) {
		this.waybillStatusList = waybillStatusList;
	}

	public Date getStartTime() {
    	return startTime;
    }
    
    public void setStartTime(Date startTime) {
    	this.startTime = startTime;
    }
    
    public Date getEndTime() {
    	return endTime;
    }
    
    public void setEndTime(Date endTime) {
    	this.endTime = endTime;
    }
}
