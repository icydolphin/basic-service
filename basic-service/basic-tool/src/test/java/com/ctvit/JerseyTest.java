package com.ctvit;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ctvit.httptool.HttpKit;

public class JerseyTest {
	
	public static void main(String[] args) throws Exception{
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("content-type", "application/json; charset=utf-8");
		JSONObject params = new JSONObject();
		params.put("测试", "value");
		String url = "http://127.0.0.1:8080/framework-spring/rest/test/jsonWay";
		String resp = HttpKit.post(url, JSONObject.toJSONString(params), headers);
		System.out.println(resp);
	}

}
