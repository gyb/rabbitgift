package com.irelint.ttt.order;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
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

@Service
public class OrderService implements ApplicationEventPublisherAware {
	@Autowired 
	private OrderDao orderDao;
	@Autowired 
	private OrderHistoryDao orderHistoryDao;
	@Autowired 
	private GoodsDao goodsDao;
	@Autowired
	private RatingDao ratingDao;
	
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
	public Page<Order> findBuyerOrders(final Long userId, Pageable pageable) {
		return orderDao.findByBuyerId(userId, pageable); 
	}
	
	@Transactional(readOnly=true)
	public Order get(Long orderId) {
		return orderDao.findOne(orderId);
	}
	
	@Transactional(readOnly=true)
	public void pay(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.CONFIRMED) {
			return;
		}
		
		publisher.publishEvent(new ToPayOrderEvent(this, orderId, order.getBuyerId(), order.getMoney()));
	}
	
	@Transactional
	@EventListener
	public Order pay(OrderPayedEvent event) {
		Order order = orderDao.findOne(event.getOrderId());
		order.pay();
		
		orderHistoryDao.save(OrderHistory.newPay(order));
		
		return order;
	}

	@Transactional(readOnly=true)
	public Page<Order> findSellerOrders(final Long userId, Pageable pageable) {
		return orderDao.findBySellerId(userId, pageable); 
	}
	
	@Transactional
	public Order cancel(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.CREATED) return null;

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
	public List<OrderHistory> history(Long orderId) {
		return orderHistoryDao.findByOrderId(orderId);
	}

	@Transactional(readOnly=true)
	public Order findDetail(Long orderId) {
		return orderDao.findOne(orderId);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
