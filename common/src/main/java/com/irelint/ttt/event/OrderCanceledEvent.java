package com.irelint.ttt.event;

import java.io.Serializable;

public class OrderCanceledEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long goodsId;
	private int goodsNumber;

	public OrderCanceledEvent(Long orderId, Long goodsId, int goodsNumber) {
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
