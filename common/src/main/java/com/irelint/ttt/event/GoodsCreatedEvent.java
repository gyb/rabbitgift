package com.irelint.ttt.event;

import java.io.Serializable;

import com.irelint.ttt.dto.State;

public class GoodsCreatedEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long goodsId;
	
	private Long ownerId;
	
	private long price;
	
	private State state;

	public GoodsCreatedEvent(Long goodsId, Long ownerId, long price, State state) {
		this.goodsId = goodsId;
		this.ownerId = ownerId;
		this.price = price;
		this.state = state;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public long getPrice() {
		return price;
	}

	public State getState() {
		return state;
	}
}
