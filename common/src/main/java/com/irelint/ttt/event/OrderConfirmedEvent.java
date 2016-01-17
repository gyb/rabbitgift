package com.irelint.ttt.event;

import java.io.Serializable;

public class OrderConfirmedEvent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long orderId;

	public OrderConfirmedEvent(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return this.orderId;
	}
}
