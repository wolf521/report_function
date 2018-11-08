package com.issmart.active.beacon_function;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.issmart.beacon.model.BeaconActive;
import com.issmart.beacon.model.BeaconActiveInfo;
import com.issmart.beacon.service.BeaconService;
import com.issmart.beacon.serviceImpl.BeaconServiceImpl;
import com.issmart.common.model.Response;
import com.issmart.common.util.BusinessUtil;

public class BeaconServiceTest {

	public static void main(String[] args) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		Response response = new Response();
		String unitId = "919";
		Integer pageNo = 1;
		Integer pageSize = 10;
		BeaconService beaconService = new BeaconServiceImpl();
		Integer total = beaconService.getTotal(unitId);
		List<BeaconActiveInfo> list = beaconService.queryMemberActives(unitId, pageNo, pageSize);
		for(BeaconActiveInfo beaconActiveInfo:list) {
			System.out.println(beaconActiveInfo);
		}
		response.success(list);
		BusinessUtil.setPage(response, pageNo.toString(), pageSize.toString(), total);
		result.put("body", response);
		System.out.println(JSON.toJSONString(response));
		System.out.println(result);
	}
	
	
	@Test
	public void queryActive() {
		BeaconService BeaconService = new BeaconServiceImpl();
		LinkedList<BeaconActive> list = BeaconService.queryActive("919","F0F8F2DA979F");
		for(BeaconActive beaconActive:list) {
			System.out.println(beaconActive);
		}
	}
	
	@Test
	public void queryOverallIndicators() {
		BeaconService beaconService = new BeaconServiceImpl();
		Map<String,Object> map = beaconService.queryOverallIndicators("919");
		System.out.println(map);
	}

	@Test
	public void queryInteractionAnalysis() {
		BeaconService beaconService = new BeaconServiceImpl();
		List<Map<String,Object>> map = beaconService.queryInteractionAnalysis("919");
		System.out.println(map);
	}
}
