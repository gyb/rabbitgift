package com.irelint.ttt.order;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.irelint.ttt.dto.HistoryType;
import com.irelint.ttt.dto.OrderHistoryDto;

@Entity
public class OrderHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long orderId;
	private HistoryType type;
	private Timestamp time;
	
	private Long userId;
	
	public OrderHistoryDto toDto() {
		OrderHistoryDto dto = new OrderHistoryDto();
		dto.setType(this.type);
		dto.setTime(this.time);
		dto.setUserId(this.userId);
		return dto;
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

	public HistoryType getType() {
		return type;
	}

	public void setType(HistoryType type) {
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
		history.setType(HistoryType.CREATE);
		return history;
	}

	public static OrderHistory newConfirm(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(0L);
		history.setType(HistoryType.CONFIRM);
		return history;
	}

	public static OrderHistory newPay(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(HistoryType.PAY);
		return history;
	}

	public static OrderHistory newCancel(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(HistoryType.CANCEL);
		return history;
	}

	public static OrderHistory newDeliver(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(HistoryType.DELIVER);
		return history;
	}

	public static OrderHistory newRefund(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(HistoryType.REFUND);
		return history;
	}

	public static OrderHistory newReceive(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(HistoryType.RECEIVE);
		return history;
	}

	public static OrderHistory newComplete(Order order) {
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(HistoryType.COMPLETE);
		return history;
	}
	
}
