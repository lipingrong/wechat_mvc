package com.pingrong.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pingrong.web.dao.UserInfoMapper;
import com.pingrong.web.entity.UserInfo;
import com.pingrong.web.service.UserInfoService;
@Service
public class UserInfoServiceImpl implements UserInfoService{
	@Autowired
	private UserInfoMapper userMap;
	@Override
	public List<UserInfo> getUser() throws Exception {
		List<UserInfo> list = new ArrayList<UserInfo>();
		UserInfo info = userMap.selectByPrimaryKey((long)1);
		list.add(info);
		return list;
	}

}
