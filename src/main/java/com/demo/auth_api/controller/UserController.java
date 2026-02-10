package com.demo.auth_api.controller;

import com.demo.auth_api.model.User;
import com.demo.auth_api.security.MyToken;
import com.demo.auth_api.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<MyToken> login(@RequestBody User user) {
		return new ResponseEntity<>(userService.userLogin(user), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<User> postUser(@RequestBody User user) {
		return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
	}
}