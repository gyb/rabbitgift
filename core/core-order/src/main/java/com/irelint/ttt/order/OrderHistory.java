package com.irelint.ttt.order;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long orderId;
	private Type type;
	private Timestamp time;
	
	private Long userId;
	
	public enum Type {
		CREATE, CONFIRM, PAY, DELIVER, RECEIVE, COMPLETE, CANCEL, REFUND
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static OrderHistory newCreate(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.CREATE);
		return history;
	}

	public static OrderHistory newConfirm(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(0L);
		history.setType(OrderHistory.Type.CONFIRM);
		return history;
	}

	public static OrderHistory newPay(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.PAY);
		return history;
	}

	public static OrderHistory newCancel(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(OrderHistory.Type.CANCEL);
		return history;
	}

	public static OrderHistory newDeliver(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(OrderHistory.Type.DELIVER);
		return history;
	}

	public static OrderHistory newRefund(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(OrderHistory.Type.REFUND);
		return history;
	}

	public static OrderHistory newReceive(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.RECEIVE);
		return history;
	}

	public static OrderHistory newComplete(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.COMPLETE);
		return history;
	}
}
