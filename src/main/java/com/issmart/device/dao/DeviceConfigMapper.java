package com.issmart.device.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.issmart.device.model.DeviceConfigInfo;
import com.issmart.device.model.EventTemplate;

public interface DeviceConfigMapper {
	
	/**
	 * 创建分区表
	 * 
	 * @param tableName
	 * @param partitionList
	 * @return
	 */
	int createTable(@Param("tableName") String tableName,@Param("partitionList") List<Map<String,Object>> partitionList);
	
	/**
	 * 查询分区名称
	 */
	List<String> queryPartitionName(@Param("tableName") String tableName);
	
	/**
	 * 新建某会议权限等级
	 */
	int insertPermissionLevel(@Param("unitId") String unitId,@Param("permissionLevel") String permissionLevel);
	
	/**
	 * 查询某会议权限等级
	 */
	List<String> queryPermissionLevel(@Param("unitId") String unitId);

	/**
	 * 新增会议模板
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Integer insertEventTemplate(EventTemplate eventTemplate);
	
	/**
	 * 删除会议模板
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Integer deleteEventTemplate(@Param("unitId") String unitId);
	
	/**
	 * 查询boothId
	 * 
	 * @param unitId
	 * @return
	 */
	Integer selectDeviceBoothRelation(@Param("unitId")String unitId,@Param("deviceMac")String deviceMac);
	
	/**
	 * 新增采集器与展台关联关系
	 * 
	 * @param unitId
	 * @return
	 */
	int insertDeviceBoothRelation(DeviceConfigInfo deviceConfig);
	
	/**
	 * 修改采集器与展台关联关系
	 * 
	 * @param unitId
	 * @return
	 */
	int updateDeviceBoothRelation(DeviceConfigInfo deviceConfig);
	
	/**
	 * 删除采集器与展台关联关系
	 * 
	 * @param unitId
	 * @return
	 */
	int deleteDeviceBoothRelation(DeviceConfigInfo deviceConfig);
}
