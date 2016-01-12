package com.irelint.ttt.dto;

public class AccountResult {
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
