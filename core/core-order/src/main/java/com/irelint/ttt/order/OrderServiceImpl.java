package com.irelint.ttt.order;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.GoodsRatedEvent;
import com.irelint.ttt.event.GoodsUpdatedEvent;
import com.irelint.ttt.event.OrderCanceledEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderCreatedEvent;
import com.irelint.ttt.event.OrderPayedEvent;
import com.irelint.ttt.event.OrderReceivedEvent;
import com.irelint.ttt.event.ToPayOrderEvent;
import com.irelint.ttt.event.ToRefundOrderEvent;

@Service
public class OrderServiceImpl implements OrderService, ApplicationEventPublisherAware {
	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired 
	private OrderDao orderDao;
	@Autowired
	private OrderGoodsDao orderGoodsDao;
	@Autowired 
	private OrderHistoryDao orderHistoryDao;
	@Autowired
	private RatingDao ratingDao;
	
	private ApplicationEventPublisher publisher;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#create(com.irelint.ttt.order.Order)
	 */
	@Override
	@Transactional
	public Order create(Order order) {
		//validate order
		OrderGoods goods = orderGoodsDao.findOne(order.getGoodsId());
		if (!goods.isOnline()) {
			throw new GoodsNotOnlineException();
		}

		order.create(goods.getOwnerId(), goods.getPrice());
		orderDao.save(order);
		orderHistoryDao.save(OrderHistory.newCreate(order));
		
		publisher.publishEvent(new OrderCreatedEvent(this, order.getId(), order.getGoodsId(), order.getNum()));
		
		return order;
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#confirmed(com.irelint.ttt.event.OrderConfirmedEvent)
	 */
	@Override
	@Transactional
	@EventListener
	public void confirmed(OrderConfirmedEvent event) {
		Order order = orderDao.findOne(event.getOrderId());
		order.confirm();
		
		orderHistoryDao.save(OrderHistory.newConfirm(order));
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#findBuyerOrders(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Order> findBuyerOrders(final Long userId, Pageable pageable) {
		return orderDao.findByBuyerId(userId, pageable);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Order get(Long orderId) {
		return orderDao.findOne(orderId);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#pay(java.lang.Long)
	 */
	@Override
	@Transactional
	public void pay(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.CONFIRMED) {
			return;
		}
		
		publisher.publishEvent(new ToPayOrderEvent(this, orderId, order.getBuyerId(), order.getMoney()));
		logger.debug("publish ToPayOrderEvent [{}, {}, {}]", orderId, order.getBuyerId(), order.getMoney());
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#pay(com.irelint.ttt.event.OrderPayedEvent)
	 */
	@Override
	@Transactional
	@EventListener
	public void pay(OrderPayedEvent event) {
		Order order = orderDao.findOne(event.getOrderId());
		order.pay();
		orderHistoryDao.save(OrderHistory.newPay(order));
		
		logger.debug("order {} payed successfully", order.getId());
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#findSellerOrders(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Order> findSellerOrders(final Long userId, Pageable pageable) {
		return orderDao.findBySellerId(userId, pageable);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#cancel(java.lang.Long)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#deliver(java.lang.Long)
	 */
	@Override
	@Transactional
	@OptimisticLockRetry
	public Order deliver(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.PAYED) return null;
		
		order.deliver();
		
		orderHistoryDao.save(OrderHistory.newDeliver(order));
		
		return order;
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#refund(java.lang.Long)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#receive(java.lang.Long)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#rate(com.irelint.ttt.order.Rating)
	 */
	@Override
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
		
		publisher.publishEvent(new GoodsRatedEvent(this, order.getGoodsId(), rating.getNumber()));
		
		order.complete();
		orderHistoryDao.save(OrderHistory.newComplete(order));
		
		return order;
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#history(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public List<OrderHistory> history(Long orderId) {
		return orderHistoryDao.findByOrderId(orderId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.order.OrderService#findRatings(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Rating> findRatings(final Long goodsId, Pageable pageable) {
		return ratingDao.findByGoodsId(goodsId, pageable);
	}
	
	@Override
	@Transactional
	public void createGoods(GoodsCreatedEvent event) {
		OrderGoods goods = new OrderGoods();
		goods.setId(event.getGoodsId());
		goods.setOwnerId(event.getOwnerId());
		orderGoodsDao.save(goods);
	}

	@Override
	@Transactional
	public void updateGoods(GoodsUpdatedEvent event) {
		OrderGoods goods = orderGoodsDao.findOne(event.getGoodsId());
		goods.setPrice(event.getPrice());
		goods.setState(event.getState());
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}