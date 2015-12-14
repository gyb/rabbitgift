package com.irelint.ttt.goods.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Goods implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull 
	private Long ownerId;
	
	private int categoryId;
	
	@NotBlank 
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
	
	@Version
	private int version;
	
	public Goods() {}
	
	public Goods(Long id) {
		this.id = id;
	}
	
	public Goods createCopy() {
		Goods copy = new Goods();
		copy.setName(this.getName());
		copy.setDescription(this.getDescription());
		copy.setCategoryId(this.getCategoryId());
		copy.setPrice(this.getPrice());
		copy.setPicUrl(this.getPicUrl());
		copy.setOwnerId(this.getOwnerId());
		copy.setState(Goods.State.CREATED);
		return copy;
	}
	
	public boolean putOnline() {
		if (!isNew()) {
			return false;
		}

		this.setState(Goods.State.ONLINE);
		this.setOnlineTime(new Timestamp(System.currentTimeMillis()));
		return true;
	}
	
	public boolean putOffline() {
		if (!isOnline()) {
			return false;
		}

		this.setState(Goods.State.OFFLINE);
		this.setOfflineTime(new Timestamp(System.currentTimeMillis()));
		return true;
	}
	
	public boolean isOnline() {
		return state == State.ONLINE;
	}
	
	public boolean isNew() {
		return state == State.CREATED;
	}
	
	public boolean isOffline() {
		return state == State.OFFLINE;
	}

	public enum State {
		CREATED,
		ONLINE,
		OFFLINE
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long userId) {
		this.ownerId = userId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
	
	public void addRating(int number) {
		if (number < 1 || number > 5) throw new IllegalArgumentException(
				"Rating should be between 1 and 5, but it is " + number);
		
		this.ratingTimes++;
		this.ratingNumber += number;
		this.averageRating = (float)ratingNumber / ratingTimes;
		switch (number) {
		case 1:
			this.ratingStars1++;
			break;
		case 2:
			this.ratingStars2++;
			break;
		case 3:
			this.ratingStars3++;
			break;
		case 4:
			this.ratingStars4++;
			break;
		case 5:
			this.ratingStars5++;
			break;
		}
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
