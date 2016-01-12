package com.irelint.ttt.dto;

import java.sql.Timestamp;

public class OrderHistoryDto {
	private HistoryType type;
	private Timestamp time;
	private Long userId;
	private UserDto user;
	
	public HistoryType getType() {
		return type;
	}

	public void setType(HistoryType type) {
		this.type = type;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserDto getUser() {
		return user;
	}

	public OrderHistoryDto setUser(UserDto user) {
		this.user = user;
		return this;
	}

}
