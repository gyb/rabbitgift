package com.irelint.ttt.order;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.irelint.ttt.dto.State;

@Entity
public class OrderGoods implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;

	private Long ownerId;
	
	private long price;
	
	private State state;

	public boolean isOnline() {
		return state == State.ONLINE;
	}

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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
