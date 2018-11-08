package com.issmart.beacon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.issmart.beacon.model.BeaconActive;
import com.issmart.beacon.model.BeaconActiveInfo;

public interface BeaconMapper {
	
	/**
	 * 查询此展会报表功能等级
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	int queryActiveScore(String unitId);
	
	/**
	 * 查询出用于处理展台重复访问数的数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActiveInfo> queryMemberActivesResult(@Param("unitId") String unitId,@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);
	
	
	/*******************************************************/
	
	/**
	 * 查询此展会报表功能等级
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	String queryPermissionLevel(String unitId);
	
	/**
	 * 查询用户活跃指数
	 * 
	 * @param params
	 * @return
	 */
	List<BeaconActiveInfo> queryMemberActives(@Param("tableName") String tableName,@Param("unitId") String unitId,@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);
	
	/**
	 * 查询出某用户轨迹数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActive> queryActive(@Param("unitId") String unitId,@Param("beaconMac") String beaconMac);
	
	/**
	 * 查询出用于处理展台重复访问数的数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActiveInfo> queryActiveBoothRepeatTotal(@Param("tableName") String tableName,@Param("unitId") String unitId);
	
	/**
	 * 查询最感兴趣的展台
	 * 
	 * @param unitId
	 * @param beaconMac
	 * @return
	 */
	Map<String,String> queryInterestedBooth(@Param("unitId") String unitId,@Param("beaconMac") String beaconMac);
	
	/**
	 * 查询出所有人员
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActive> queryMemberNameByBeaconMac(@Param("unitId") String unitId);
	
	
	/**
	 * 查询数据条数
	 * 
	 * @param unitId
	 * @return
	 */
	Integer getTotal(@Param("unitId") String unitId);
	
	/**
	 * 查询当前beacon所处采集器Mac地址
	 * 
	 * @param unitId
	 * @return
	 */
	String getDeviceMac(@Param("unitId") String unitId,@Param("beaconId") String beaconId,@Param("minutes") Integer minutes);
	/**
	 * 查询当前采集器周围人数
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryMemberByNearBy(@Param("unitId") String unitId,@Param("beaconId") String beaconId,@Param("deviceMac") String deviceMac,@Param("minutes") Integer minutes,@Param("rssi") Integer rssi);

	/**
	 * 查询用户总体指标数据
	 * 
	 * @param unitId
	 * @return
	 */
	Map<String,Object> queryOverallIndicators(String unitId);
	
	/**
	 * 查询用户总数
	 * 
	 * @param unitId
	 * @return
	 */
	int queryMemberNumber(String unitId);
	
	/**
	 * 查询签到用户
	 * 
	 * @param unitId
	 * @return
	 */
	Integer getBeaconTotal(String unitId);
	
	/**
	 * 查询智慧互动分析
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryInteractionAnalysis(String unitId);
}
