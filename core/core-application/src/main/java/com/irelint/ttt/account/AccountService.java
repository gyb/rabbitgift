package com.irelint.ttt.account;

import com.irelint.ttt.event.UserCreatedEvent;

public interface AccountService {

	Account get(Long userId);

	void createAccount(UserCreatedEvent event);

	AccountResult deposit(Long id, long money);

	AccountResult withdraw(Long id, long money);

}