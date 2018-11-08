package com.issmart.device.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.issmart.common.dao.GetPressNumberMapper;
import com.issmart.common.model.ReportPermission;
import com.issmart.common.util.BusinessUtil;
import com.issmart.common.util.MyBatisUtil;
import com.issmart.common.util.TimeUtil;
import com.issmart.device.dao.DeviceMapper;
import com.issmart.device.model.DeviceBaseInfo;
import com.issmart.device.model.RequestParamOfBoothDetails;
import com.issmart.device.service.DeviceService;

public class DeviceServiceImpl implements DeviceService {

	@Override
	public Map<String, Integer> queryBoothIndex(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		return deviceDao.queryBoothIndex(unitId);
	}

	@Override
	public Integer queryRealMemberTotal(String unitId, Integer boothId, Integer minutes) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		String permissionLevel = deviceDao.queryPermissionLevel(unitId);
		if (permissionLevel != null && permissionLevel.equals(ReportPermission.getS())) {
			Long endTimeStamp = new Date().getTime() / 1000;
			Long startTimeStamp = endTimeStamp - (minutes * 60);
			return deviceDao.getRealMemberNumber(unitId, boothId, startTimeStamp, endTimeStamp);
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> queryBestDrainage(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		return deviceDao.queryBestDrainage(unitId);
	}

	@Override
	public List<Map<String, Object>> queryBestAttract(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		return deviceDao.queryBestAttract(unitId);
	}

	@Override
	public List<Map<String, Object>> queryBestActive(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		String permissionLevel = deviceDao.queryPermissionLevel(unitId);
		if (permissionLevel != null && permissionLevel.equals(ReportPermission.getS())) {
			return deviceDao.queryBestActive(unitId);
		}
		return new ArrayList<>();
	}

	@Override
	public List<DeviceBaseInfo> queryDeviceHot(String unitId, int pageNo, int pageSize) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		GetPressNumberMapper getPressNumberMapper = session.getMapper(GetPressNumberMapper.class);
		List<DeviceBaseInfo> list = deviceDao.queryDeviceHot(unitId, BusinessUtil.processPageNo(pageNo, pageSize),
				pageSize);
		if (list.size() == 0) {
			return new ArrayList<>();
		}
		List<Map<String, Object>> mapList = getPressNumberMapper.getActiveNumberMapperByDeviceMac(unitId, 1);
		for (DeviceBaseInfo deviceBaseInfo : list) {
			for (Map<String, Object> m1 : mapList) {
				if (deviceBaseInfo.getDeviceMac().equals(m1.get("deviceMac"))) {
					deviceBaseInfo.setMsgSendTotal(Integer.parseInt(m1.get("activeNumber").toString()));
					break;
				}
			}
		}
		return list;
	}

	/**
	 * 处理访问次数
	 * 
	 * @param deviceDao
	 * @param unitId
	 * @param boothId
	 * @return
	 */
	public int opeRealMemberTotal(List<DeviceBaseInfo> logListParam, String unitId, Integer boothId) {
		List<DeviceBaseInfo> logList = getlogList(logListParam, boothId);
		if (logList.size() == 0)
			return 0;
		long lastTimeStamp = logList.get(0).getCalculateTimeStamp();
		String deviceMac = logList.get(0).getDeviceMac();
		String beaconMac = logList.get(0).getBeaconMac();
		int sum = 0;
		for (DeviceBaseInfo deviceBaseInfo : logList) {
			if (deviceMac != null && deviceMac.equals(deviceBaseInfo.getDeviceMac())) {
				if (beaconMac != null && beaconMac.equals(deviceBaseInfo.getBeaconMac())) {
					if ((deviceBaseInfo.getCalculateTimeStamp() - lastTimeStamp) >= 300) {
						sum += 1;
					}
				} else {
					sum += 1;
				}
			} else {
				sum += 1;
			}
			deviceMac = deviceBaseInfo.getDeviceMac();
			beaconMac = deviceBaseInfo.getBeaconMac();
			lastTimeStamp = deviceBaseInfo.getCalculateTimeStamp();
		}
		return sum;
	}

	public List<DeviceBaseInfo> getlogList(List<DeviceBaseInfo> logListParam, Integer boothId) {
		List<DeviceBaseInfo> list = new ArrayList<>();
		for (DeviceBaseInfo deviceBaseInfo : logListParam) {
			if (boothId.equals(deviceBaseInfo.getBoothId())) {
				list.add(deviceBaseInfo);
			}
		}
		return list;
	}

	@Override
	public Integer getTotal(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		return deviceDao.getTotal(unitId);
	}

	@Override
	public Map<String, Object> querySingleBoothInfo(String unitId, Integer boothId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		Map<String, Object> map = deviceDao.querySingleBoothInfo(unitId, boothId);
		return map == null ? new HashMap<>() : map;
	}

	/**
	 * 查询展台折线图
	 */
	@Override
	public Map<String, List<Object>> queryTrendChart(RequestParamOfBoothDetails param) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		TimeUtil.handleTime(param);
		List<Map<String, Object>> paramList = TimeUtil.getDivisionTime(param);
		List<Map<String, Object>> list = queryTrendChartByBoothId(deviceDao, param);
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		if (list != null) {
			for (Map<String, Object> mapTime : paramList) {
				boolean flag = true;
				for (Map<String, Object> mapValue : list) {
					if (mapTime.get("time").equals(mapValue.get("time"))) {
						mapTime.put("count", mapValue.get("count"));
						flag = false;
						break;
					}
				}
				if (flag) {
					mapTime.put("count", 0);
				}
			}
		} else {
			for (Map<String, Object> mapTime : paramList) {
				mapTime.put("count", 0);
			}
		}
		List<Object> quantumTimes = new ArrayList<Object>();
		List<Object> memberNums = new ArrayList<Object>();
		for (Map<String, Object> paramMap : paramList) {
			quantumTimes.add(paramMap.get("time"));
			memberNums.add(paramMap.get("count"));
		}
		map.put("quantumTimes", quantumTimes);
		map.put("memberNums", memberNums);
		return map;
	}

	/**
	 * 根据场地关联采集器数量多少整合折线图数据
	 * 
	 * @param deviceDao
	 * @param param
	 * @return
	 */
	private List<Map<String, Object>> queryTrendChartByBoothId(DeviceMapper deviceDao,
			RequestParamOfBoothDetails param) {
		List<String> deviceMacList = deviceDao.queryDeviceMacs(param.getBoothId());
		if (getPartitionInfo(deviceDao, param)) {
			if (deviceMacList.size() != 0) {
				// 场地只绑定一个采集器
				if (deviceMacList.size() == 1) {
					param.setDeviceMac(deviceMacList.get(0));
					return deviceDao.queryTrendChart(param);
				} else {
					// 场地绑定多个采集器
					List<Map<String, Object>> listResult = null;
					for (String deviceMac : deviceMacList) {
						param.setDeviceMac(deviceMac);
						List<Map<String, Object>> dataList = deviceDao.queryTrendChart(param);
						if (listResult == null) {
							listResult = dataList;
						} else if (dataList != null) {
							for (Map<String, Object> map : dataList) {
								boolean flag = true;
								for (Map<String, Object> mapResult : listResult) {
									if (mapResult.get("time").equals(map.get("time"))) {
										mapResult.put("count", (Integer.parseInt(mapResult.get("count").toString())
												+ Integer.parseInt(map.get("count").toString())));
										flag = false;
									}
								}
								if (flag) {
									listResult.add(map);
								}
							}
						}
					}
					return listResult;
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Map<String, List<Object>>> queryDistributeChart(RequestParamOfBoothDetails param) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		TimeUtil.handleTime(param);
		Map<String, Map<String, List<Object>>> map = new HashMap<String, Map<String, List<Object>>>();
		if (!getPartitionInfo(deviceDao, param))
			return map;
		List<String> deviceMacList = deviceDao.queryDeviceMacs(param.getBoothId());
		map.put("regionDistribute", queryDistributeChartByCity(param, deviceDao, deviceMacList));
		map.put("positionDistribute", queryDistributeChartByPosition(param, deviceDao, deviceMacList));
		map.put("industryDistribute", queryDistributeChartByIndustry(param, deviceDao, deviceMacList));
		return map;
	}

	/**
	 * 查询职位分布
	 * 
	 * @param param
	 * @param deviceDao
	 * @return
	 */
	public Map<String, List<Object>> queryDistributeChartByPosition(RequestParamOfBoothDetails param,
			DeviceMapper deviceDao, List<String> deviceMacList) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Map<String, Object>> list = queryDistributeChartByPositionByBoothId(deviceDao, param, deviceMacList);
		List<Object> data = new ArrayList<Object>();
		List<Object> name = new ArrayList<Object>();
		for (Map<String, Object> paramMap : list) {
			Map<String, Integer> singleMap = new HashMap<String, Integer>();
			singleMap.put(paramMap.get("postion_code").toString(), Integer.parseInt(paramMap.get("count").toString()));
			data.add(singleMap);
			name.add(paramMap.get("postion_code"));
		}
		map.put("data", data);
		map.put("name", name);
		return map;
	}

	/**
	 * 根据场地关联采集器数量多少整合职位分布数据
	 * 
	 * @param deviceDao
	 * @param param
	 * @return
	 */
	private List<Map<String, Object>> queryDistributeChartByPositionByBoothId(DeviceMapper deviceDao,
			RequestParamOfBoothDetails param, List<String> deviceMacList) {
		if (deviceMacList.size() != 0) {
			// 场地只绑定一个采集器
			if (deviceMacList.size() == 1) {
				param.setDeviceMac(deviceMacList.get(0));
				return deviceDao.queryDistributeChartByPosition(param);
			} else {
				// 场地绑定多个采集器
				List<Map<String, Object>> listResult = null;
				for (String deviceMac : deviceMacList) {
					param.setDeviceMac(deviceMac);
					List<Map<String, Object>> dataList = deviceDao.queryDistributeChartByPosition(param);
					if (dataList != null) {
						if (listResult == null) {
							listResult = dataList;
						} else {
							for (Map<String, Object> map : dataList) {
								boolean flag = true;
								for (Map<String, Object> mapResult : listResult) {
									if (mapResult.get("postion_code").equals(map.get("postion_code"))) {
										mapResult.put("count", (Integer.parseInt(mapResult.get("count").toString())
												+ Integer.parseInt(map.get("count").toString())));
										flag = false;
										break;
									}
								}
								if (flag) {
									listResult.add(map);
								}
							}
						}
					}
				}
				return listResult;
			}
		}
		return null;
	}

	/**
	 * 查询地域分布
	 * 
	 * @param param
	 * @param deviceDao
	 * @return
	 */
	public Map<String, List<Object>> queryDistributeChartByCity(RequestParamOfBoothDetails param,
			DeviceMapper deviceDao, List<String> deviceMacList) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Map<String, Object>> list = queryDistributeChartByCityByBoothId(deviceDao, param, deviceMacList);
		List<Object> data = new ArrayList<Object>();
		List<Object> name = new ArrayList<Object>();
		for (Map<String, Object> paramMap : list) {
			Map<String, Integer> singleMap = new HashMap<String, Integer>();
			singleMap.put(paramMap.get("city").toString(), Integer.parseInt(paramMap.get("count").toString()));
			data.add(singleMap);
			name.add(paramMap.get("city"));
		}
		map.put("data", data);
		map.put("name", name);
		return map;
	}

	/**
	 * 根据场地关联采集器数量多少整合地域分布数据
	 * 
	 * @param deviceDao
	 * @param param
	 * @return
	 */
	private List<Map<String, Object>> queryDistributeChartByCityByBoothId(DeviceMapper deviceDao,
			RequestParamOfBoothDetails param, List<String> deviceMacList) {
		if (deviceMacList.size() != 0) {
			// 场地只绑定一个采集器
			if (deviceMacList.size() == 1) {
				param.setDeviceMac(deviceMacList.get(0));
				return deviceDao.queryDistributeChartByCity(param);
			} else {
				// 场地绑定多个采集器
				List<Map<String, Object>> listResult = null;
				for (String deviceMac : deviceMacList) {
					param.setDeviceMac(deviceMac);
					List<Map<String, Object>> dataList = deviceDao.queryDistributeChartByCity(param);
					if (dataList != null) {
						if (listResult == null) {
							listResult = dataList;
						} else {
							for (Map<String, Object> map : dataList) {
								boolean flag = true;
								for (Map<String, Object> mapResult : listResult) {
									if (mapResult.get("city").equals(map.get("city"))) {
										mapResult.put("count", (Integer.parseInt(mapResult.get("count").toString())
												+ Integer.parseInt(map.get("count").toString())));
										flag = false;
										break;
									}
								}
								if (flag) {
									listResult.add(map);
								}
							}
						}
					}
				}
				return listResult;
			}
		}
		return null;
	}

	/**
	 * 查询行业分布
	 * 
	 * @param param
	 * @param deviceDao
	 * @return
	 */
	public Map<String, List<Object>> queryDistributeChartByIndustry(RequestParamOfBoothDetails param,
			DeviceMapper deviceDao, List<String> deviceMacList) {
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();
		List<Map<String, Object>> list = queryDistributeChartByIndustryByBoothId(deviceDao, param, deviceMacList);
		List<Object> data = new ArrayList<Object>();
		List<Object> name = new ArrayList<Object>();
		for (Map<String, Object> paramMap : list) {
			Map<String, Integer> singleMap = new HashMap<String, Integer>();
			singleMap.put(paramMap.get("industry").toString(), Integer.parseInt(paramMap.get("count").toString()));
			data.add(singleMap);
			name.add(paramMap.get("industry"));
		}
		map.put("data", data);
		map.put("name", name);
		return map;
	}

	/**
	 * 根据场地关联采集器数量多少整合行业分布数据
	 * 
	 * @param deviceDao
	 * @param param
	 * @return
	 */
	private List<Map<String, Object>> queryDistributeChartByIndustryByBoothId(DeviceMapper deviceDao,
			RequestParamOfBoothDetails param, List<String> deviceMacList) {
		if (deviceMacList.size() != 0) {
			// 场地只绑定一个采集器
			if (deviceMacList.size() == 1) {
				param.setDeviceMac(deviceMacList.get(0));
				return deviceDao.queryDistributeChartByIndustry(param);
			} else {
				// 场地绑定多个采集器
				List<Map<String, Object>> listResult = null;
				for (String deviceMac : deviceMacList) {
					param.setDeviceMac(deviceMac);
					List<Map<String, Object>> dataList = deviceDao.queryDistributeChartByIndustry(param);
					if (dataList != null) {
						if (listResult == null) {
							listResult = dataList;
						} else {
							for (Map<String, Object> map : dataList) {
								boolean flag = true;
								for (Map<String, Object> mapResult : listResult) {
									if (mapResult.get("industry").equals(map.get("industry"))) {
										mapResult.put("count", (Integer.parseInt(mapResult.get("count").toString())
												+ Integer.parseInt(map.get("count").toString())));
										flag = false;
										break;
									}
								}
								if (flag) {
									listResult.add(map);
								}
							}
						}
					}
				}
				return listResult;
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> queryActiveList(RequestParamOfBoothDetails param) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		DeviceMapper deviceDao = session.getMapper(DeviceMapper.class);
		TimeUtil.handleTime(param);
		if (!getPartitionInfo(deviceDao, param))
			return new ArrayList<>();
		return queryActiveListByBoothId(deviceDao, param);
	}

	/**
	 * 获取分区信息
	 * 
	 * @param deviceDao
	 * @param param
	 * @return
	 */
	private static boolean getPartitionInfo(DeviceMapper deviceDao, RequestParamOfBoothDetails param) {
		List<String> partitionInfoList = deviceDao.queryTablePartitionInfo("tb_report_visit_log_" + param.getUnitId());
		boolean flag = false;
		if (partitionInfoList.size() == 0)
			return flag;
		Date date = new Date();
		date.setTime(param.getEndTimeStamp() * 1000);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		int da = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		for (String partitionInfo : partitionInfoList) {
			if (partitionInfo.equals("pend"))
				continue;
			if (Integer.parseInt(partitionInfo.substring(1, 3)) == month
					&& Integer.parseInt(partitionInfo.substring(3)) == da) {
				param.setPartitionName(partitionInfo);
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 根据场地关联采集器数量多少整合行业分布数据
	 * 
	 * @param deviceDao
	 * @param param
	 * @return
	 */
	private List<Map<String, Object>> queryActiveListByBoothId(DeviceMapper deviceDao,
			RequestParamOfBoothDetails param) {
		List<String> deviceMacList = deviceDao.queryDeviceMacs(param.getBoothId());
		if (deviceMacList.size() != 0) {
			// 场地只绑定一个采集器
			if (deviceMacList.size() == 1) {
				param.setDeviceMac(deviceMacList.get(0));
				return deviceDao.queryActiveList(param);
			} else {
				// 场地绑定多个采集器
				List<Map<String, Object>> listResult = null;
				for (String deviceMac : deviceMacList) {
					param.setDeviceMac(deviceMac);
					List<Map<String, Object>> dataList = deviceDao.queryActiveList(param);
					if (dataList != null) {
						if (listResult == null) {
							listResult = dataList;
						} else {
							for (Map<String, Object> map : dataList) {
								boolean flag = true;
								for (Map<String, Object> mapResult : listResult) {
									if (mapResult.get("beaconMac").equals(map.get("beaconMac"))) {
										mapResult.put("duration",
												(Integer.parseInt(mapResult.get("duration").toString())
														+ Integer.parseInt(map.get("duration").toString())));
										flag = false;
										break;
									}
								}
								if (flag) {
									listResult.add(map);
								}
							}
						}
					}
				}
				return listResult;
			}
		}
		return null;
	}
}
