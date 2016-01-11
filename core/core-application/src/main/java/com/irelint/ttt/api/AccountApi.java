package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.account.Account;
import com.irelint.ttt.account.AccountResult;
import com.irelint.ttt.account.AccountService;

@Service
@Transactional
public class AccountApi {

	@Autowired
	private AccountService accountService;
	
	@Transactional(readOnly=true)
	public Account get(Long userId) {
		return accountService.get(userId);
	}

	public AccountResult deposit(Long id, long money) {
		return accountService.deposit(id, money);
	}

	public AccountResult withdraw(Long id, long money) {
		return accountService.withdraw(id, money);
	}
}
