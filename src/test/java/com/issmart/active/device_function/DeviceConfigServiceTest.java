package com.issmart.active.device_function;

import org.junit.Test;

import com.issmart.device.model.DeviceConfigInfo;
import com.issmart.device.model.EventTemplate;
import com.issmart.device.service.DeviceConfigService;
import com.issmart.device.serviceImpl.DeviceConfigServiceImpl;

public class DeviceConfigServiceTest {

	@Test
	public void saveDeviceBoothRelation() {
		DeviceConfigService DeviceConfigService = new DeviceConfigServiceImpl();
		DeviceConfigInfo deviceConfig = new DeviceConfigInfo();
		deviceConfig.setUnitId("9195");
		deviceConfig.setBoothId(278);
		deviceConfig.setDeviceMac("00:1f:c2:26:54:f6");
		deviceConfig.setBoothType(33444);
		deviceConfig.setCoordsX(333666.888);
		deviceConfig.setCoordsY(33.333666);
		deviceConfig.setCoordsZ(333666.77);
		deviceConfig.setRssi(85);
		deviceConfig.setLabel("互联网,智能,网络");
		deviceConfig.setBoothTypeName("蝴蝶结");
		System.out.println(DeviceConfigService.saveDeviceBoothRelation(deviceConfig));		
	}
	
	@Test
	public void deleteDeviceBoothRelation() {
		DeviceConfigService DeviceConfigService = new DeviceConfigServiceImpl();
		DeviceConfigInfo deviceConfig = new DeviceConfigInfo();
		deviceConfig.setUnitId("9195");
		deviceConfig.setBoothId(278);
		deviceConfig.setDeviceMac("00:1f:c2:26:54:f6");
		System.out.println(DeviceConfigService.deleteDeviceBoothRelation(deviceConfig));	
	}
	
	@Test
	public void pushEventTemplateTest() {
		DeviceConfigService DeviceConfigService = new DeviceConfigServiceImpl();
		EventTemplate eventTemplate = new EventTemplate();
		eventTemplate.setUnitId("919");
		eventTemplate.setEventId("345");
		eventTemplate.setStatus("on");
		eventTemplate.setStartTimeStamp(1540346330l);
		eventTemplate.setEndTimeStamp(1541210330l);
		System.out.println(DeviceConfigService.pushEventTemplate(null, eventTemplate));
	}
}
