package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class GoodsCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private Long goodsId;

	public GoodsCreatedEvent(Object source, Long goodsId) {
		super(source);
		this.goodsId = goodsId;
	}

	public Long getGoodsId() {
		return goodsId;
	}
}
