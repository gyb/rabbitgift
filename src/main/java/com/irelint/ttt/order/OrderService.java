package com.irelint.ttt.order;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.goods.Goods;
import com.irelint.ttt.goods.GoodsDao;
import com.irelint.ttt.user.Account;
import com.irelint.ttt.user.AccountDao;
import com.irelint.ttt.user.User;
import com.irelint.ttt.user.UserDao;

@Service
public class OrderService {
	@Autowired OrderDao orderDao;
	@Autowired OrderHistoryDao orderHistoryDao;
	@Autowired GoodsDao goodsDao;
	@Autowired AccountDao accountDao;
	@Autowired UserDao userDao;
	@Autowired RatingDao ratingDao;

	@Transactional
	@OptimisticLockRetry
	@CacheEvict(value="goods", key="#order.goods.id")
	public Order create(Order order) {
		//validate order
		Goods goods = goodsDao.findOne(order.getGoods().getId());
		if (!goods.isOnline()) {
			throw new GoodsNotOnlineException();
		}
		if (goods.getAvailableNumber() < order.getNum()) {
			throw new GoodsNotEnoughException();
		}
		
		goods.setAvailableNumber(goods.getAvailableNumber() - order.getNum());
		goods.setSelledNumber(goods.getSelledNumber() + order.getNum());
		goodsDao.save(goods);
		
		order.setSeller(new User(goods.getUserId()));
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		order.setMoney(goods.getPrice() * order.getNum());
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getBuyer());
		history.setType(OrderHistory.Type.CREATE);
		orderHistoryDao.save(history);
		
		return order;
	}
	
	@Transactional(readOnly=true)
	public Page<Order> findBuyerOrders(final Long userId, Pageable pageable) {
		return orderDao.findByBuyerId(userId, pageable); 
		//"/myttt/orders/");
	}
	
	@Transactional(readOnly=true)
	public Order get(Long orderId) {
		return orderDao.findOne(orderId);
	}
	
	@Transactional
	@OptimisticLockRetry
	public Order pay(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.CREATED) return null;

		Account account = accountDao.findOne(order.getBuyer().getId());
		if (!account.freeze(order.getMoney())) return null;
		accountDao.save(account);
		
		order.setState(Order.State.PAYED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getBuyer());
		history.setType(OrderHistory.Type.PAY);
		orderHistoryDao.save(history);
		
		return order;
	}

	@Transactional(readOnly=true)
	public Page<Order> findSellerOrders(final Long userId, Pageable pageable) {
		return orderDao.findBySellerId(userId, pageable); 
		//"/myshop/orders/");
	}
	
	@Transactional
	@OptimisticLockRetry
	public Order cancel(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.CREATED) return null;

		updateGoodsForCancelOrder(order);
		
		order.setState(Order.State.CANCELED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getSeller());
		history.setType(OrderHistory.Type.CANCEL);
		orderHistoryDao.save(history);
		
		return order;
	}

	@CacheEvict(value="goods", key="#order.goods.id")
	protected void updateGoodsForCancelOrder(Order order) {
		Goods goods = goodsDao.findOne(order.getGoods().getId());
		goods.setAvailableNumber(goods.getAvailableNumber() + order.getNum());
		goods.setSelledNumber(goods.getSelledNumber() - order.getNum());
		goodsDao.save(goods);
	}

	@Transactional
	@OptimisticLockRetry
	public Order deliver(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.PAYED) return null;
		
		order.setState(Order.State.DELIVERED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getSeller());
		history.setType(OrderHistory.Type.DELIVER);
		orderHistoryDao.save(history);
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order refund(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.PAYED
				&& order.getState() != Order.State.DELIVERED) return null;

		Account account = accountDao.findOne(order.getBuyer().getId());
		account.refund(order.getMoney());
		accountDao.save(account);
		
		order.setState(Order.State.REFUNDED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getSeller());
		history.setType(OrderHistory.Type.REFUND);
		orderHistoryDao.save(history);
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order receive(Long orderId) {
		Order order = orderDao.findOne(orderId);
		if (order.getState() != Order.State.DELIVERED) return null;

		Account buyer = accountDao.findOne(order.getBuyer().getId());
		buyer.confirmPay(order.getMoney());
		accountDao.save(buyer);
		Account seller = accountDao.findOne(order.getSeller().getId());
		seller.receivePay(order.getMoney());
		accountDao.save(seller);
		
		order.setState(Order.State.RECEIVED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getBuyer());
		history.setType(OrderHistory.Type.RECEIVE);
		orderHistoryDao.save(history);
		
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
		
		order.setState(Order.State.COMPLETED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUser(order.getBuyer());
		history.setType(OrderHistory.Type.COMPLETE);
		orderHistoryDao.save(history);
		
		return order;
	}
	
	@Transactional(readOnly=true)
	public List<OrderHistory> history(Long orderId) {
		return orderHistoryDao.findByOrderId(orderId);
	}

	@Transactional(readOnly=true)
	public Order findDetail(Long orderId) {
		Order order = orderDao.findOne(orderId);
		order.setGoods(goodsDao.findOne(order.getGoods().getId()));
		order.setBuyer(userDao.findOne(order.getBuyer().getId()));
		order.setSeller(userDao.findOne(order.getSeller().getId()));
		return order;
	}
}
