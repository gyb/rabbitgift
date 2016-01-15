package com.irelint.ttt.service;

import java.util.List;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.dto.PageDto;
import com.irelint.ttt.dto.RatingDto;
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.GoodsUpdatedEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderPayedEvent;

public interface OrderService {

	OrderDto create(OrderDto order);

	void confirmed(OrderConfirmedEvent event);

	PageDto<OrderDto> findBuyerOrders(Long userId, int page, int size);

	OrderDto get(Long orderId);

	void pay(Long orderId);

	void pay(OrderPayedEvent event);

	PageDto<OrderDto> findSellerOrders(Long userId, int page, int size);

	OrderDto cancel(Long orderId);

	OrderDto deliver(Long orderId);

	OrderDto refund(Long orderId);

	OrderDto receive(Long orderId);

	OrderDto rate(RatingDto rating);

	List<OrderHistoryDto> history(Long orderId);

	PageDto<RatingDto> findRatings(Long goodsId, int page, int size);

	void createGoods(GoodsCreatedEvent event);
	
	void updateGoods(GoodsUpdatedEvent event);
}