package com.aliyun.function.config_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.common.util.BusinessUtil;
import com.issmart.device.model.EventTemplate;
import com.issmart.device.service.DeviceConfigService;
import com.issmart.device.serviceImpl.DeviceConfigServiceImpl;

/**
 * 推送会议模板
 * 
 * @param unitId
 * @param deviceMac
 * @return
 */
public class PushEventTemplate implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

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
			Map<String, Object> param = BusinessUtil.proParamAPIGatewayBodyToMap(bodyBase64);
			Long startTimeStamp = Long.parseLong(param.get("startTimeStamp").toString());
			Long endTimeStamp = Long.parseLong(param.get("endTimeStamp").toString());
			String unitId = (String) param.get("unitId");
			String eventId = param.get("eventId").toString();
			EventTemplate paramBody = new EventTemplate(startTimeStamp, endTimeStamp,eventId, unitId);
			DeviceConfigService deviceConfigService = new DeviceConfigServiceImpl();
			Integer i = deviceConfigService.pushEventTemplate(context,paramBody);
			context.getLogger().info("推送会议模板参数："+paramBody);
			context.getLogger().info("推送会议模板结果："+i);
			response.success(i);
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("推送会议模板异常："+e.getMessage());
		}
		return result;
	}
}
