package com.issmart.active.device_function;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.issmart.device.model.DeviceBaseInfo;
import com.issmart.device.model.RequestParamOfBoothDetails;
import com.issmart.device.service.DeviceService;
import com.issmart.device.serviceImpl.DeviceServiceImpl;

public class DeviceServiceTest {
	
	@Test
	public void queryBoothIndex() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		Map<String,Integer> map = DeviceServiceDays.queryBoothIndex("920");
		System.out.println(map); 
	}
	
	@Test
	public void queryRealMemberTotalTest() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		System.out.println(DeviceServiceDays.queryRealMemberTotal("919",null,2));
	} 
	
	@Test 
	public void queryBestDrainage() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		System.out.println(DeviceServiceDays.queryBestDrainage("920"));
	}
	 
	@Test 
	public void queryBestAttract() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		System.out.println(DeviceServiceDays.queryBestAttract("920"));
	}
	
	@Test 
	public void queryBestActive() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		System.out.println(DeviceServiceDays.queryBestActive("919"));
	}

	@Test
	public void queryDeviceHot() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		List<DeviceBaseInfo> list = DeviceServiceDays.queryDeviceHot("920",1,10);
		System.out.println(list);
		if(list != null) {
			for(DeviceBaseInfo deviceBaseInfo:list) {
				System.out.println(deviceBaseInfo);
			}
		}
	}
	
	public static void main(String[] args) {
		long l1 =System.currentTimeMillis();
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		List<DeviceBaseInfo> list = DeviceServiceDays.queryDeviceHot("920",1,10);
		if(list != null) {
			for(DeviceBaseInfo deviceBaseInfo:list) {
				System.out.println(deviceBaseInfo);
			}
		}
		long l2 =System.currentTimeMillis();
		System.out.println(l2 - l1);
	}
	
	@Test
	public void querySingleBoothInfoTest() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		Map<String,Object> map = DeviceServiceDays.querySingleBoothInfo("919",256);
		System.out.println(map);
	}

	@Test
	public void queryTrendChartTest() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		RequestParamOfBoothDetails param = 
				new RequestParamOfBoothDetails(289, 24, "hh", 1540950500000l, "920");
		Map<String, List<Object>> list = DeviceServiceDays.queryTrendChart(param);
		System.out.println(param);
		System.out.println(list.get("quantumTimes"));
		System.out.println(list.get("memberNums"));
	}
	
	@Test
	public void queryDistributeChartTest() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		RequestParamOfBoothDetails param = 
				new RequestParamOfBoothDetails(256, 24, "hh", 1540564730000l, "919");
		Map<String,Map<String,List<Object>>> list = DeviceServiceDays.queryDistributeChart(param);
		System.out.println(list);
	}
	
	@Test
	public void queryActiveListTest() {
		DeviceService DeviceServiceDays = new DeviceServiceImpl();
		RequestParamOfBoothDetails param = 
				new RequestParamOfBoothDetails(256, 24, "hh", 1540864730000l, "919");
		List<Map<String,Object>> list = DeviceServiceDays.queryActiveList(param);
		System.out.println(list);
	}
}
