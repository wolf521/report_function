package com.issmart.device.model;

import java.util.Date;

import com.issmart.common.util.StringUtil;

/**
 * 展台详情请求参数
 * 
 * @author Administrator
 *
 */
public class RequestParamOfBoothDetails {
	
	/**
	 * deviceMac
	 */
	private String deviceMac;
	
	/**
	 * 查询时间段
	 */
	private Integer quantumTime;
	
	/**
	 * 查询时间单位：HH 小时
	 */
	private String quantumUnit;
	
	/**
	 * 查询开始时间
	 */
	private Date startTime;
	
	/**
	 * 查询截止时间
	 */
	private Date endTime;
	
	/**
	 * 查询开始时间戳
	 */
	private Long startTimeStamp;;
	
	/**
	 * 查询截止时间戳
	 */
	private Long endTimeStamp;
	
	/**
	 * unitId
	 */
	private String unitId;
	
	/**
	 * unitId
	 */
	private Integer boothId;
	
	/**
	 * 表名称
	 */
	private String tableName;
	
	/**
	 * 分区信息
	 */
	private String partitionName;

	public RequestParamOfBoothDetails() {
		super();
	}

	public RequestParamOfBoothDetails(Integer boothId, Integer quantumTime, String quantumUnit,
			Long endTimeStamp, String unitId) {
		super();
		this.boothId = boothId;
		this.quantumTime = quantumTime;
		this.quantumUnit = quantumUnit;
		this.endTimeStamp = endTimeStamp;
		this.unitId = unitId;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public String getTableName() {
		return StringUtil.getReportTableName(this.unitId);
	}

	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public Integer getQuantumTime() {
		return quantumTime;
	}

	public void setQuantumTime(Integer quantumTime) {
		this.quantumTime = quantumTime;
	}

	public String getQuantumUnit() {
		return quantumUnit;
	}

	public void setQuantumUnit(String quantumUnit) {
		this.quantumUnit = quantumUnit;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Long getStartTimeStamp() {
		return startTimeStamp;
	}

	public void setStartTimeStamp(Long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}

	public Long getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(Long endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public Integer getBoothId() {
		return boothId;
	}

	public void setBoothId(Integer boothId) {
		this.boothId = boothId;
	}

	@Override
	public String toString() {
		return "RequestParamOfBoothDetails [deviceMac=" + deviceMac + ", quantumTime=" + quantumTime + ", quantumUnit="
				+ quantumUnit + ", startTime=" + startTime + ", endTime=" + endTime + ", startTimeStamp="
				+ startTimeStamp + ", endTimeStamp=" + endTimeStamp + ", unitId=" + unitId + ", boothId=" + boothId
				+ ", tableName=" + this.getTableName() + ", partitionName=" + this.getPartitionName() + "]";
	}
}
