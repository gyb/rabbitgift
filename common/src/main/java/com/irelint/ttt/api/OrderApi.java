package com.irelint.ttt.api;

import java.util.List;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.dto.PageDto;
import com.irelint.ttt.dto.RatingDto;

public interface OrderApi {

	OrderDto get(Long orderId);

	OrderDto create(OrderDto order);

	PageDto<OrderDto> findSellerOrders(Long userId, int page, int size);

	PageDto<OrderDto> findBuyerOrders(Long userId, int page, int size);

	PageDto<RatingDto> findRatings(Long goodsId, int page, int size);

	void pay(Long orderId);

	OrderDto cancel(Long orderId);

	OrderDto deliver(Long orderId);

	OrderDto refund(Long orderId);

	OrderDto receive(Long orderId);

	OrderDto rate(RatingDto rating);

	OrderDto findDetail(Long orderId);

	List<OrderHistoryDto> history(Long orderId);

}