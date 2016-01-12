package com.irelint.ttt.api;

import com.irelint.ttt.dto.AccountDto;
import com.irelint.ttt.dto.AccountResult;

public interface AccountApi {

	AccountDto get(Long userId);

	AccountResult deposit(Long id, long money);

	AccountResult withdraw(Long id, long money);

}