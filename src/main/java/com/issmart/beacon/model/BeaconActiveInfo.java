package com.issmart.beacon.model;

/**
 * 用户活跃指数实体
 * 
 * @author cuizhixiang
 *
 */
public class BeaconActiveInfo {
	
	/**
	 * contactId
	 */
	private Integer contactId;
	
	/**
	 * memberId
	 */
	private Integer memberId;
	
	/**
	 * 姓名
	 */
	private String memberName;
	
	/**
	 * 手机
	 */
	private String memberPhone;
	
	/**
	 * 邮箱
	 */
	private String memberEmail;
	
	/**
	 * beaconMac
	 */
	private String beaconMac;
	
	/**
	 * deviceMac
	 */
	private String deviceMac;
	
	/**
	 * calculateTimeStamp
	 */
	private long calculateTimeStamp;
	
	/**
	 * 公司
	 */
	private String companyName;
	
	/**
	 * 职位
	 */
	private String postionCode;
	
	/**
	 * 行业
	 */
	private String industry;
	
	/**
	 * 区域
	 */
	private String region;
	
	/**
	 * 累计展台访问数
	 */
	private Integer boothTotal;
	
	/**
	 * 重复展台访问数
	 */
	private Integer boothRepeatTotal;
	
	/**
	 * 展台有效互动
	 */
	private Integer interactTotal;
	
	/**
	 * 累计停留时间/min
	 */
	private Integer durationTimeTotal;
	
	/**
	 * 分配单元
	 */
	private Integer unitId;
	
	/**
	 * 评分
	 */
	private Integer score;

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getBeaconMac() {
		return beaconMac;
	}

	public void setBeaconMac(String beaconMac) {
		this.beaconMac = beaconMac;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPostionCode() {
		return postionCode;
	}

	public void setPostionCode(String postionCode) {
		this.postionCode = postionCode;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getBoothTotal() {
		return boothTotal;
	}

	public void setBoothTotal(Integer boothTotal) {
		this.boothTotal = boothTotal;
	}

	public Integer getBoothRepeatTotal() {
		return boothRepeatTotal;
	}

	public void setBoothRepeatTotal(Integer boothRepeatTotal) {
		this.boothRepeatTotal = boothRepeatTotal;
	}

	public Integer getInteractTotal() {
		return interactTotal;
	}

	public void setInteractTotal(Integer interactTotal) {
		this.interactTotal = interactTotal;
	}

	public Integer getDurationTimeTotal() {
		return durationTimeTotal;
	}

	public void setDurationTimeTotal(Integer durationTimeTotal) {
		this.durationTimeTotal = durationTimeTotal;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public long getCalculateTimeStamp() {
		return calculateTimeStamp;
	}

	public void setCalculateTimeStamp(long calculateTimeStamp) {
		this.calculateTimeStamp = calculateTimeStamp;
	}

	@Override
	public String toString() {
		return "BeaconActiveInfo [contactId=" + contactId + ", memberId=" + memberId + ", memberName=" + memberName
				+ ", memberPhone=" + memberPhone + ", memberEmail=" + memberEmail + ", beaconMac=" + beaconMac
				+ ", deviceMac=" + deviceMac + ", calculateTimeStamp=" + calculateTimeStamp + ", companyName="
				+ companyName + ", postionCode=" + postionCode + ", industry=" + industry + ", region=" + region
				+ ", boothTotal=" + boothTotal + ", boothRepeatTotal=" + boothRepeatTotal + ", interactTotal="
				+ interactTotal + ", durationTimeTotal=" + durationTimeTotal + ", unitId=" + unitId + ", score=" + score
				+ "]";
	}
}
