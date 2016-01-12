package com.irelint.ttt.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.dto.RatingDto;
import com.irelint.ttt.service.GoodsService;
import com.irelint.ttt.service.OrderService;
import com.irelint.ttt.service.UserService;

@Service
@Transactional
public class OrderApi {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsService goodsService;

	@Transactional(readOnly=true)
	public OrderDto get(Long orderId) {
		return orderService.get(orderId);
	}

	public OrderDto create(OrderDto order) {
		return orderService.create(order);
	}

	@Transactional(readOnly=true)
	public Page<OrderDto> findSellerOrders(Long userId, Pageable pageable) {
		Page<OrderDto> page = orderService.findSellerOrders(userId, pageable);
		List<OrderDto> dtos = page.getContent().stream()
				.map(dto -> {
					dto.setBuyer(userService.get(dto.getBuyerId()));
					dto.setGoods(goodsService.get(dto.getGoodsId()));
					return dto;
				})
				.collect(Collectors.toList());
		return new PageImpl<OrderDto>(dtos, pageable, page.getTotalElements());
	}

	@Transactional(readOnly=true)
	public Page<OrderDto> findBuyerOrders(Long userId, Pageable pageable) {
		Page<OrderDto> page = orderService.findBuyerOrders(userId, pageable);
		List<OrderDto> dtos = page.getContent().stream()
				.map(dto -> {
					dto.setSeller(userService.get(dto.getSellerId()));
					dto.setGoods(goodsService.get(dto.getGoodsId()));
					return dto;
				})
				.collect(Collectors.toList());
		return new PageImpl<OrderDto>(dtos, pageable, page.getTotalElements());
	}
	
	@Transactional(readOnly=true)
	public Page<RatingDto> findRatings(Long goodsId, Pageable pageable) {
		return orderService.findRatings(goodsId, pageable);
	}

	public void pay(Long orderId) {
		orderService.pay(orderId);
	}

	public OrderDto cancel(Long orderId) {
		return orderService.cancel(orderId);
	}

	public OrderDto deliver(Long orderId) {
		return orderService.deliver(orderId);
	}

	public OrderDto refund(Long orderId) {
		return orderService.refund(orderId);
	}

	public OrderDto receive(Long orderId) {
		return orderService.receive(orderId);
	}

	public OrderDto rate(RatingDto rating) {
		return orderService.rate(rating);
	}

	@Transactional(readOnly=true)
	public OrderDto findDetail(Long orderId) {
		OrderDto order = orderService.get(orderId);
		order.setBuyer(userService.get(order.getBuyerId()));
		order.setSeller(userService.get(order.getSellerId()));
		order.setGoods(goodsService.get(order.getGoodsId()));
		return order;
	}

	@Transactional(readOnly=true)
	public List<OrderHistoryDto> history(Long orderId) {
		return orderService.history(orderId).stream()
				.map(oh -> oh.setUser(userService.get(oh.getUserId())))
				.collect(Collectors.toList());
	}

}
