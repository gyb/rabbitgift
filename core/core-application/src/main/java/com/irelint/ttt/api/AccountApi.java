package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.dto.AccountDto;
import com.irelint.ttt.dto.AccountResult;
import com.irelint.ttt.service.AccountService;

@Service
@Transactional
public class AccountApi {

	@Autowired
	private AccountService accountService;
	
	@Transactional(readOnly=true)
	public AccountDto get(Long userId) {
		return accountService.get(userId);
	}

	public AccountResult deposit(Long id, long money) {
		return accountService.deposit(id, money);
	}

	public AccountResult withdraw(Long id, long money) {
		return accountService.withdraw(id, money);
	}
}
