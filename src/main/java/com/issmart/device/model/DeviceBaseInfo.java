package com.issmart.device.model;

import java.util.Date;

/**
 * 场地活跃指数实体
 * 
 * @author CuiZhiXiang
 *
 */
public class DeviceBaseInfo {
	
	private Integer id;
	
	/*
	 * eventId
	 */
	private Integer eventId;
	
	/*
	 * unitId
	 */
	private String unitId;
	
	/*
	 * rssi
	 */
	private Integer rssi;
	
	/*
	 * 展台类别
	 */
	private Integer boothType;
	
	/*
	 * 场地名称
	 */
	private String deviceName;
	
	/*
	 * 展台Id
	 */
	private Integer boothId;
	
	/*
	 * deviceBooth
	 */
	private String deviceBooth;
	
	/*
	 * rentState
	 */
	private boolean rentState;
	
	/*
	 * deviceStatus
	 */
	private String deviceStatus;
	
	/*
	 * deviceAddress
	 */
	private String deviceAddress;
	
	/*
	 * boothName
	 */
	private String boothName;
	
	/*
	 * deviceMac
	 */
	private String deviceMac;
	
	/*
	 * 展台类别
	 */
	private Integer deviceX;
	
	/*
	 * 展台类别
	 */
	private Integer deviceY;
	
	/*
	 * 展台类别
	 */
	private Integer deviceZ;
	
	/*
	 * 展台类别
	 */
	private Date time;
	
	/*
	 * 累计停留时长/min
	 */
	private int duration;
	
	/*
	 * 累计访问人数
	 */
	private int bemberTotal;
	
	/*
	 * 访问人次
	 */
	private int realMemberTotal;
	
	/*
	 * 人均停留时长/min
	 */
	private int durationAvg;
	
	/*
	 * 累计有效互动数
	 */
	private int msgSendTotal;
	
	/*
	 * deviceMmac
	 */
	private String deviceMmac;
	
	/*
	 * beaconMac
	 */
	private String beaconMac;
	
	/*
	 * calculateTimeStamp
	 */
	private long calculateTimeStamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getRssi() {
		return rssi;
	}

	public void setRssi(Integer rssi) {
		this.rssi = rssi;
	}

	public Integer getBoothType() {
		return boothType;
	}

	public void setBoothType(Integer boothType) {
		this.boothType = boothType;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getBoothId() {
		return boothId;
	}

	public void setBoothId(Integer boothId) {
		this.boothId = boothId;
	}

	public String getDeviceBooth() {
		return deviceBooth;
	}

	public void setDeviceBooth(String deviceBooth) {
		this.deviceBooth = deviceBooth;
	}

	public boolean isRentState() {
		return rentState;
	}

	public void setRentState(boolean rentState) {
		this.rentState = rentState;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public String getBoothName() {
		return boothName;
	}

	public void setBoothName(String boothName) {
		this.boothName = boothName;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public Integer getDeviceX() {
		return deviceX;
	}

	public void setDeviceX(Integer deviceX) {
		this.deviceX = deviceX;
	}

	public Integer getDeviceY() {
		return deviceY;
	}

	public void setDeviceY(Integer deviceY) {
		this.deviceY = deviceY;
	}

	public Integer getDeviceZ() {
		return deviceZ;
	}

	public void setDeviceZ(Integer deviceZ) {
		this.deviceZ = deviceZ;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getBemberTotal() {
		return bemberTotal;
	}

	public void setBemberTotal(int bemberTotal) {
		this.bemberTotal = bemberTotal;
	}

	public int getRealMemberTotal() {
		return realMemberTotal;
	}

	public void setRealMemberTotal(int realMemberTotal) {
		this.realMemberTotal = realMemberTotal;
	}

	public int getDurationAvg() {
		return durationAvg;
	}

	public void setDurationAvg(int durationAvg) {
		this.durationAvg = durationAvg;
	}

	public int getMsgSendTotal() {
		return msgSendTotal;
	}

	public void setMsgSendTotal(int msgSendTotal) {
		this.msgSendTotal = msgSendTotal;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public int getSortParam() {
		int sortParam = this.bemberTotal * 100 + this.realMemberTotal * 50 + this.msgSendTotal * 50 +
				this.duration + this.durationAvg;
		return sortParam;
	}

	public String getDeviceMmac() {
		return deviceMmac;
	}

	public void setDeviceMmac(String deviceMmac) {
		this.deviceMmac = deviceMmac;
	}

	public String getBeaconMac() {
		return beaconMac;
	}

	public void setBeaconMac(String beaconMac) {
		this.beaconMac = beaconMac;
	}

	public long getCalculateTimeStamp() {
		return calculateTimeStamp;
	}

	public void setCalculateTimeStamp(long calculateTimeStamp) {
		this.calculateTimeStamp = calculateTimeStamp;
	}

	@Override
	public String toString() {
		return "DeviceBaseInfo [id=" + id + ", eventId=" + eventId + ", unitId=" + unitId + ", rssi=" + rssi
				+ ", boothType=" + boothType + ", deviceName=" + deviceName + ", boothId=" + boothId + ", deviceBooth="
				+ deviceBooth + ", rentState=" + rentState + ", deviceStatus=" + deviceStatus + ", deviceAddress="
				+ deviceAddress + ", boothName=" + boothName + ", deviceMac=" + deviceMac + ", deviceX=" + deviceX
				+ ", deviceY=" + deviceY + ", deviceZ=" + deviceZ + ", time=" + time + ", duration=" + duration
				+ ", bemberTotal=" + bemberTotal + ", realMemberTotal=" + realMemberTotal + ", durationAvg="
				+ durationAvg + ", msgSendTotal=" + msgSendTotal + "]";
	}
	
}
