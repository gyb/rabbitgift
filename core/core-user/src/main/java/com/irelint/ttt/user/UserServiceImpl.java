package com.irelint.ttt.user;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.dto.AddressDto;
import com.irelint.ttt.dto.UserDto;
import com.irelint.ttt.event.UserCreatedEvent;
import com.irelint.ttt.exception.DuplicateEmailException;
import com.irelint.ttt.exception.DuplicateLoginException;
import com.irelint.ttt.service.UserService;
import com.irelint.ttt.user.dao.AddressDao;
import com.irelint.ttt.user.dao.UserDao;
import com.irelint.ttt.user.model.Address;
import com.irelint.ttt.user.model.User;
import com.irelint.ttt.util.Constants;


@Service
@DubboService(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
	@Autowired 
	private UserDao userDao;
	@Autowired 
	private AddressDao addressDao;
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#register(com.irelint.ttt.user.User)
	 */
	@Override
	@Transactional
	public UserDto register(UserDto dto) {
		User checkDuplicate = userDao.findByLogin(dto.getLogin());
		if (checkDuplicate != null) throw new DuplicateLoginException();
		
		checkDuplicate = userDao.findByEmail(dto.getEmail());
		if (checkDuplicate != null) throw new DuplicateEmailException();
		
		User user = User.fromDto(dto);
		userDao.save(user);
		amqpTemplate.convertAndSend(Constants.EXCHANGE_NAME, Constants.EVENT_USERCREATED, 
				new UserCreatedEvent(user.getId()));

		return user.toDto();
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public UserDto login(String login, String password) {
		User user = userDao.findByLogin(login);
		if (user != null && user.checkPassword(password)) {
			return user.toDto();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true) 
	public UserDto get(Long id) {
		User user = userDao.findOne(id);
		if (user != null) {
			return user.toDto();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#saveAddress(com.irelint.ttt.user.Address)
	 */
	@Override
	@Transactional
	public void saveAddress(AddressDto dto) {
		addressDao.save(Address.fromDto(dto));
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#findAddresses(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<AddressDto> findAddresses(Long userId) {
		return addressDao.findByUserId(userId).stream()
				.map(a -> a.toDto()).collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#deleteAddress(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteAddress(Long addressId) {
		addressDao.delete(addressId);
	}

}
