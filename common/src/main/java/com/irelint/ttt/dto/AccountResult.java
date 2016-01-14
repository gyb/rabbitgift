package com.irelint.ttt.dto;

import java.io.Serializable;

public class AccountResult implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean result;
	public AccountDto account;
	
	public boolean success() {
		return result;
	}
	
	public boolean fail() {
		return !result;
	}
	
	public static AccountResult fail(AccountDto account) {
		AccountResult result = new AccountResult();
		result.account = account;
		return result;
	}
	
	public static AccountResult success(AccountDto account) {
		AccountResult result = new AccountResult();
		result.account = account;
		result.result = true;
		return result;
	}
}
