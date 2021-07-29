package com.test.olx.login.service;

import com.test.olx.login.model.User;

public interface LoginService {
	
	public String authenticateUserSer(User user);
	
	public Boolean logoutUserSer(String authToken);
	
	public User registerUserSer(User user);
	
	public User getUserInfoSer(String username);

	public User findByUsername(String userName);

}
