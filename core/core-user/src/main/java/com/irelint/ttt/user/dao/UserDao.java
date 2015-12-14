package com.irelint.ttt.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.irelint.ttt.user.model.User;

public interface UserDao extends CrudRepository<User, Long> {
	User findByLogin(String login);
	User findByEmail(String email);
}
