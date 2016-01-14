package com.irelint.ttt.dto;

import java.io.Serializable;

public class AccountDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private long userId;
	private long totalBalance;
	private long availableBalance;

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(long totalBalance) {
		this.totalBalance = totalBalance;
	}
	public long getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(long availableBalance) {
		this.availableBalance = availableBalance;
	}

}
