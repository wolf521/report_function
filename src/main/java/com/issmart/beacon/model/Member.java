package com.issmart.beacon.model;

public class Member {

	/**
	 * id
	 */
	private Integer id;
	/**
	 * unitId
	 */
	private String unitId;
	/**
	 * memberName
	 */
	private String memberName;
	/**
	 * beaconId
	 */
	private String beaconId;
	/**
	 * beaconMac
	 */
	private String beaconMac;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}

	public String getBeaconMac() {
		return beaconMac;
	}

	public void setBeaconMac(String beaconMac) {
		this.beaconMac = beaconMac;
	}
}
