package com.irelint.ttt.event;

import java.io.Serializable;

public class GoodsRatedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long goodsId;
	
	private int ratingNumber;

	public GoodsRatedEvent(Long goodsId, int ratingNumber) {
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
