package com.ctvit.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
@Path("/cctv/test")
public class TestApi {
	
	@Path("/hello")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject hello(JSONObject data,@Context HttpServletRequest request){
		System.out.println(data);
		return data;
	}
	
	@Path("/formWay")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public JSONObject formWay(@FormParam("data") String param){
		System.out.println(param);
		return JSONObject.parseObject(param);
	}
	
	

}
