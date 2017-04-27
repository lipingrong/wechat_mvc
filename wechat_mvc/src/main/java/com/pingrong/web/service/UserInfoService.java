package com.pingrong.web.service;

import java.util.List;

import com.pingrong.web.entity.UserInfo;

public interface UserInfoService {
	/**
	 * 获取所有用户信息
	 * @return
	 * @throws Exception
	 */
	public List<UserInfo> getUser()throws Exception;
}
