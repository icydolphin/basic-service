package com.ctvit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ctvit.entity.PageInfo;
import com.ctvit.entity.UserInf;
import com.ctvit.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取单个用户
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value="/getUser",method=RequestMethod.GET,consumes = "application/json")
	@ResponseBody
	public JSONObject getUser(@RequestParam String loginName){
		JSONObject result = new JSONObject();
		List<UserInf> userInfList =  userService.getUserByLoginName(loginName);
		result.put("result", userInfList);
		result.put("msg", "success");
		return result;	
	}
	
	/**
	 * 分页获取用户
	 * @param pageInfo
	 * @return
	 */
	@RequestMapping(value="/getByPaging",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getByPaging(PageInfo pageInfo){
		JSONObject result = new JSONObject();
		List<UserInf> userInfList =  userService.getUserListByPaging(pageInfo);
		result.put("result", userInfList);
		result.put("msg", "success");
		result.put("pageInfo", pageInfo);
		return result;
	}

}
