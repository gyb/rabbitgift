package com.irelint.ttt.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.event.UserCreatedEvent;
import com.irelint.ttt.user.DuplicateEmailException;
import com.irelint.ttt.user.DuplicateLoginException;
import com.irelint.ttt.user.dao.AddressDao;
import com.irelint.ttt.user.dao.UserDao;
import com.irelint.ttt.user.model.Address;
import com.irelint.ttt.user.model.User;


@Service
public class UserServiceImpl implements UserService, ApplicationEventPublisherAware {
	@Autowired 
	private UserDao userDao;
	@Autowired 
	private AddressDao addressDao;
	
	private ApplicationEventPublisher publisher;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#register(com.irelint.ttt.user.User)
	 */
	@Override
	@Transactional
	public User register(User user) {
		User checkDuplicate = userDao.findByLogin(user.getLogin());
		if (checkDuplicate != null) throw new DuplicateLoginException();
		
		checkDuplicate = userDao.findByEmail(user.getEmail());
		if (checkDuplicate != null) throw new DuplicateEmailException();
		
		userDao.save(user);
		publisher.publishEvent(new UserCreatedEvent(this, user.getId()));

		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true)
	public User login(String login, String password) {
		User user = userDao.findByLogin(login);
		if (user != null && user.checkPassword(password)) {
			return user;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true) 
	public User get(Long id) {
		return userDao.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#saveAddress(com.irelint.ttt.user.Address)
	 */
	@Override
	@Transactional
	public void saveAddress(Address address) {
		addressDao.save(address);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#findAddresses(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Address> findAddresses(Long userId) {
		return addressDao.findByUserId(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.UserService#deleteAddress(java.lang.Long)
	 */
	@Override
	@Transactional
	public void deleteAddress(Long addressId) {
		addressDao.delete(addressId);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
