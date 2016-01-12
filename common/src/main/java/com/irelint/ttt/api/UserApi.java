package com.irelint.ttt.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.dto.AddressDto;
import com.irelint.ttt.dto.UserDto;

public interface UserApi {

	UserDto get(Long userId);

	UserDto register(UserDto user);

	UserDto login(String login, String password);

	List<AddressDto> findAddresses(Long userId);

	void saveAddress(AddressDto address);

	void deleteAddress(Long addressId);

}