package com.issmart.beacon.serviceImpl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.issmart.beacon.dao.MemberMapper;
import com.issmart.beacon.model.Member;
import com.issmart.beacon.service.MemberService;
import com.issmart.common.util.MyBatisUtil;

public class MemberServiceImpl implements MemberService {

	@Override
	public Member findById(Integer memberId) {
		Member member = null;
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(true);
		try {
			MemberMapper memberDao = session.getMapper(MemberMapper.class);
			member = memberDao.findById(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return member;
	}

	@Override
	public boolean updateFullMembers(Map<String, Object> param) {
		boolean point = false;
		String unitId = (String) param.get("unitId");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> members = (List<Map<String, Object>>) param.get("members");
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(false);
		try {
			MemberMapper memberDao = session.getMapper(MemberMapper.class);
			List<Member> beaconList = memberDao.findMemberListByUnitId(unitId);
			for(Map<String, Object> map:members) {
				boolean flag = true;
				for(Member member:beaconList) {
					if(map.get("beaconId") != null && map.get("beaconId").equals(member.getBeaconId())) {
						map.put("beaconMac", member.getBeaconMac());
						flag = false;
						break;
					} 
				}
				if(flag) {
					map.put("beaconMac", null);
				}
			}
			Integer pointdelAllNum = 0;
			Integer pointInsertsNum = 0;
			// 先删除已存在用户
			pointdelAllNum = memberDao.deleteByUnitId(unitId);
			pointInsertsNum = memberDao.inserts(unitId, members);
			// 失败回滚
			if (pointdelAllNum != 0 && pointInsertsNum == 0) {
				session.rollback();
			} else {
				session.commit();
				point = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return point;
	}

	@Override
	public boolean updateIncrMembers(Map<String, Object> param) {
		boolean point = false;
		String unitId = (String) param.get("unitId");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> members = (List<Map<String, Object>>) param.get("members");
		// 获得会话对象
		SqlSession session = MyBatisUtil.getSqlSession(false);
		try {
			MemberMapper memberDao = session.getMapper(MemberMapper.class);
			List<Member> beaconList = memberDao.findMemberListByUnitId(unitId);
			for(Map<String, Object> map:members) {
				for(Member member:beaconList) {
					if(map.get("beaconId") != null && map.get("beaconId").equals(member.getBeaconId())) {
						map.put("beaconMac", member.getBeaconMac());
					} 
				}
			}	
			if (members != null && members.size() != 0) {
				for(Map<String, Object> m:members) {
					m.put("unitId", unitId);
					if (memberDao.updateMap(m) == 0) {
						if(memberDao.insertMap(m) == 0) {
							point = false;
							break;
						}
					}
				}
			}
			point = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (point) {
			session.commit();
		} else {
			session.rollback();
		}
		return point;
	}
}
