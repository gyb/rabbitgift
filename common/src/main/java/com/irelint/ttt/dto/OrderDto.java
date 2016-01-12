package com.irelint.ttt.dto;

import java.sql.Timestamp;

public class OrderDto {

	private Long id;
	private long money;
	
	private int num;
	
	private String receiverName;
	private String address;
	private String phone;
	
	private OrderState state;
	private Timestamp lastUpdateTime;

	private Long buyerId;
	private Long sellerId;
	private Long goodsId;

	private UserDto Buyer;
	private UserDto Seller;
	private GoodsDto goods;

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
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public UserDto getBuyer() {
		return Buyer;
	}
	public void setBuyer(UserDto buyer) {
		Buyer = buyer;
	}
	public UserDto getSeller() {
		return Seller;
	}
	public void setSeller(UserDto seller) {
		Seller = seller;
	}
	public GoodsDto getGoods() {
		return goods;
	}
	public void setGoods(GoodsDto goods) {
		this.goods = goods;
	}
}
