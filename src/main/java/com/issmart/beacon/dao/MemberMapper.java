package com.issmart.beacon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.issmart.beacon.model.Member;

public interface MemberMapper {
	/**
	 * 获取参会者beaconmac数组字符串
	 * 
	 * @param unitId
	 * @return
	 */
	public String getBeaconMacArrayToString(@Param("unitId") String unitId);

	public Member findById(@Param("id") Integer id);

	/**
	 * 全量更新member信息
	 * 
	 * @param unitId
	 * @return
	 */
	public Integer deleteByUnitId(@Param("unitId") String unitId);

	/**
	 * 批量添加用户信息
	 * 
	 * @param unitId
	 * @param members
	 * @return
	 */
	public Integer inserts(@Param("unitId") String unitId, @Param("list") List<Map<String, Object>> members);

	/**
	 * map更新
	 * 
	 * @param param
	 * @return
	 */
	public Integer updateMap(Map<String, Object> param);

	/**
	 * map新增
	 * 
	 * @param param
	 * @return
	 */
	public Integer insertMap(Map<String, Object> param);

	/**
	 * 获取指定时间内附近的人
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @param realMinute
	 * @param radarRssi
	 * @return
	 */
	public List<Map<String, Object>> getBoothPeopleNearby(@Param("unitId") String unitId,
			@Param("deviceMac") String deviceMac, @Param("radarTime") Integer radarTime,
			@Param("radarRssi") Integer radarRssi);
	
	/**
	 * 根据unitId获取用户beaconMac集合
	 * @param unitId
	 * @return
	 */
	public List<String> getMemberBeaconMacs(@Param("unitId") String unitId);

}
