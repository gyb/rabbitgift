package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class GoodsUpdatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long goodsId;
	
	private long price;
	
	private State state;

	public GoodsUpdatedEvent(Object source, Long goodsId, long price, State state) {
		super(source);
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
