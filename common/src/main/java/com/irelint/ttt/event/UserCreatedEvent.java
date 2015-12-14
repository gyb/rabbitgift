package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	public UserCreatedEvent(Object source, Long userId) {
		super(source);
		this.userId = userId;
	}
	
	public Long getUserId() {
		return userId;
	}
}
