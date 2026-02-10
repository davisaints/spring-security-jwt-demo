package com.demo.auth_api.service.impl;

import com.demo.auth_api.model.User;
import com.demo.auth_api.repository.UserRepository;
import com.demo.auth_api.security.MyToken;
import com.demo.auth_api.security.TokenUtil;
import com.demo.auth_api.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User addUser(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		return repository.save(user);
	}

	@Override
	public User getUserByUsername(String username) {
		Optional<User> userOptional = repository.findByUsername(username);

		return userOptional.orElseThrow();
	}

	@Override
	public MyToken userLogin(User user) {
		User storedUser = repository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Unable to find user!"));

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		if (bCryptPasswordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
			return TokenUtil.encode(storedUser);
		}

		throw new RuntimeException("Unauthorized user!");
	}
}