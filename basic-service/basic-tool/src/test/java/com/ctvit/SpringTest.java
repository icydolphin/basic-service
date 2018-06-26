package com.ctvit;

import com.alibaba.fastjson.JSONObject;
import com.ctvit.httptool.HttpKit;

public class SpringTest {

	public static void main(String[] args) throws Exception{
		String url = "http://127.0.0.1:8080/framework-spring/rest/test/hello";
		String resp = HttpKit.post(url, "objectId=234&cannelIds=5678");
		System.out.println(resp);

	}

}
