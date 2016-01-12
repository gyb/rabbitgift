package com.irelint.ttt.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.dto.AddressDto;
import com.irelint.ttt.dto.UserDto;
import com.irelint.ttt.service.UserService;

@Service
@DubboService(interfaceClass=UserApi.class)
public class UserApiImpl implements UserApi {
	@Autowired
	private UserService userService;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.UserApi#get(java.lang.Long)
	 */
	@Override
	public UserDto get(Long userId) {
		return userService.get(userId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.UserApi#register(com.irelint.ttt.dto.UserDto)
	 */
	@Override
	public UserDto register(UserDto user) {
		return userService.register(user);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.UserApi#login(java.lang.String, java.lang.String)
	 */
	@Override
	public UserDto login(String login, String password) {
		return userService.login(login, password);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.UserApi#findAddresses(java.lang.Long)
	 */
	@Override
	public List<AddressDto> findAddresses(Long userId) {
		return userService.findAddresses(userId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.UserApi#saveAddress(com.irelint.ttt.dto.AddressDto)
	 */
	@Override
	public void saveAddress(AddressDto address) {
		userService.saveAddress(address);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.UserApi#deleteAddress(java.lang.Long)
	 */
	@Override
	public void deleteAddress(Long addressId) {
		userService.deleteAddress(addressId);
	}

}
