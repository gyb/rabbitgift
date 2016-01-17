package com.irelint.ttt.event;

import java.io.Serializable;

public class ToRefundOrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long orderBuyerId;
	private long orderMoney;

	public ToRefundOrderEvent(Long orderId, Long orderBuyerId, long orderMoney) {
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
