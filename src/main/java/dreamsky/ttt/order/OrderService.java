package dreamsky.ttt.order;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamsky.ttt.aop.OptimisticLockRetry;
import dreamsky.ttt.dao.Dao;
import dreamsky.ttt.goods.Goods;
import dreamsky.ttt.goods.GoodsDao;
import dreamsky.ttt.user.Account;
import dreamsky.ttt.user.AccountDao;
import dreamsky.ttt.user.UserDao;
import dreamsky.ttt.util.PageDataProvider;
import dreamsky.ttt.util.PageList;

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
	@CacheEvict(value="goods", key="#order.goodsId")
	public Order create(Order order) {
		//validate order
		Goods goods = goodsDao.get(order.getGoodsId());
		if (!goods.isOnline()) {
			throw new GoodsNotOnlineException();
		}
		if (goods.getAvailableNumber() < order.getNum()) {
			throw new GoodsNotEnoughException();
		}
		
		goods.setAvailableNumber(goods.getAvailableNumber() - order.getNum());
		goods.setSelledNumber(goods.getSelledNumber() + order.getNum());
		goodsDao.save(goods);
		
		order.setSellerId(goods.getUserId());
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		order.setMoney(goods.getPrice() * order.getNum());
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.CREATE);
		orderHistoryDao.save(history);
		
		return order;
	}
	
	@Transactional(readOnly=true)
	public PageList<Order> findBuyerOrders(final Long userId, int pageNo, int pageSize) {
		return new PageList<Order>(new PageDataProvider<Order>() {
			@Override
			public List<Order> find(int start, int size) {
				List<Order> orderList = orderDao.findPageBy("buyerId", userId, start, size);
				return orderDao.join(orderList, 
						new String[] {"goodsId", "sellerId"}, 
						new String[] {"goods", "seller"},
						new Dao[] { goodsDao, userDao });
			}

			@Override
			public int total() {
				return orderDao.count("buyerId", userId);
			}
		}, pageNo, pageSize, "/myttt/orders/");
	}
	
	@Transactional(readOnly=true)
	public Order get(Long orderId) {
		return orderDao.get(orderId);
	}
	
	@Transactional
	@OptimisticLockRetry
	public Order pay(Long orderId) {
		Order order = orderDao.get(orderId);
		if (order.getState() != Order.State.CREATED) return null;

		Account account = accountDao.get(order.getBuyerId());
		if (!account.freeze(order.getMoney())) return null;
		accountDao.update(account);
		
		order.setState(Order.State.PAYED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.PAY);
		orderHistoryDao.save(history);
		
		return order;
	}

	@Transactional(readOnly=true)
	public Object findSellerOrders(final Long userId, int pageNo, int pageSize) {
		return new PageList<Order>(new PageDataProvider<Order>() {
			@Override
			public List<Order> find(int start, int size) {
				List<Order> orderList = orderDao.findPageBy("sellerId", userId, start, size);
				return orderDao.join(orderList, 
						new String[] {"goodsId", "buyerId"}, 
						new String[] {"goods", "buyer"},
						new Dao[] { goodsDao, userDao });
			}

			@Override
			public int total() {
				return orderDao.count("sellerId", userId);
			}
		}, pageNo, pageSize, "/myshop/orders/");
	}
	
	@Transactional
	@OptimisticLockRetry
	public Order cancel(Long orderId) {
		Order order = orderDao.get(orderId);
		if (order.getState() != Order.State.CREATED) return null;

		updateGoodsForCancelOrder(order);
		
		order.setState(Order.State.CANCELED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(OrderHistory.Type.CANCEL);
		orderHistoryDao.save(history);
		
		return order;
	}

	//FIXME Cache annotation doesn't work on private methods
	@CacheEvict(value="goods", key="#order.goodsId")
	private void updateGoodsForCancelOrder(Order order) {
		Goods goods = goodsDao.get(order.getGoodsId());
		goods.setAvailableNumber(goods.getAvailableNumber() + order.getNum());
		goods.setSelledNumber(goods.getSelledNumber() - order.getNum());
		goodsDao.save(goods);
	}

	@Transactional
	@OptimisticLockRetry
	public Order deliver(Long orderId) {
		Order order = orderDao.get(orderId);
		if (order.getState() != Order.State.PAYED) return null;
		
		order.setState(Order.State.DELIVERED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(OrderHistory.Type.DELIVER);
		orderHistoryDao.save(history);
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order refund(Long orderId) {
		Order order = orderDao.get(orderId);
		if (order.getState() != Order.State.PAYED
				&& order.getState() != Order.State.DELIVERED) return null;

		Account account = accountDao.get(order.getBuyerId());
		account.refund(order.getMoney());
		accountDao.update(account);
		
		order.setState(Order.State.REFUNDED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getSellerId());
		history.setType(OrderHistory.Type.REFUND);
		orderHistoryDao.save(history);
		
		return order;
	}

	@Transactional
	@OptimisticLockRetry
	public Order receive(Long orderId) {
		Order order = orderDao.get(orderId);
		if (order.getState() != Order.State.DELIVERED) return null;

		Account buyer = accountDao.get(order.getBuyerId());
		buyer.confirmPay(order.getMoney());
		accountDao.update(buyer);
		Account seller = accountDao.get(order.getSellerId());
		seller.receivePay(order.getMoney());
		accountDao.update(seller);
		
		order.setState(Order.State.RECEIVED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.RECEIVE);
		orderHistoryDao.save(history);
		
		return order;
	}
	
	@Transactional
	@OptimisticLockRetry
	@CacheEvict(value="goods", key="#rating.goodsId")
	public Order rate(Rating rating) {
		Order order = orderDao.get(rating.getOrderId());
		if (order.getState() != Order.State.RECEIVED) return null;
		
		rating.setRatingTime(new Timestamp(System.currentTimeMillis()));
		rating.setBuyPrice(order.getMoney() / order.getNum());
		OrderHistory created = orderHistoryDao.findByUnique(new String[] { "orderId", "type" }, 
				new Object[] { order.getId(), OrderHistory.Type.CREATE.toString() });
		rating.setBuyTime(created.getTime());
		ratingDao.save(rating);
		
		Goods goods = goodsDao.get(rating.getGoodsId());
		goods.addRating(rating.getNumber());
		goodsDao.save(goods);
		
		order.setState(Order.State.COMPLETED);
		order.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orderDao.save(order);
		
		OrderHistory history = new OrderHistory();
		history.setOrderId(order.getId());
		history.setTime(order.getLastUpdateTime());
		history.setUserId(order.getBuyerId());
		history.setType(OrderHistory.Type.COMPLETE);
		orderHistoryDao.save(history);
		
		return order;
	}
	
	@Transactional(readOnly=true)
	public List<OrderHistory> history(Long orderId) {
		List<OrderHistory> list = orderHistoryDao.findBy("orderId", orderId);
		return orderHistoryDao.join(list, "userId", "user", userDao);
	}

	@Transactional(readOnly=true)
	public Order findDetail(Long orderId) {
		Order order = orderDao.get(orderId);
		order.setGoods(goodsDao.get(order.getGoodsId()));
		order.setBuyer(userDao.get(order.getBuyerId()));
		order.setSeller(userDao.get(order.getSellerId()));
		return order;
	}
}
