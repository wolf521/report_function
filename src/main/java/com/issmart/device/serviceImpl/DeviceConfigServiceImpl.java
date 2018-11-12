package com.issmart.device.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.aliyun.fc.runtime.Context;
import com.issmart.common.util.MyBatisUtil;
import com.issmart.device.dao.DeviceConfigMapper;
import com.issmart.device.model.DeviceConfigInfo;
import com.issmart.device.model.EventTemplate;
import com.issmart.device.service.DeviceConfigService;

public class DeviceConfigServiceImpl implements DeviceConfigService {

	/**
	 * 推送会议模板
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	@Override
	public Integer pushEventTemplate(Context context, EventTemplate eventTemplate) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(false);
		DeviceConfigMapper deviceConfigMapper = session.getMapper(DeviceConfigMapper.class);
		int i = deviceConfigMapper.deleteEventTemplate(eventTemplate.getUnitId());
		int count = deviceConfigMapper.insertEventTemplate(eventTemplate);
		if (i == 0) {
			//createTable(context, deviceConfigMapper, eventTemplate);
		}
		if (i != 0 && count == 0) {
			session.rollback();
		} else {
			session.commit();
		}
		return count;
	}

	/**
	 * 创建会议数据表
	 * 
	 * @param context
	 * @param deviceConfigDao
	 * @param eventTemplate
	 */
	@Override
	public void createTable(Context context, EventTemplate eventTemplate) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(false);
		DeviceConfigMapper deviceConfigMapper = session.getMapper(DeviceConfigMapper.class);
		String tableName = "tb_report_visit_log_" + eventTemplate.getUnitId();
		int days = (int) ((eventTemplate.getEndTimeStamp() - eventTemplate.getStartTimeStamp()) / (3600 * 24));
		if (days <= 10) {
			Date date = new Date();
			date.setTime(eventTemplate.getStartTimeStamp() * 1000);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			List<Map<String, Object>> partitionInfoList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < days; i++) {
				cal.add(Calendar.DATE, 1);
				Map<String, Object> map = new HashMap<>();
				if((cal.get(Calendar.MONTH)+1) < 10) {
					map.put("name", "p0" + (cal.get(Calendar.MONTH)+1) + cal.get(Calendar.DATE));
					map.put("time", cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE));
				} else {
					map.put("name", "p" + (cal.get(Calendar.MONTH)+1) + cal.get(Calendar.DATE));
					map.put("time", cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE));
				}
				partitionInfoList.add(map);
			}
			try {
				int i = deviceConfigMapper.insertPermissionLevel(eventTemplate.getUnitId(), "S");
				deviceConfigMapper.createTable(tableName, partitionInfoList);
				System.out.println(i);
				//context.getLogger().info(eventTemplate.getUnitId() + "新建数据表成功");
				if (i != 0) {
					//context.getLogger().info(eventTemplate.getUnitId() + "新建数据报表权限信息成功");
				} else {
					//context.getLogger().info(eventTemplate.getUnitId() + "新建数据报表权限信息失败");
				}
			} catch (Exception e) {
				//context.getLogger().info(eventTemplate.getUnitId() + "新建数据表失败");
				e.printStackTrace();
			}
		} else {
			//context.getLogger().info(eventTemplate.getUnitId() + "新建数据表失败,分区数大于10");
		}
	}

	@Override
	public int saveDeviceBoothRelation(DeviceConfigInfo deviceConfig) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(false);
		DeviceConfigMapper deviceConfigMapper = session.getMapper(DeviceConfigMapper.class);
		int count = save(deviceConfigMapper, deviceConfig);
		if (count == 0) {
			session.rollback();
			for (int i = 0; i < deviceConfig.getRetryCount(); i++) {
				count = save(deviceConfigMapper, deviceConfig);
				if (count != 0) {
					session.commit();
					break;
				} else {
					session.rollback();
				}
			}
		} else {
			session.commit();
		}
		return count;
	}

	/**
	 * 操作
	 * 
	 * @param deviceConfigDao
	 * @param deviceConfig
	 * @return
	 */
	private int save(DeviceConfigMapper deviceConfigMapper, DeviceConfigInfo deviceConfig) {
		Integer boorhId = deviceConfigMapper.selectDeviceBoothRelation(deviceConfig.getUnitId(),
				deviceConfig.getDeviceMac());
		int count = 0;
		if (boorhId == null) {
			count = deviceConfigMapper.insertDeviceBoothRelation(deviceConfig);
		} else {
			count = deviceConfigMapper.updateDeviceBoothRelation(deviceConfig);
		}
		return count;
	}

	@Override
	public int deleteDeviceBoothRelation(DeviceConfigInfo deviceConfig) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(false);
		DeviceConfigMapper deviceConfigMapper = session.getMapper(DeviceConfigMapper.class);
		int count = delete(deviceConfigMapper, deviceConfig);
		if (count == 0) {
			session.rollback();
			for (int i = 0; i < deviceConfig.getRetryCount(); i++) {
				count = delete(deviceConfigMapper, deviceConfig);
				if (count != 0) {
					session.commit();
					break;
				}
			}
		} else {
			session.commit();
		}
		return count;
	}

	/**
	 * 操作
	 * 
	 * @param deviceConfigDao
	 * @param deviceConfig
	 * @return
	 */
	private int delete(DeviceConfigMapper deviceConfigMapper, DeviceConfigInfo deviceConfig) {
		return deviceConfigMapper.deleteDeviceBoothRelation(deviceConfig);
	}

//	public static void main(String[] args) {
//		int days = (int) ((1541210330l - 1540519130l) / (3600 * 24));
//		System.out.println(days);
//		if (days <= 10) {
//			Date date = new Date();
//			date.setTime(1540519130l * 1000);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			List<Map<String, Object>> partitionInfoList = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < days; i++) {
//				cal.add(Calendar.DATE, 1);
//				Map<String, Object> map = new HashMap<>();
//				if((cal.get(Calendar.MONTH)+1) < 10) {
//					map.put("name", "p0" + (cal.get(Calendar.MONTH)+1) + cal.get(Calendar.DATE));
//					map.put("time", cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE));
//				} else {
//					map.put("name", "p" + (cal.get(Calendar.MONTH)+1) + cal.get(Calendar.DATE));
//					map.put("time", cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE));
//				}
//				
//				partitionInfoList.add(map);
//			}
//			System.out.println(partitionInfoList);
//		}
//	}
}
