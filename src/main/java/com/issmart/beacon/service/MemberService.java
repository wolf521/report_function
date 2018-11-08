package com.issmart.beacon.service;

import java.util.Map;

import com.issmart.beacon.model.Member;

public interface MemberService {
	
	
	public Member findById(Integer memberId);
	
	/**
	 * 更新上传的参会用户信息
	 * @param param
	 * @return
	 */
	public boolean updateFullMembers(Map<String, Object> param);
	
	/**
	 * 增量更新用户信息
	 * @param param
	 * @return
	 */
	public boolean updateIncrMembers(Map<String, Object> param);
}
