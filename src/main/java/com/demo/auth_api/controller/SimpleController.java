package com.demo.auth_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

	@GetMapping("/open")
	public String openEndpoint() {
		return "This is an open endpoint.";
	}

	@GetMapping("/restricted")
	public String restrictedEndpoint() {
		return "This is a restricted endpoint.";
	}
}