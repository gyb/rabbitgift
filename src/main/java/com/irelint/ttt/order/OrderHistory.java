package com.irelint.ttt.order;

import java.sql.Timestamp;

import com.irelint.ttt.dao.Column;
import com.irelint.ttt.dao.IdEntity;
import com.irelint.ttt.user.User;

public class OrderHistory implements IdEntity {

	private static final long serialVersionUID = 1L;
	@Column private Long id;
	@Column private Long orderId;
	@Column private Long userId;
	@Column private Type type;
	@Column private Timestamp time;
	
	private User user;
	
	public enum Type {
		CREATE, PAY, DELIVER, RECEIVE, COMPLETE, CANCEL, REFUND
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public void setUser(User user) {
		this.user = user;
	}
}
