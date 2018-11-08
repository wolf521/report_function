package com.issmart.device.model;

/**
 * 会议模板实体
 * 
 * @author Administrator
 *
 */
public class EventTemplate {
	
	/**
	 * 会议开始时间戳
	 */
	private Long startTimeStamp;;
	
	/**
	 * 会议截止时间戳
	 */
	private Long endTimeStamp;
	
	/**
	 * eventId
	 */
	private String eventId;
	
	/**
	 * unitId
	 */
	private String unitId;
	
	/**
	 * 会议状态
	 */
	private String status;
	
	public EventTemplate(Long startTimeStamp, Long endTimeStamp, String eventId, String unitId) {
		super();
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
		this.eventId = eventId;
		this.unitId = unitId;
	}

	public EventTemplate() {
		super();
	}

	@Override
	public String toString() {
		return "EventTemplate [startTimeStamp=" + startTimeStamp + ", endTimeStamp=" + endTimeStamp + ", eventId="
				+ eventId + ", unitId=" + unitId + ", status=" + status + "]";
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
