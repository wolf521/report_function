package com.issmart.beacon.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.issmart.beacon.dao.BeaconMapper;
import com.issmart.beacon.model.BeaconActive;
import com.issmart.beacon.model.BeaconActiveInfo;
import com.issmart.beacon.service.BeaconService;
import com.issmart.common.dao.GetPressNumberMapper;
import com.issmart.common.util.BusinessUtil;
import com.issmart.common.util.MyBatisUtil;
import com.issmart.common.util.StringUtil;

public class BeaconServiceImpl implements BeaconService {
	
	@Override
	public Map<String, Object> queryOverallIndicators(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		int memberNumber = beaconDao.queryMemberNumber(unitId);
		Map<String,Object> map =  beaconDao.queryOverallIndicators(unitId);
		if(memberNumber != 0) {
			map.put("interactionAvgNumber", Integer.parseInt(map.get("interactionTotalNumber").toString()) / memberNumber);
			map.put("interactionPartInRate", Double.parseDouble(map.get("activeUserNumber").toString()) / memberNumber);
		} else {
			map.put("interactionAvgNumber", 0);
			map.put("interactionPartInRate", 0);
		}
		map.put("activeComprehensiveScore", beaconDao.queryActiveScore(unitId));
		return map;
	}

	@Override
	public List<BeaconActiveInfo> queryMemberActives(String unitId, int pageNo, int pageSize) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		GetPressNumberMapper getPressNumberMapper = session.getMapper(GetPressNumberMapper.class);
		List<BeaconActiveInfo> list = beaconDao.queryMemberActivesResult(unitId, BusinessUtil.processPageNo(pageNo, pageSize), pageSize);
		List<Map<String, Object>> mapList = getPressNumberMapper.getActiveNumberMapperByBeaconMac(unitId, 1);
		for (BeaconActiveInfo beaconActiveInfo : list) {
			for (Map<String, Object> m1 : mapList) {
				if (beaconActiveInfo.getBeaconMac().equals(m1.get("beaconMac"))) {
					beaconActiveInfo.setInteractTotal(Integer.parseInt(m1.get("activeNumber").toString()));
				}
			}
		}
		return list;
	}

	@Override
	public Integer getTotal(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		return beaconDao.getTotal(unitId);
	}

	@Override
	public LinkedList<BeaconActive> queryActive(String unitId,String beaconMac) {
		LinkedList<BeaconActive> list = handleActiveData(unitId,beaconMac);
		for (BeaconActive beaconActive:list) {
			beaconActive.setMemberName(null);
			beaconActive.setLeaveTime(null);
			beaconActive.setDeviceMac(null);
		}
		return list;
	}

	@Override
	public Map<String, String> queryInterestedBooth(String unitId, String beaconMac) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		return beaconDao.queryInterestedBooth(unitId, beaconMac);
	}

	@Override
	public List<BeaconActive> queryMemberNameByBeaconMac(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		return beaconDao.queryMemberNameByBeaconMac(unitId);
	}

	@Override
	public List<BeaconActive> queryActiveList(String unitId,String beaconMac) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		return beaconDao.queryActive(unitId,beaconMac);
	}
	/**
	 * 处理个人轨迹数据
	 * 
	 * @param unitId
	 * @param beaconMac
	 * @return
	 */
	public LinkedList<BeaconActive> handleActiveData(String unitId,String beaconMac) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		List<BeaconActive> list = beaconDao.queryActive(unitId,beaconMac);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LinkedList<BeaconActive> activeList = new LinkedList<BeaconActive>();
		list.get(0).setArriveTime(format.format(list.get(0).getLeaveTime()));
		activeList.add(list.get(0));
		for(BeaconActive beaconActive:list) {
			BeaconActive lastBeaconActive = activeList.getLast();
			if(beaconActive.getDeviceMac().equals(lastBeaconActive.getDeviceMac()) && 
					judgeIffiveMin(beaconActive.getLeaveTimeStamp(), lastBeaconActive.getLeaveTimeStamp())) {
				lastBeaconActive.setLeaveTimeStamp(beaconActive.getLeaveTimeStamp());
				int stayTime = lastBeaconActive.getStayTime() + 1;
				lastBeaconActive.setStayTime(stayTime);
			} else {
				activeList.addLast(beaconActive);
			}
			beaconActive.setArriveTime(format.format(beaconActive.getLeaveTime()));
		}
		return activeList;
	}

	/**
	 * 处理轨迹数据
	 * 
	 * @param unitId
	 * @return
	 */
//	public Map<String, Map<String, LinkedList<BeaconActive>>> opeActiveData(String unitId) {
//		// 获得会话对象
//		SqlSession session = MyBatisUtil.getSqlSession(true);
//		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
//		List<BeaconActive> list = beaconDao.queryActive(unitId,null);
//		Map<String, Map<String, LinkedList<BeaconActive>>> map = new HashMap<String, Map<String, LinkedList<BeaconActive>>>();
//		for (BeaconActive beaconActive : list) {
//			if (map.containsKey(beaconActive.getDeviceMac())) {
//				Map<String, LinkedList<BeaconActive>> deviceMap = map.get(beaconActive.getDeviceMac());
//				if (deviceMap.containsKey(beaconActive.getBeaconMac())) {
//					LinkedList<BeaconActive> beaconList = deviceMap.get(beaconActive.getBeaconMac());
//					BeaconActive active = beaconList.getLast();
//					if (active.getLeaveTime() != null
//							&& judgeIffiveMin(active.getLeaveTime(), beaconActive.getLeaveTime())) {
//						active.setLeaveTime(beaconActive.getLeaveTime());
//						active.setStayTime(active.getStayTime() + beaconActive.getStayTime());
//					} else {
//						beaconList.add(beaconActive);
//					}
//				} else {
//					LinkedList<BeaconActive> activeList = new LinkedList<BeaconActive>();
//					activeList.add(beaconActive);
//					deviceMap.put(beaconActive.getBeaconMac(), activeList);
//				}
//			} else {
//				Map<String, LinkedList<BeaconActive>> activeMap = new HashMap<String, LinkedList<BeaconActive>>();
//				LinkedList<BeaconActive> newList = new LinkedList<BeaconActive>();
//				newList.add(beaconActive);
//				activeMap.put(beaconActive.getBeaconMac(), newList);
//				map.put(beaconActive.getDeviceMac(), activeMap);
//			}
//		}
//		return map;
//	}

	/**
	 * 处理轨迹数据2
	 * 
	 * @param unitId
	 * @return
	 */
//	private Map<String, List<BeaconActive>> opeActiveData2(Map<String, Map<String, LinkedList<BeaconActive>>> map) {
//		Map<String, List<BeaconActive>> contentMap = new HashMap<String, List<BeaconActive>>();
//		for (Entry<String, Map<String, LinkedList<BeaconActive>>> entry : map.entrySet()) {
//			for (Entry<String, LinkedList<BeaconActive>> beaconEntry : entry.getValue().entrySet()) {
//				if (contentMap.containsKey(beaconEntry.getKey())) {
//					List<BeaconActive> list = contentMap.get(beaconEntry.getKey());
//					list.addAll(beaconEntry.getValue());
//				} else {
//					List<BeaconActive> list = beaconEntry.getValue();
//					contentMap.put(beaconEntry.getKey(), list);
//				}
//			}
//		}
//		return contentMap;
//	}

	/**
	 * 处理轨迹数据3
	 * 
	 * @param unitId
	 * @return
	 */
//	private Map<String, List<BeaconActive>> opeActiveData3(Map<String, List<BeaconActive>> map, String unitId) {
//		List<BeaconActive> beaconActiveList = queryMemberNameByBeaconMac(unitId);
//		Map<String, List<BeaconActive>> contentMaps = new HashMap<String, List<BeaconActive>>();
//		if (beaconActiveList.size() != 0) {
//			for (BeaconActive beaconActive : beaconActiveList) {
//				// 内容列表
//				List<BeaconActive> contentList = map.get(beaconActive.getBeaconMac());
//				contentMaps.put(beaconActive.getMemberName() + "_" + beaconActive.getBeaconMac(), contentList);
//			}
//		}
//		return contentMaps;
//	}

	/**
	 * 判断两个时间差是否相差5分钟
	 * 
	 * @return
	 */
	private static boolean judgeIffiveMin(long l1, long l2) {
		return (l1 - l2) <= 300l;
	}

	/**
	 * 查询当前beacon扫一扫数据
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryMemberByNearBy(String unitId, String beaconId, Integer minutes,
			Integer rssi) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		String deviceMac = beaconDao.getDeviceMac(unitId, beaconId, minutes);
		if (StringUtil.isNotEmpty(deviceMac)) {
			return beaconDao.queryMemberByNearBy(unitId, beaconId, deviceMac, minutes, rssi);
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> queryInteractionAnalysis(String unitId) {
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		BeaconMapper beaconDao = session.getMapper(BeaconMapper.class);
		int memberNumber = beaconDao.queryMemberNumber(unitId);
		List<Map<String,Object>> mapList = beaconDao.queryInteractionAnalysis(unitId);
		if(mapList.size() == 0) return mapList;
		for(Map<String,Object> map:mapList) {
			map.put("interactionPartInRate", (Double.parseDouble(map.get("partInNumber").toString()) / memberNumber));
			map.put("perInteraction",  (Integer.parseInt(map.get("effectiveInteractionNumber").toString()) / memberNumber));
		}
		return mapList;
	}
}
