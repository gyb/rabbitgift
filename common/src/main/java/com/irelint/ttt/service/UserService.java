package com.irelint.ttt.service;

import java.util.List;

import com.irelint.ttt.dto.AddressDto;
import com.irelint.ttt.dto.UserDto;

public interface UserService {

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	UserDto register(UserDto user);

	/**
	 * 用户登录
	 * @param login
	 * @param password
	 * @return
	 */
	UserDto login(String login, String password);

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return
	 */
	UserDto get(Long id);

	/**
	 * 保存用户地址
	 * @param address
	 */
	void saveAddress(AddressDto address);

	/**
	 * 获取用户的所有地址
	 * @param userId
	 * @return
	 */
	List<AddressDto> findAddresses(Long userId);

	/**
	 * 删除一个地址
	 * @param addressId
	 */
	void deleteAddress(Long addressId);

}