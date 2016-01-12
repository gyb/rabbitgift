package com.irelint.ttt.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class GoodsDto {
	private Long id;
	
	private Long ownerId;
	
	private int categoryId;
	
	@Size(max=40)
	private String name;
	
	private String description;
	
	private String picUrl;
	
	@Min(1)
	private long price;
	
	private Timestamp onlineTime;
	private Timestamp offlineTime;
	private State state = State.CREATED;
	
	private int ratingNumber;
	private int ratingTimes;
	private float averageRating;

	private int ratingStars1;
	private int ratingStars2;
	private int ratingStars3;
	private int ratingStars4;
	private int ratingStars5;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public Timestamp getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Timestamp getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(Timestamp offlineTime) {
		this.offlineTime = offlineTime;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public int getRatingNumber() {
		return ratingNumber;
	}
	public void setRatingNumber(int ratingNumber) {
		this.ratingNumber = ratingNumber;
	}
	public int getRatingTimes() {
		return ratingTimes;
	}
	public void setRatingTimes(int ratingTimes) {
		this.ratingTimes = ratingTimes;
	}
	public float getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	public int getRatingStars1() {
		return ratingStars1;
	}
	public void setRatingStars1(int ratingStars1) {
		this.ratingStars1 = ratingStars1;
	}
	public int getRatingStars2() {
		return ratingStars2;
	}
	public void setRatingStars2(int ratingStars2) {
		this.ratingStars2 = ratingStars2;
	}
	public int getRatingStars3() {
		return ratingStars3;
	}
	public void setRatingStars3(int ratingStars3) {
		this.ratingStars3 = ratingStars3;
	}
	public int getRatingStars4() {
		return ratingStars4;
	}
	public void setRatingStars4(int ratingStars4) {
		this.ratingStars4 = ratingStars4;
	}
	public int getRatingStars5() {
		return ratingStars5;
	}
	public void setRatingStars5(int ratingStars5) {
		this.ratingStars5 = ratingStars5;
	}

}
