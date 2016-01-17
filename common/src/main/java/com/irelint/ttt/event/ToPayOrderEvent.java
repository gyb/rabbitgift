package com.irelint.ttt.event;

import java.io.Serializable;

public class ToPayOrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long orderBuyerId;
	private long orderMoney;

	public ToPayOrderEvent(Long orderId, Long orderBuyerId, long orderMoney) {
		this.orderId = orderId;
		this.orderBuyerId = orderBuyerId;
		this.orderMoney = orderMoney;
	}

	public Long getOrderBuyerId() {
		return orderBuyerId;
	}

	public long getOrderMoney() {
		return orderMoney;
	}

	public Long getOrderId() {
		return orderId;
	}

}
