package com.irelint.ttt.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.irelint.ttt.user.UserService;
import com.irelint.ttt.user.model.Address;
import com.irelint.ttt.user.model.User;

public class UserApi {
	@Autowired
	private UserService userService;

	public User get(Long userId) {
		return userService.get(userId);
	}

	public void register(User user) {
		userService.register(user);
	}

	public User login(String login, String password) {
		return userService.login(login, password);
	}

	public List<Address> findAddresses(Long userId) {
		return userService.findAddresses(userId);
	}

	public void saveAddress(Address address) {
		userService.saveAddress(address);
	}

	public void deleteAddress(Long addressId) {
		userService.deleteAddress(addressId);
	}

}
