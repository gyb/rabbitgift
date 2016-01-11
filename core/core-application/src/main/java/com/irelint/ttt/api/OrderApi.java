package com.irelint.ttt.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.goods.GoodsService;
import com.irelint.ttt.order.Order;
import com.irelint.ttt.order.OrderService;
import com.irelint.ttt.order.Rating;
import com.irelint.ttt.user.UserService;

public class OrderApi {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsService goodsService;

	public Order get(Long orderId) {
		return orderService.get(orderId);
	}

	public Order create(Order order) {
		return orderService.create(order);
	}

	public Page<OrderDto> findSellerOrders(Long userId, Pageable pageable) {
		Page<Order> page = orderService.findSellerOrders(userId, pageable);
		List<OrderDto> dtos = page.getContent().stream()
				.map(o -> {
					OrderDto dto = new OrderDto(o);
					dto.setBuyer(userService.get(o.getBuyerId()));
					dto.setGoods(goodsService.get(o.getGoodsId()));
					return dto;
				})
				.collect(Collectors.toList());
		return new PageImpl<OrderDto>(dtos, pageable, page.getTotalElements());
	}

	public Page<OrderDto> findBuyerOrders(Long userId, Pageable pageable) {
		Page<Order> page = orderService.findBuyerOrders(userId, pageable);
		List<OrderDto> dtos = page.getContent().stream()
				.map(o -> {
					OrderDto dto = new OrderDto(o);
					dto.setSeller(userService.get(o.getSellerId()));
					dto.setGoods(goodsService.get(o.getGoodsId()));
					return dto;
				})
				.collect(Collectors.toList());
		return new PageImpl<OrderDto>(dtos, pageable, page.getTotalElements());
	}
	
	public Page<Rating> findRatings(Long goodsId, Pageable pageable) {
		return orderService.findRatings(goodsId, pageable);
	}

	public void pay(Long orderId) {
		orderService.pay(orderId);
	}

	public Order cancel(Long orderId) {
		return orderService.cancel(orderId);
	}

	public Order deliver(Long orderId) {
		return orderService.deliver(orderId);
	}

	public Order refund(Long orderId) {
		return orderService.refund(orderId);
	}

	public Order receive(Long orderId) {
		return orderService.receive(orderId);
	}

	public Order rate(Rating rating) {
		return orderService.rate(rating);
	}

	public OrderDto findDetail(Long orderId) {
		Order order = orderService.get(orderId);
		OrderDto dto = new OrderDto(order);
		dto.setBuyer(userService.get(order.getBuyerId()));
		dto.setSeller(userService.get(order.getSellerId()));
		dto.setGoods(goodsService.get(order.getGoodsId()));
		return dto;
	}

	public List<OrderHistoryDto> history(Long orderId) {
		return orderService.history(orderId).stream()
				.map(oh -> new OrderHistoryDto(oh).setUser(userService.get(oh.getUserId())))
				.collect(Collectors.toList());
	}

}
