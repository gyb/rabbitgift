package com.irelint.ttt.order;

import java.sql.Timestamp;

import com.irelint.ttt.order.OrderHistory.Type;
import com.irelint.ttt.user.model.User;

public class OrderHistoryDto {
	private Type type;
	private Timestamp time;
	
	private User user;
	
	public OrderHistoryDto(OrderHistory history) {
		this.type = history.getType();
		this.time = history.getTime();
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public OrderHistoryDto setUser(User user) {
		this.user = user;
		return this;
	}

}
