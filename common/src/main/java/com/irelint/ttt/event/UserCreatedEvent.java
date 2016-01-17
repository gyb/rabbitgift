package com.irelint.ttt.event;

import java.io.Serializable;

public class UserCreatedEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	public UserCreatedEvent(Long userId) {
		this.userId = userId;
	}
	
	public Long getUserId() {
		return userId;
	}
}
