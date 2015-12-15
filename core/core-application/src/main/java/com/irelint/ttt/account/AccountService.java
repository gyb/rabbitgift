package com.irelint.ttt.account;

import com.irelint.ttt.event.OrderReceivedEvent;
import com.irelint.ttt.event.ToPayOrderEvent;
import com.irelint.ttt.event.ToRefundOrderEvent;
import com.irelint.ttt.event.UserCreatedEvent;

public interface AccountService {

	Account get(Long userId);

	void createAccount(UserCreatedEvent event);

	AccountResult deposit(Long id, long money);

	AccountResult withdraw(Long id, long money);

	void pay(ToPayOrderEvent event);

	void refund(ToRefundOrderEvent event);
	
	void confirm(OrderReceivedEvent event);
}