package com.irelint.ttt.order;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.event.OrderCanceledEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderCreatedEvent;
import com.irelint.ttt.event.OrderPayedEvent;
import com.irelint.ttt.event.OrderReceivedEvent;
import com.irelint.ttt.event.ToPayOrderEvent;
import com.irelint.ttt.event.ToRefundOrderEvent;
import com.irelint.ttt.goods.GoodsDao;
import com.irelint.ttt.goods.GoodsNotOnlineException;
import com.irelint.ttt.goods.model.Goods;
import com.irelint.ttt.user.dao.UserDao;

@Service
public class OrderService implements ApplicationEventPublisherAware {
	private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired 
	private OrderDao orderDao;
	@Autowired 
	private OrderHistoryDao orderHistoryDao;
	@Autowired 
	private GoodsDao goodsDao;
	@Autowired
	private RatingDao ratingDao;
	@Autowired
	private UserDao userDao;
	
	private ApplicationEventPublisher publisher;

	@Transactional
	public Order create(Order order) {
		//validate order
		Goods goods = goodsDao.findOne(order.getGoodsId());
		if (!goods.isOnline()) {
			throw new GoodsNotOnlineException();
		}

		order.create(goods.getOwnerId(), goods.getPrice());
		orderDao.save(order);
		orderHistoryDao.save(OrderHistory.newCreate(order));
		
		publisher.publishEvent(new OrderCreatedEvent(this, order.getId(), order.getGoodsId(), order.getNum()));
		
		return order;
	}
	
	@Transactional
	@EventListener
	public void confirmed(OrderConfirmedEvent event) {
		Order order = orderDao.findOne(event.getOrderId());
		order.confirm();
		
		orderHistoryDao.save(OrderHistory.newConfirm(order));
	}
	
	@Transactional(readOnly=true)
	public Page<OrderDto> findBuyerOrders(final Long userId, Pageable pageable) {
		Page<Order> page = orderDao.findByBuyerId(userId, pageable);
		List<OrderDto> dtos = page.getContent().stream()
				.map(o -> {
					OrderDto dto = new OrderDto(o);
					dto.setSeller(userDao.findOne(o.getSellerId()));
					dto.setGoods(goodsDao.findOne(o.getGoodsId()));
					return dto;
				})
				.collect(Collectors.toList());
		return new PageImpl<OrderDto>(dtos, pageable, page.getTotalElements());
	}
	
	@Transactional(readOnly=true)
	public Order get(Long orderId) {
		return orderDao.findOne(orderId);
	}
	
	@Transactional
	public void pay(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.CONFIRMED) {
			return;
		}
		
		publisher.publishEvent(new ToPayOrderEvent(this, orderId, order.getBuyerId(), order.getMoney()));
		logger.debug("publish ToPayOrderEvent [{}, {}, {}]", orderId, order.getBuyerId(), order.getMoney());
	}
	
	@Transactional
	@EventListener
	public void pay(OrderPayedEvent event) {
		Order order = orderDao.findOne(event.getOrderId());
		order.pay();
		orderHistoryDao.save(OrderHistory.newPay(order));
		
		logger.debug("order {} payed successfully", order.getId());
	}

	@Transactional(readOnly=true)
	public Page<OrderDto> findSellerOrders(final Long userId, Pageable pageable) {
		Page<Order> page = orderDao.findBySellerId(userId, pageable);
		List<OrderDto> dtos = page.getContent().stream()
				.map(o -> {
					OrderDto dto = new OrderDto(o);
					dto.setBuyer(userDao.findOne(o.getBuyerId()));
					dto.setGoods(goodsDao.findOne(o.getGoodsId()));
					return dto;
				})
				.collect(Collectors.toList());
		return new PageImpl<OrderDto>(dtos, pageable, page.getTotalElements());
	}
	
	@Transactional
	public Order cancel(Long orderId) {
		Order order = orderDao.findOne(orderId);
		logger.debug("cancel order {}, status is {}", order.getId(), order.getState());
		if (order.getState() != Order.State.CREATED && order.getState() != Order.State.CONFIRMED) return null;

		order.cancel();
		orderHistoryDao.save(OrderHistory.newCancel(order));
		publisher.publishEvent(new OrderCanceledEvent(this, order.getId(), order.getGoodsId(), order.getNum()));
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order deliver(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.PAYED) return null;
		
		order.deliver();
		
		orderHistoryDao.save(OrderHistory.newDeliver(order));
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order refund(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.PAYED && order.getState() != Order.State.DELIVERED) return null;
		
		order.refund();
		orderHistoryDao.save(OrderHistory.newRefund(order));
		
		publisher.publishEvent(new ToRefundOrderEvent(this, order.getId(), order.getBuyerId(), order.getMoney()));
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order receive(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.DELIVERED) return null;
		
		order.receive();
		orderHistoryDao.save(OrderHistory.newReceive(order));

		publisher.publishEvent(new OrderReceivedEvent(this, order.getId(), order.getBuyerId(), order.getSellerId(), order.getMoney()));
		
		return order;
	}
	
	@Transactional
	@OptimisticLockRetry
	@CacheEvict(value="goods", key="#rating.goodsId")
	public Order rate(Rating rating) {
		Order order = orderDao.findOne(rating.getOrderId());
		if (order.getState() != Order.State.RECEIVED) return null;
		
		rating.setRatingTime(new Timestamp(System.currentTimeMillis()));
		rating.setBuyPrice(order.getMoney() / order.getNum());
		OrderHistory created = orderHistoryDao.findByOrderIdAndType(order.getId(), OrderHistory.Type.CREATE);
		rating.setBuyTime(created.getTime());
		ratingDao.save(rating);
		
		Goods goods = goodsDao.findOne(rating.getGoodsId());
		goods.addRating(rating.getNumber());
		goodsDao.save(goods);
		
		order.complete();
		orderHistoryDao.save(OrderHistory.newComplete(order));
		
		return order;
	}
	
	@Transactional(readOnly=true)
	public List<OrderHistoryDto> history(Long orderId) {
		return orderHistoryDao.findByOrderId(orderId).stream()
				.map(oh -> new OrderHistoryDto(oh).setUser(userDao.findOne(oh.getUserId())))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly=true)
	public OrderDto findDetail(Long orderId) {
		Order order = orderDao.findOne(orderId);
		OrderDto dto = new OrderDto(order);
		dto.setBuyer(userDao.findOne(order.getBuyerId()));
		dto.setSeller(userDao.findOne(order.getSellerId()));
		dto.setGoods(goodsDao.findOne(order.getGoodsId()));
		return dto;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
