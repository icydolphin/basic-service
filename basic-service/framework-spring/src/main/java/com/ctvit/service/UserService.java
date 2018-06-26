package com.ctvit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctvit.dao.UserInfMapper;
import com.ctvit.entity.PageInfo;
import com.ctvit.entity.UserInf;
import com.ctvit.entity.UserInfExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

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
	
	public List<UserInf> getUserListByPaging(PageInfo pageInfo){
		Page page = PageHelper.startPage(pageInfo.getCurPage(), pageInfo.getRows(), true);
		UserInfExample userInfExample = new UserInfExample();
		List<UserInf> userInf = userInfMapper.selectByExample(userInfExample);
		pageInfo.setTotal(Integer.parseInt(page.getTotal()+""));
		return userInf;
	}

}
