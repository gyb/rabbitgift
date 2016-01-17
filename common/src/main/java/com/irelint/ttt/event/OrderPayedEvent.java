package com.irelint.ttt.event;

import java.io.Serializable;

public class OrderPayedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;

	public OrderPayedEvent(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

}
