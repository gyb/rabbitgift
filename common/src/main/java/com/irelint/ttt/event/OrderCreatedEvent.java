package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class OrderCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long goodsId;
	private int goodsNumber;

	public OrderCreatedEvent(Object source, Long orderId, Long goodsId, int goodsNumber) {
		super(source);
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsNumber = goodsNumber;
	}
	
	public Long getGoodsId() {
		return goodsId;
	}

	public Long getOrderId() {
		return orderId;
	}
	
	public int getGoodsNumber() {
		return goodsNumber;
	}
}
