package com.ctvit.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ctvit.entity.DitributeForm;

@Controller
@RequestMapping("test")
public class TestController {
	
	@RequestMapping(path="/hello",method=RequestMethod.POST,consumes="application/x-www-form-urlencoded")
	@ResponseBody
	public Map<String,Object> hello(@ModelAttribute DitributeForm ditributeForm){
		System.out.println(ditributeForm);
		Map<String,Object> queryJson = new HashMap<String, Object>();
		queryJson.put("message", "success");
		queryJson.put("code", 0);
		return queryJson;
	}
	@RequestMapping(value="/jsonWay",method=RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	public JSONObject jsonWay(@RequestBody JSONObject param,HttpServletRequest request){
		System.out.println(param);
		JSONObject response = param;
		return response;
	}

}
