package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.dto.AccountDto;
import com.irelint.ttt.dto.AccountResult;
import com.irelint.ttt.service.AccountService;

@Service
@DubboService(interfaceClass=AccountApi.class)
public class AccountApiImpl implements AccountApi {

	@Autowired
	private AccountService accountService;
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.AccountApi#get(java.lang.Long)
	 */
	@Override
	public AccountDto get(Long userId) {
		return accountService.get(userId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.AccountApi#deposit(java.lang.Long, long)
	 */
	@Override
	public AccountResult deposit(Long id, long money) {
		return accountService.deposit(id, money);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.AccountApi#withdraw(java.lang.Long, long)
	 */
	@Override
	public AccountResult withdraw(Long id, long money) {
		return accountService.withdraw(id, money);
	}
}
