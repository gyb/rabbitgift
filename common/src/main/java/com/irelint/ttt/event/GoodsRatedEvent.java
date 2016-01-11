package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class GoodsRatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long goodsId;
	
	private int ratingNumber;

	public GoodsRatedEvent(Object source, Long goodsId, int ratingNumber) {
		super(source);
		this.goodsId = goodsId;
		this.ratingNumber = ratingNumber;
	}
	
	public Long getGoodsId() {
		return this.goodsId;
	}

	public int getRatingNumber() {
		return this.ratingNumber;
	}
}
