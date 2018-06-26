package com.ctvit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctvit.dao.UserInfMapper;
import com.ctvit.entity.UserInf;
import com.ctvit.entity.UserInfExample;

@Service
public class UserService {
	
	@Autowired
	private UserInfMapper userInfMapper;
	
	public List<UserInf>  getUserByLoginName(String userName){
		UserInfExample userInfExample = new UserInfExample();
		userInfExample.createCriteria().andLoginNameEqualTo(userName);
		List<UserInf> userInf = userInfMapper.selectByExample(userInfExample);
		return userInf;
	}

}
