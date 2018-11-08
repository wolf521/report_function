package com.issmart.common.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.issmart.beacon.model.BeaconActiveInfo;
import com.issmart.common.dao.GetPressNumberMapper;
import com.issmart.common.dao.TimerMapper;
import com.issmart.common.service.TimerService;
import com.issmart.common.util.GradeUtil;
import com.issmart.common.util.MyBatisUtil;
import com.issmart.common.util.StringUtil;
import com.issmart.device.model.DeviceBaseInfo;

public class TimerServiceImpl implements TimerService {
	
	//*****************************************场地报表定时处理*****************************************//

	@Override
	public void opeBoothActiveTimer() {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		TimerMapper timerMapper = session.getMapper(TimerMapper.class);
		GetPressNumberMapper getPressNumberMapper = session.getMapper(GetPressNumberMapper.class);
		List<String> unitIdList = timerMapper.queryUnitIdList();
		if (unitIdList == null || unitIdList.size() == 0)
			return;
		for (String unitId : unitIdList) {
			Map<String, Object> map = timerMapper.queryBoothIndex(StringUtil.getReportTableName(unitId));
			List<DeviceBaseInfo> list = queryDeviceHot(timerMapper,getPressNumberMapper,unitId);
			if(list.size() == 0) continue;
			if(timerMapper.updateBoothReportResult(unitId, map) == 0) {
				timerMapper.insertBoothReportResult(unitId, map, list);
			} else {
				timerMapper.updateBoothReportResultBatch(unitId, list);
			}
		}
	}
	
	public List<DeviceBaseInfo> queryDeviceHot(TimerMapper timerMapper,GetPressNumberMapper getPressNumberMapper,String unitId) {
		List<DeviceBaseInfo> list = timerMapper.queryDeviceHot(StringUtil.getReportTableName(unitId),unitId);
		if (list.size() == 0) {
			return new ArrayList<>();
		}
		List<Integer> boothIdList = timerMapper.queryBoothIds(unitId);
		// 获取累计有效互动数
		List<Map<String, Object>> mapList = getPressNumberMapper.getActiveNumberMapperByDeviceMac(unitId, 1);
		for (DeviceBaseInfo deviceBaseInfo : list) {
			for (Map<String, Object> m1 : mapList) {
				if (deviceBaseInfo.getDeviceMac().equals(m1.get("deviceMac"))) {
					deviceBaseInfo.setMsgSendTotal(Integer.parseInt(m1.get("activeNumber").toString()));
					break;
				}
			}
		}
		// 汇总场地与采集器一对多的数据
		list = collectData(list, boothIdList);
		// 查询场地累计访问人数
		List<DeviceBaseInfo> bemberTotalList = timerMapper.queryBemberTotalByBoothId(StringUtil.getReportTableName(unitId),unitId);
		list = sort(list);
		List<Integer> boothIdLists = new ArrayList<>();
		for(DeviceBaseInfo deviceBaseInfo : list) {
			boothIdLists.add(deviceBaseInfo.getBoothId());
		}
		// 查询场地访问记录
		List<DeviceBaseInfo> logList = timerMapper.queryVisitLogByBoothId(StringUtil.getReportTableName(unitId),unitId,boothIdLists);
		// 处理场地累计访问人数及访问人次
		for (DeviceBaseInfo deviceBaseInfo : list) {
			for (DeviceBaseInfo deviceBaseInfo1 : bemberTotalList) {
				if (deviceBaseInfo.getBoothId().equals(deviceBaseInfo1.getBoothId())) {
					deviceBaseInfo.setBemberTotal(deviceBaseInfo1.getBemberTotal());
				}
			}
			if (deviceBaseInfo.getBemberTotal() != 0) {
				deviceBaseInfo.setDurationAvg(deviceBaseInfo.getDuration() / deviceBaseInfo.getBemberTotal());
			}
			deviceBaseInfo.setRealMemberTotal(opeRealMemberTotal(logList, unitId, deviceBaseInfo.getBoothId()));
		}
		list = sort(list);
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
		List<DeviceBaseInfo> logList = getlogList(logListParam,boothId);
		if(logList.size() == 0) return 0;
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
	
	public List<DeviceBaseInfo> getlogList(List<DeviceBaseInfo> logListParam,Integer boothId){
		List<DeviceBaseInfo> list = new ArrayList<>();
		for(DeviceBaseInfo deviceBaseInfo:logListParam) {
			if(boothId.equals(deviceBaseInfo.getBoothId())) {
				list.add(deviceBaseInfo);
			}
		}
		return list;
	}
	
	/**
	 * 汇总场地与采集器一对多的数据
	 */
	private List<DeviceBaseInfo> collectData(List<DeviceBaseInfo> list, List<Integer> boothIdList) {
		if (list.size() == 0) {
			return null;
		}
		List<DeviceBaseInfo> listResult = new ArrayList<>();
		for (Integer boothId : boothIdList) {
			DeviceBaseInfo deviceBaseInfo = null;
			for (DeviceBaseInfo deviceBaseInfoParam : list) {
				if (boothId.equals(deviceBaseInfoParam.getBoothId())) {
					if (deviceBaseInfo == null) {
						deviceBaseInfo = deviceBaseInfoParam;
					} else {
						deviceBaseInfo.setDuration(deviceBaseInfo.getDuration() + deviceBaseInfoParam.getDuration());
						deviceBaseInfo
								.setBemberTotal(deviceBaseInfo.getBemberTotal() + deviceBaseInfoParam.getBemberTotal());
						deviceBaseInfo.setRealMemberTotal(
								deviceBaseInfo.getRealMemberTotal() + deviceBaseInfoParam.getRealMemberTotal());
						deviceBaseInfo.setMsgSendTotal(
								deviceBaseInfo.getMsgSendTotal() + deviceBaseInfoParam.getMsgSendTotal());
					}
				}
			}
			if (deviceBaseInfo != null) {
				listResult.add(deviceBaseInfo);
			}
		}
		return listResult;
	}
	
	/**
	 * 对结果进行排序
	 * 
	 * @param list
	 * @return
	 */
	private List<DeviceBaseInfo> sort(List<DeviceBaseInfo> list) {
		Collections.sort(list, new Comparator<DeviceBaseInfo>() {
			@Override
			public int compare(DeviceBaseInfo o1, DeviceBaseInfo o2) {
				if (o1.getSortParam() < o2.getSortParam()) {
					return 1;
				}
				if (o1.getSortParam() == o2.getSortParam()) {
					return 0;
				}
				return -1;
			}
		});
		return list;
	}

	
	//*****************************************用户报表定时处理*****************************************//
	
	@Override
	public void opeMemberActiveTimer() {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		TimerMapper TimerMapper = session.getMapper(TimerMapper.class);
		GetPressNumberMapper getPressNumberMapper = session.getMapper(GetPressNumberMapper.class);
		List<String> unitIdList = TimerMapper.queryUnitIdList();
		if (unitIdList == null || unitIdList.size() == 0)
			return;
		for (String unitId : unitIdList) {
			List<BeaconActiveInfo> list = queryActiveScore(TimerMapper, getPressNumberMapper, unitId);
			int totalScore = 0;
			for (BeaconActiveInfo beaconActiveInfo : list) {
				totalScore += beaconActiveInfo.getScore();
			}
			totalScore = totalScore / list.size();
			if (TimerMapper.updateReportResult(unitId, totalScore) == 0) {
				TimerMapper.insertReportResult(unitId, totalScore, list);
			} else {
				TimerMapper.updateReportResultScoreBatch(unitId, list);
			}
		}
	}

	public List<BeaconActiveInfo> queryActiveScore(TimerMapper timerMapper,
			GetPressNumberMapper getPressNumberMapper, String unitId) {
		try {
			List<BeaconActiveInfo> list = timerMapper.queryMemberActives(StringUtil.getReportTableName(unitId),
					unitId);
			List<BeaconActiveInfo> repeatTotalList = timerMapper
					.queryActiveBoothRepeatTotal(StringUtil.getReportTableName(unitId), unitId);
			for (BeaconActiveInfo beaconActiveInfo : list) {
				beaconActiveInfo.setBoothRepeatTotal(
						opeBoothRepeatTotal(repeatTotalList, unitId, beaconActiveInfo.getBeaconMac()));
			}
			List<Map<String, Object>> mapList = getPressNumberMapper.getActiveNumberMapperByBeaconMac(unitId, 1);
			for (BeaconActiveInfo beaconActiveInfo : list) {
				for (Map<String, Object> m1 : mapList) {
					if (beaconActiveInfo.getBeaconMac().equals(m1.get("beaconMac"))) {
						beaconActiveInfo.setInteractTotal(Integer.parseInt(m1.get("activeNumber").toString()));
					}
				}
				Map<String, Object> map = new HashMap<>();
				map.put("beaconMac", beaconActiveInfo.getBeaconMac());
				map.put("boothRepeatTotal", beaconActiveInfo.getBoothRepeatTotal());
				if (beaconActiveInfo.getInteractTotal() == null) {
					Integer score = GradeUtil.gradeBooth(beaconActiveInfo.getBoothTotal())
							+ GradeUtil.gradeBoothRepeat(beaconActiveInfo.getBoothRepeatTotal())
							+ GradeUtil.gradeInteract(0)
							+ GradeUtil.gradeDurationTime(beaconActiveInfo.getDurationTimeTotal());
					beaconActiveInfo.setScore(score);
				} else {
					Integer score = GradeUtil.gradeBooth(beaconActiveInfo.getBoothTotal())
							+ GradeUtil.gradeBoothRepeat(beaconActiveInfo.getBoothRepeatTotal())
							+ GradeUtil.gradeInteract(beaconActiveInfo.getInteractTotal())
							+ GradeUtil.gradeDurationTime(beaconActiveInfo.getDurationTimeTotal());
					beaconActiveInfo.setScore(score);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理重复展台访问数
	 * 
	 * @param beaconMac
	 * @return
	 */
	private int opeBoothRepeatTotal(List<BeaconActiveInfo> repeatTotalList, String unitId, String beaconMac) {
		int sum = 0;
		List<BeaconActiveInfo> list = getRepeatTotalList(repeatTotalList, beaconMac);
		if (list.size() == 0) {
			return 0;
		}
		long calculateTimeStamp = list.get(0).getCalculateTimeStamp();
		String deviceMac = list.get(0).getDeviceMac();
		for (BeaconActiveInfo beaconActiveInfo : list) {
			if (deviceMac.equals(beaconActiveInfo.getDeviceMac())) {
				if ((beaconActiveInfo.getCalculateTimeStamp() - calculateTimeStamp) >= 300) {
					sum += 1;
				}
			} else {
				sum += 1;
			}
			calculateTimeStamp = beaconActiveInfo.getCalculateTimeStamp();
			deviceMac = beaconActiveInfo.getDeviceMac();
		}
		return sum;
	}

	/**
	 * 获取此BeaconMac所有数据
	 * 
	 * @param repeatTotalList
	 * @param beaconMac
	 * @return
	 */
	private List<BeaconActiveInfo> getRepeatTotalList(List<BeaconActiveInfo> repeatTotalList, String beaconMac) {
		List<BeaconActiveInfo> list = new ArrayList<>();
		for (BeaconActiveInfo beaconActiveInfo : repeatTotalList) {
			if (beaconMac.equals(beaconActiveInfo.getBeaconMac())) {
				list.add(beaconActiveInfo);
			}
		}
		return list;
	}
}
