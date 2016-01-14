package com.irelint.ttt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class RatingDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long goodsId;
	private Long userId;
	private Long orderId;

	private String username;
	private Timestamp ratingTime;
	private int number;
	private String comment;
	private Timestamp buyTime;
	private long buyPrice;

	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getRatingTime() {
		return ratingTime;
	}
	public void setRatingTime(Timestamp ratingTime) {
		this.ratingTime = ratingTime;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}
	public long getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(long buyPrice) {
		this.buyPrice = buyPrice;
	}

}
