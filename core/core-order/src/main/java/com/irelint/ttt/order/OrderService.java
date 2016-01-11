package com.irelint.ttt.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.GoodsUpdatedEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderPayedEvent;

public interface OrderService {

	Order create(Order order);

	void confirmed(OrderConfirmedEvent event);

	Page<Order> findBuyerOrders(Long userId, Pageable pageable);

	Order get(Long orderId);

	void pay(Long orderId);

	void pay(OrderPayedEvent event);

	Page<Order> findSellerOrders(Long userId, Pageable pageable);

	Order cancel(Long orderId);

	Order deliver(Long orderId);

	Order refund(Long orderId);

	Order receive(Long orderId);

	Order rate(Rating rating);

	List<OrderHistory> history(Long orderId);

	Page<Rating> findRatings(Long goodsId, Pageable pageable);

	void createGoods(GoodsCreatedEvent event);
	
	void updateGoods(GoodsUpdatedEvent event);
}