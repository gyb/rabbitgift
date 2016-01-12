package com.irelint.ttt.order;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.irelint.ttt.dto.RatingDto;

@Entity
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Long goodsId;
	
	@NotNull
	private Long orderId;
	
	@NotNull
	private Long userId;

	@NotNull
	private String username;
	
	private Timestamp ratingTime;
	
	@NotNull
	@Min(1)
	@Max(5)
	private int number;
	
	@Size(max=1024) private String comment;

	private Timestamp buyTime;
	private long buyPrice;
	
	public RatingDto toDto() {
		RatingDto dto = new RatingDto();
		dto.setGoodsId(goodsId);
		dto.setUserId(userId);
		dto.setUsername(username);
		dto.setOrderId(orderId);
		dto.setRatingTime(ratingTime);
		dto.setBuyPrice(buyPrice);
		dto.setBuyTime(buyTime);
		dto.setRatingTime(ratingTime);
		dto.setNumber(number);
		dto.setComment(comment);
		return dto;
	}

	public static Rating fromDto(RatingDto ratingDto) {
		Rating rating = new Rating();
		rating.setBuyPrice(ratingDto.getBuyPrice());
		rating.setBuyTime(ratingDto.getBuyTime());
		rating.setComment(ratingDto.getComment());
		rating.setGoodsId(ratingDto.getGoodsId());
		rating.setNumber(ratingDto.getNumber());
		rating.setOrderId(ratingDto.getOrderId());
		rating.setRatingTime(ratingDto.getRatingTime());
		rating.setUserId(ratingDto.getUserId());
		rating.setUsername(ratingDto.getUsername());
		return rating;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
