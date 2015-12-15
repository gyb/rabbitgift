package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class OrderPayedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;

	public OrderPayedEvent(Object source, Long orderId) {
		super(source);
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

}
