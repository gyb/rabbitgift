package com.irelint.ttt.user;

import java.util.List;

import com.irelint.ttt.user.model.Address;
import com.irelint.ttt.user.model.User;

public interface UserService {

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	User register(User user);

	/**
	 * 用户登录
	 * @param login
	 * @param password
	 * @return
	 */
	User login(String login, String password);

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return
	 */
	User get(Long id);

	/**
	 * 保存用户地址
	 * @param address
	 */
	void saveAddress(Address address);

	/**
	 * 获取用户的所有地址
	 * @param userId
	 * @return
	 */
	List<Address> findAddresses(Long userId);

	/**
	 * 删除一个地址
	 * @param addressId
	 */
	void deleteAddress(Long addressId);

}