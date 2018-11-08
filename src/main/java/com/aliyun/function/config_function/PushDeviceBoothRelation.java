package com.aliyun.function.config_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.common.util.BusinessUtil;
import com.issmart.device.model.DeviceConfigInfo;
import com.issmart.device.service.DeviceConfigService;
import com.issmart.device.serviceImpl.DeviceConfigServiceImpl;

/**
 * 推送展台与采集器关联关系
 * 
 * @param unitId
 * @param deviceMac
 * @return
 */
public class PushDeviceBoothRelation implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		try {
			String bodyBase64 = (String) requestParam.get("body");
			if (BusinessUtil.stringIsEmpty(bodyBase64)) {
				result.put("body", "The body is empty");
				return result;
			}
			// 获取body参数
			DeviceConfigInfo param = BusinessUtil.proParamAPIGatewayBodyToDeviceConfigInfo(bodyBase64);
			DeviceConfigService DeviceConfigService = new DeviceConfigServiceImpl();
			int count = 0;
			if("save".equals(param.getOpeFlag())) {
				count = DeviceConfigService.saveDeviceBoothRelation(param);
			} else if("delete".equals(param.getOpeFlag())) {
				count = DeviceConfigService.deleteDeviceBoothRelation(param);
			}
			context.getLogger().info("推送展台与采集器关联关系参数："+param);
			context.getLogger().info("推送展台与采集器关联关系结果："+count);
			response.success(count);
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("推送展台与采集器关联关系异常："+e.getMessage());
		}
		return result;
	}
}
