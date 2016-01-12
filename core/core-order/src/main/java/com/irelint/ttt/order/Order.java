package com.irelint.ttt.order;    

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderState;

@Entity
@Table(name="orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	private Long buyerId;
	
	private Long sellerId;

	private long money;
	
	@NotNull
	private Long goodsId;

	@Min(1)
	private int num;
	
	@NotNull
	private String receiverName;
	@NotNull
	private String address;
	@NotNull
	private String phone;
	
	private OrderState state = OrderState.CREATED;
	private Timestamp lastUpdateTime;
	@Version 
	private int version;
	
	public OrderDto toDto() {
		OrderDto dto = new OrderDto();
		dto.setId(this.id);
		dto.setMoney(this.money);
		dto.setNum(this.num);
		dto.setReceiverName(this.receiverName);
		dto.setAddress(this.address);
		dto.setPhone(this.phone);
		dto.setState(this.state);
		dto.setLastUpdateTime(this.lastUpdateTime);
		dto.setBuyerId(this.buyerId);
		dto.setSellerId(this.sellerId);
		dto.setGoodsId(this.goodsId);
		return dto;
	}
	
	public static Order fromDto(OrderDto dto) {
		Order order = new Order();
		order.address = dto.getAddress();
		order.buyerId = dto.getBuyerId();
		order.goodsId = dto.getGoodsId();
		order.lastUpdateTime = dto.getLastUpdateTime();
		order.money = dto.getMoney();
		order.num = dto.getNum();
		order.phone = dto.getPhone();
		order.receiverName = dto.getReceiverName();
		order.sellerId = dto.getSellerId();
		order.state = dto.getState();
		return order;
	}
	
	public void create(Long goodsOwnerId, long goodsPrice) {
		this.setSellerId(goodsOwnerId);
		this.setMoney(goodsPrice * this.num);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	public void confirm() {
		this.setState(OrderState.CONFIRMED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}
	
	public void pay() {
		this.setState(OrderState.PAYED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}
	
	public void cancel() {
		this.setState(OrderState.CANCELED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	public void deliver() {
		this.setState(OrderState.DELIVERED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	public void refund() {
		this.setState(OrderState.REFUNDED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	public void receive() {
		this.setState(OrderState.RECEIVED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	public void complete() {
		this.setState(OrderState.COMPLETED);
		this.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
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
}
