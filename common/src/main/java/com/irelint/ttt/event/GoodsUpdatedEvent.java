package com.irelint.ttt.event;

import java.io.Serializable;

import com.irelint.ttt.dto.State;

public class GoodsUpdatedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long goodsId;
	
	private long price;
	
	private State state;

	public GoodsUpdatedEvent(Long goodsId, long price, State state) {
		this.goodsId = goodsId;
		this.price = price;
		this.state = state;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public long getPrice() {
		return price;
	}

	public State getState() {
		return state;
	}

}
