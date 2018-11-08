package com.issmart.device.model;

/**
 * 采集与展台关联关系实体
 * 
 * @author Administrator
 *
 */
public class DeviceConfigInfo {
	/**
	 * unitId
	 */
	private String unitId;
	/**
	 * boothId
	 */
	private Integer boothId;
	/**
	 * deviceMac
	 */
	private String deviceMac;
	/**
	 * rssi
	 */
	private int rssi;
	/**
	 * deviceName
	 */
	private String deviceName;
	/**
	 * boothName
	 */
	private String boothName;
	/**
	 * boothExhibitorName
	 */
	private String boothExhibitorName;
	/**
	 * boothExhibitorLogo
	 */
	private String boothExhibitorLogo;
	/**
	 * boothLocation
	 */
	private String boothLocation;
	/**
	 * coordsX
	 */
	private double coordsX;
	/**
	 * coordsY
	 */
	private double coordsY;
	/**
	 * coordsZ
	 */
	private double coordsZ;
	/**
	 * boothType
	 */
	private int boothType;
	/**
	 * boothTypeName
	 */
	private String boothTypeName;
	/**
	 * label
	 */
	private String label;
	/**
	 * 操作指标（save,delete）
	 */
	private String opeFlag;
	/**
	 * 重试次数
	 */
	private int retryCount;
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public Integer getBoothId() {
		return boothId;
	}
	public void setBoothId(Integer boothId) {
		this.boothId = boothId;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getBoothName() {
		return boothName;
	}
	public void setBoothName(String boothName) {
		this.boothName = boothName;
	}
	public String getBoothExhibitorName() {
		return boothExhibitorName;
	}
	public void setBoothExhibitorName(String boothExhibitorName) {
		this.boothExhibitorName = boothExhibitorName;
	}
	public String getBoothExhibitorLogo() {
		return boothExhibitorLogo;
	}
	public void setBoothExhibitorLogo(String boothExhibitorLogo) {
		this.boothExhibitorLogo = boothExhibitorLogo;
	}
	public String getBoothLocation() {
		return boothLocation;
	}
	public void setBoothLocation(String boothLocation) {
		this.boothLocation = boothLocation;
	}
	public double getCoordsX() {
		return coordsX;
	}
	public void setCoordsX(double coordsX) {
		this.coordsX = coordsX;
	}
	public double getCoordsY() {
		return coordsY;
	}
	public void setCoordsY(double coordsY) {
		this.coordsY = coordsY;
	}
	public double getCoordsZ() {
		return coordsZ;
	}
	public void setCoordsZ(double coordsZ) {
		this.coordsZ = coordsZ;
	}
	public int getBoothType() {
		return boothType;
	}
	public void setBoothType(int boothType) {
		this.boothType = boothType;
	}
	public String getBoothTypeName() {
		return boothTypeName;
	}
	public void setBoothTypeName(String boothTypeName) {
		this.boothTypeName = boothTypeName;
	}
	public String getOpeFlag() {
		return opeFlag;
	}
	public void setOpeFlag(String opeFlag) {
		this.opeFlag = opeFlag;
	}
	public int getRetryCount() {
		if(this.retryCount == 0) {
			this.retryCount = 1;
		}
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "DeviceConfigInfo [unitId=" + unitId + ", boothId=" + boothId + ", deviceMac=" + deviceMac + ", rssi="
				+ rssi + ", deviceName=" + deviceName + ", boothName=" + boothName + ", boothExhibitorName="
				+ boothExhibitorName + ", boothExhibitorLogo=" + boothExhibitorLogo + ", boothLocation=" + boothLocation
				+ ", coordsX=" + coordsX + ", coordsY=" + coordsY + ", coordsZ=" + coordsZ + ", boothType=" + boothType
				+ ", boothTypeName=" + boothTypeName + ", label=" + label + ", opeFlag=" + opeFlag + ", retryCount="
				+ retryCount + "]";
	}
}
