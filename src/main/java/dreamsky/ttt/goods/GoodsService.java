package dreamsky.ttt.goods;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamsky.ttt.aop.OptimisticLockRetry;
import dreamsky.ttt.order.Rating;
import dreamsky.ttt.order.RatingDao;
import dreamsky.ttt.user.UserDao;
import dreamsky.ttt.util.PageDataProvider;
import dreamsky.ttt.util.PageList;

@Service
public class GoodsService {
	@Autowired GoodsDao dao;
	@Autowired RatingDao ratingDao;
	@Autowired UserDao userDao;
	
	@Transactional
	public void create(Goods goods) {
		dao.save(goods);
	}

	@Transactional(readOnly=true)
	public PageList<Goods> findCreatedPage(final Long userId, int pageNo, int pageSize) {
		return new PageList<Goods>(new PageDataProvider<Goods>() {
			@Override
			public int total() {
				return dao.count(new String[] { "userId", "state" }, new Object[] { userId, Goods.State.CREATED.toString() });
			}

			@Override
			public List<Goods> find(int start, int size) {
				return dao.findPageBy(new String[] { "userId", "state" }, 
						new Object[] { userId, Goods.State.CREATED.toString() }, start, size);
			}
		}, pageNo, pageSize, "/myshop/createdPage/");
	}
	
	@Transactional(readOnly=true)
	@Cacheable("goods")
	public Goods get(Long goodsId) {
		return dao.get(goodsId);
	}

	@Transactional
	@OptimisticLockRetry
	@CacheEvict("goods")
	public GoodsResult putOnline(Long goodsId) {
		Goods goods = dao.get(goodsId);
		if (!goods.isNew()) return GoodsResult.fail(goods);
		
		goods.setState(Goods.State.ONLINE);
		goods.setOnlineTime(new Timestamp(System.currentTimeMillis()));
		dao.merge(goods);
		return GoodsResult.success(goods);
	}

	@Transactional(readOnly=true)
	public PageList<Goods> findOnlinePage(final Long userId, int pageNo, int pageSize) {
		return new PageList<Goods>(new PageDataProvider<Goods>() {
			@Override
			public int total() {
				return dao.count(new String[] { "userId", "state" }, new Object[] { userId, Goods.State.ONLINE.toString() });
			}

			@Override
			public List<Goods> find(int start, int size) {
				return dao.findPageBy(new String[] { "userId", "state" }, 
						new Object[] { userId, Goods.State.ONLINE.toString() }, start, size);
			}
		}, pageNo, pageSize, "/myshop/onlinePage/");
	}
	
	@Transactional
	@CacheEvict("goods")
	public GoodsResult putOffline(Long goodsId) {
		Goods goods = dao.get(goodsId);
		if (!goods.isOnline()) return GoodsResult.fail(goods);
		
		goods.setState(Goods.State.OFFLINE);
		goods.setOfflineTime(new Timestamp(System.currentTimeMillis()));
		dao.merge(goods);
		return GoodsResult.success(goods);
	}

	@Transactional(readOnly=true)
	public PageList<Goods> findOfflinePage(final Long userId, int pageNo, int pageSize) {
		return new PageList<Goods>(new PageDataProvider<Goods>() {
			@Override
			public int total() {
				return dao.count(new String[] { "userId", "state" }, new Object[] { userId, Goods.State.OFFLINE.toString() });
			}

			@Override
			public List<Goods> find(int start, int size) {
				return dao.findPageBy(new String[] { "userId", "state" }, 
						new Object[] { userId, Goods.State.OFFLINE.toString() }, start, size);
			}
		}, pageNo, pageSize, "/myshop/offlinePage/");
	}

	@Transactional
	public GoodsResult copy(Long goodsId) {
		Goods goods = dao.get(goodsId);
		if (!goods.isOffline()) return GoodsResult.fail(goods);
		
		Goods copy = new Goods();
		copy.setName(goods.getName());
		copy.setDescription(goods.getDescription());
		copy.setCategoryId(goods.getCategoryId());
		copy.setPrice(goods.getPrice());
		copy.setAvailableNumber(goods.getAvailableNumber() + goods.getSelledNumber());
		copy.setPicUrl(goods.getPicUrl());
		copy.setUserId(goods.getUserId());
		copy.setState(Goods.State.CREATED);
		dao.save(copy);
		return GoodsResult.success(copy);
	}
	
	@Transactional(readOnly=true)
	public PageList<Goods> findHomePage(int pageNo, int pageSize) {
		return new PageList<Goods>(new PageDataProvider<Goods>() {
			@Override
			public int total() {
				return dao.count("state", Goods.State.ONLINE.toString());
			}

			@Override
			public List<Goods> find(int start, int size) {
				return dao.findPageBy("state", Goods.State.ONLINE.toString(), start, size);
			}
		}, pageNo, pageSize, "/home/");
	}

	@Transactional(readOnly=true)
	public PageList<Rating> findRatings(final Long goodsId, int pageNo, int pageSize) {
		return new PageList<Rating>(new PageDataProvider<Rating>() {
			@Override
			public int total() {
				return ratingDao.count("goodsId", goodsId);
			}

			@Override
			public List<Rating> find(int start, int size) {
				List<Rating> ratingList = ratingDao.findPageBy("goodsId", goodsId, start, size);
				return ratingDao.joinProperty(ratingList, "userId", "login", userDao);
			}
		}, pageNo, pageSize, "/goods/" + goodsId + "/rating/page/");
	}
}
