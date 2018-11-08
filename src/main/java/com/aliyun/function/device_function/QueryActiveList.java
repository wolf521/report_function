package com.aliyun.function.device_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.common.util.BusinessUtil;
import com.issmart.device.model.RequestParamOfBoothDetails;
import com.issmart.device.serviceImpl.DeviceServiceImpl;

/**
 * 查询单展台活跃客户排名
 * 
 * @param unitId
 * @param deviceMac
 * @return
 */
public class QueryActiveList implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

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
			RequestParamOfBoothDetails paramBody = new RequestParamOfBoothDetails(
					Integer.parseInt(param.get("boothId").toString()),
					Integer.parseInt(param.get("quantumTime").toString()), 
					param.get("quantumUnit").toString(),
					Long.parseLong(param.get("endTimeStamp").toString()), 
					param.get("unitId").toString());
			context.getLogger().info("查询单展台活跃客户排名参数：" + paramBody);
			response.success(new DeviceServiceImpl().queryActiveList(paramBody));
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("查询单展台活跃客户排名异常：" + e.getMessage());
		}
		return result;
	}
}
