package com.demo.auth_api.repository;

import com.demo.auth_api.model.User;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
}