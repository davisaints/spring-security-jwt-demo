package com.demo.auth_api.service;

import com.demo.auth_api.model.User;
import com.demo.auth_api.security.MyToken;

public interface UserService {
	public User addUser(User user);

	public User getUserByUsername(String email);

	public MyToken userLogin(User user);
}
