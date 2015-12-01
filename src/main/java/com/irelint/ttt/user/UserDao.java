package com.irelint.ttt.user;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
	User findByLogin(String login);
	User findByEmail(String email);
}
