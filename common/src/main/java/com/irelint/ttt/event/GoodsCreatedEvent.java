package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

import com.irelint.ttt.dto.State;

public class GoodsCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Long goodsId;
	
	private Long ownerId;
	
	private long price;
	
	private State state;

	public GoodsCreatedEvent(Object source, Long goodsId, Long ownerId, long price, State state) {
		super(source);
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
