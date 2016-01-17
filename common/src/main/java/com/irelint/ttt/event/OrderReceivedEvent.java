package com.irelint.ttt.event;

import java.io.Serializable;

public class OrderReceivedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long orderBuyerId;
	private Long orderSellerId;
	private long orderMoney;

	public OrderReceivedEvent(Long orderId, Long orderBuyerId, Long orderSellerId, long orderMoney) {
		this.orderBuyerId = orderBuyerId;
		this.orderSellerId = orderSellerId;
		this.orderMoney = orderMoney;
	}

	public Long getOrderBuyerId() {
		return orderBuyerId;
	}
	
	public Long getOrderSellerId() {
		return orderSellerId;
	}

	public long getOrderMoney() {
		return orderMoney;
	}

	public Long getOrderId() {
		return orderId;
	}

}
