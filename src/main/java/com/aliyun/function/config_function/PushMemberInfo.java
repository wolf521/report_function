package com.aliyun.function.config_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.beacon.service.MemberService;
import com.issmart.beacon.serviceImpl.MemberServiceImpl;
import com.issmart.common.model.Response;
import com.issmart.common.util.BusinessUtil;

/**
 * 推送签到用户信息
 * 
 * @author Administrator
 *
 */
public class PushMemberInfo implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestResult, Context context) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response responseBody = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		try {
			MemberService memberService = new MemberServiceImpl();
			String bodyBase64 = (String) requestResult.get("body");
			if (BusinessUtil.stringIsEmpty(bodyBase64)) {
				result.put("body", "The body is empty");
				return result;
			}
			// 获取body参数
			Map<String, Object> param = BusinessUtil.proParamAPIGatewayBodyToMap(bodyBase64);
			context.getLogger().info("推送签到用户信息参数："+param);
			String unitId = (String) param.get("unitId");
			if(BusinessUtil.stringIsEmpty(unitId)) {
				result.put("body", "The unit is empty");
				return result;
			}
			boolean flag = false;
			// 获取更新类型
			String type = (String) param.get("type");
			if (type.equals("full")) {
				flag = memberService.updateFullMembers(param);
			} else if (type.equals("incr")) {
				flag = memberService.updateIncrMembers(param);
			}
			context.getLogger().info("推送签到用户信息结果："+flag);
			responseBody.success(flag);
			result.put("body", responseBody);
		} catch (Exception e) {
			context.getLogger().info("推送签到用户信息异常："+e.getMessage());
			result.put("body", e.getMessage());
		}
		return result;
	}
}
