package com.irelint.ttt.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.dto.AddressDto;
import com.irelint.ttt.dto.UserDto;
import com.irelint.ttt.service.UserService;

@Service
@Transactional
public class UserApi {
	@Autowired
	private UserService userService;

	@Transactional(readOnly=true)
	public UserDto get(Long userId) {
		return userService.get(userId);
	}

	public UserDto register(UserDto user) {
		return userService.register(user);
	}

	public UserDto login(String login, String password) {
		return userService.login(login, password);
	}

	@Transactional(readOnly=true)
	public List<AddressDto> findAddresses(Long userId) {
		return userService.findAddresses(userId);
	}

	public void saveAddress(AddressDto address) {
		userService.saveAddress(address);
	}

	public void deleteAddress(Long addressId) {
		userService.deleteAddress(addressId);
	}

}
