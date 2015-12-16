package com.irelint.ttt.order;

import java.sql.Timestamp;

import com.irelint.ttt.goods.model.Goods;
import com.irelint.ttt.order.Order.State;
import com.irelint.ttt.user.model.User;

public class OrderDto {
	
	public OrderDto(Order order) {
		this.id = order.getId();
		this.money = order.getMoney();
		this.num = order.getNum();
		this.receiverName = order.getReceiverName();
		this.address = order.getAddress();
		this.phone = order.getPhone();
		this.state = order.getState();
		this.lastUpdateTime = order.getLastUpdateTime();
	}

	private Long id;
	private long money;
	
	private int num;
	
	private String receiverName;
	private String address;
	private String phone;
	
	private State state;
	private Timestamp lastUpdateTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	private User Buyer;
	private User Seller;
	private Goods goods;

	public User getBuyer() {
		return Buyer;
	}
	public void setBuyer(User buyer) {
		Buyer = buyer;
	}
	public User getSeller() {
		return Seller;
	}
	public void setSeller(User seller) {
		Seller = seller;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}
