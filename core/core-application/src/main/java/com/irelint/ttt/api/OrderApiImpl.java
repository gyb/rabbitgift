package com.irelint.ttt.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.dto.OrderDto;
import com.irelint.ttt.dto.OrderHistoryDto;
import com.irelint.ttt.dto.RatingDto;
import com.irelint.ttt.service.GoodsService;
import com.irelint.ttt.service.OrderService;
import com.irelint.ttt.service.UserService;

@Service
@DubboService
public class OrderApiImpl implements OrderApi {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsService goodsService;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#get(java.lang.Long)
	 */
	@Override
	public OrderDto get(Long orderId) {
		return orderService.get(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#create(com.irelint.ttt.dto.OrderDto)
	 */
	@Override
	public OrderDto create(OrderDto order) {
		return orderService.create(order);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#findSellerOrders(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#findBuyerOrders(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#findRatings(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<RatingDto> findRatings(Long goodsId, Pageable pageable) {
		return orderService.findRatings(goodsId, pageable);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#pay(java.lang.Long)
	 */
	@Override
	public void pay(Long orderId) {
		orderService.pay(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#cancel(java.lang.Long)
	 */
	@Override
	public OrderDto cancel(Long orderId) {
		return orderService.cancel(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#deliver(java.lang.Long)
	 */
	@Override
	public OrderDto deliver(Long orderId) {
		return orderService.deliver(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#refund(java.lang.Long)
	 */
	@Override
	public OrderDto refund(Long orderId) {
		return orderService.refund(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#receive(java.lang.Long)
	 */
	@Override
	public OrderDto receive(Long orderId) {
		return orderService.receive(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#rate(com.irelint.ttt.dto.RatingDto)
	 */
	@Override
	public OrderDto rate(RatingDto rating) {
		return orderService.rate(rating);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#findDetail(java.lang.Long)
	 */
	@Override
	public OrderDto findDetail(Long orderId) {
		OrderDto order = orderService.get(orderId);
		order.setBuyer(userService.get(order.getBuyerId()));
		order.setSeller(userService.get(order.getSellerId()));
		order.setGoods(goodsService.get(order.getGoodsId()));
		return order;
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.OrderApi#history(java.lang.Long)
	 */
	@Override
	public List<OrderHistoryDto> history(Long orderId) {
		return orderService.history(orderId).stream()
				.map(oh -> oh.setUser(userService.get(oh.getUserId())))
				.collect(Collectors.toList());
	}

}
