package com.issmart.device.service;

import com.aliyun.fc.runtime.Context;
import com.issmart.device.model.DeviceConfigInfo;
import com.issmart.device.model.EventTemplate;


public interface DeviceConfigService {
	
	/**
	 * 推送会议模板
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Integer pushEventTemplate(Context context,EventTemplate eventTemplate);
	
	/**
	 * 创建数据报表
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	void createTable(Context context,EventTemplate eventTemplate);
	
	/**
	 * 保存采集器与展台关联关系
	 * 
	 * @param unitId
	 * @return
	 */
	int saveDeviceBoothRelation(DeviceConfigInfo deviceConfig);
	
	/**
	 * 删除采集器与展台关联关系
	 * 
	 * @param unitId
	 * @return
	 */
	int deleteDeviceBoothRelation(DeviceConfigInfo deviceConfig);
}
