package com.ctvit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@RequestMapping(value="/",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String, Object> addData(@RequestParam("id")String id,@RequestParam(required=false,name="name") String name){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		resultMap.put("name", name);
		return resultMap;
	}

}
