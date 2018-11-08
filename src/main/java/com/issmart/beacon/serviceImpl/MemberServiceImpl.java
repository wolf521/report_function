package com.issmart.beacon.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public static void main(String[] args) {
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> m = new HashMap<>();
		Map<String, Object> m1 = new HashMap<>();
		Map<String, Object> m2 = new HashMap<>();
		m.put("memberId", 123);
		m1.put("memberId", 321);
		m2.put("memberId", 3321);
		m.put("beaconMac", "dsfsdf");
		m1.put("beaconMac", "dssssfsdf");
		m2.put("beaconMac", "dsf33fsdf");
		list.add(m);
		list.add(m1);
		list.add(m2);
		param.put("members", list);
		param.put("unitId", "309");
		System.out.println(new MemberServiceImpl().updateIncrMembers(param));
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
