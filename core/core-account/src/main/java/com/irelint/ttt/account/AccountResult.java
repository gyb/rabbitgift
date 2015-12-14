package com.irelint.ttt.account;

public class AccountResult {
	public boolean result;
	public Account account;
	
	public boolean success() {
		return result;
	}
	
	public boolean fail() {
		return !result;
	}
	
	public static AccountResult fail(Account account) {
		AccountResult result = new AccountResult();
		result.account = account;
		return result;
	}
	
	public static AccountResult success(Account account) {
		AccountResult result = new AccountResult();
		result.account = account;
		result.result = true;
		return result;
	}
}
