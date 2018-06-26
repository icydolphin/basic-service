package com.ctvit;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ctvit.httptool.HttpKit;

public class JerseyFormTest {
	
	public static void main(String[] args) throws Exception{
		JSONObject params = new JSONObject();
		params.put("测试", "value");
		String url = "http://127.0.0.1:8080/framework-jersey/rest/cctv/test/formWay";
		String resp = HttpKit.post(url, "data="+JSONObject.toJSONString(params));
		System.out.println(resp);
	}

}
