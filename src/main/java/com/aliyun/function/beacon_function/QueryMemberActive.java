package com.aliyun.function.beacon_function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.beacon.model.BeaconActiveInfo;
import com.issmart.beacon.service.BeaconService;
import com.issmart.beacon.serviceImpl.BeaconServiceImpl;
import com.issmart.common.model.Response;
import com.issmart.common.util.BusinessUtil;

/**
 * 查询用户活跃指数
 * 
 * @author CuiZhiXiang
 *
 */
public class QueryMemberActive implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		context.getLogger().info("查询用户活跃指数参数："+requestParam);
		try {
			// 获取参数
			@SuppressWarnings("unchecked")
			Map<String, Object> queryParameters = (Map<String, Object>) requestParam.get("queryParameters");
			if (queryParameters.get("unitId") == null || queryParameters.get("pageNo") == null || 
					queryParameters.get("pageSize") == null) {
				result.put("body", "The param is empty");
				return result;
			}
			String unitId = queryParameters.get("unitId").toString();
			Integer pageNo = Integer.parseInt(queryParameters.get("pageNo").toString());
			Integer pageSize = Integer.parseInt(queryParameters.get("pageSize").toString());
			// 参数校验
			if (pageNo == null || pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == null || pageSize == 0) {
				pageSize = 10;
			}
			BeaconService beaconService = new BeaconServiceImpl();
			Integer total = beaconService.getTotal(unitId);
			List<BeaconActiveInfo> list = beaconService.queryMemberActives(unitId, pageNo, pageSize);
			response.success(list);
			BusinessUtil.setPage(response, pageNo.toString(), pageSize.toString(), total);
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("查询用户活跃指数异常："+e.getMessage());
			result.put("body", response);
		}
		return result;
	}
}
