package com.irelint.ttt.goods;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.order.Rating;
import com.irelint.ttt.order.RatingDao;
import com.irelint.ttt.user.UserDao;

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
	public Page<Goods> findCreatedPage(final Long userId, Pageable pageable) {
		return dao.findByUserIdAndState(userId, Goods.State.CREATED, pageable);
		//"/myshop/createdPage/"
	}
	
	@Transactional(readOnly=true)
	@Cacheable("goods")
	public Goods get(Long goodsId) {
		return dao.findOne(goodsId);
	}

	@Transactional
	@OptimisticLockRetry
	@CacheEvict("goods")
	public GoodsResult putOnline(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.isNew()) {
			return GoodsResult.fail(goods);
		}
		
		goods.setState(Goods.State.ONLINE);
		goods.setOnlineTime(new Timestamp(System.currentTimeMillis()));
		return GoodsResult.success(goods);
	}

	@Transactional(readOnly=true)
	public Page<Goods> findOnlinePage(final Long userId, Pageable pageable) {
		return dao.findByUserIdAndState(userId, Goods.State.ONLINE, pageable);
		//"/myshop/onlinePage/"
	}
	
	@Transactional
	@CacheEvict("goods")
	public GoodsResult putOffline(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.isOnline()) {
			return GoodsResult.fail(goods);
		}
		
		goods.setState(Goods.State.OFFLINE);
		goods.setOfflineTime(new Timestamp(System.currentTimeMillis()));
		return GoodsResult.success(goods);
	}

	@Transactional(readOnly=true)
	public Page<Goods> findOfflinePage(final Long userId, Pageable pageable) {
		return dao.findByUserIdAndState(userId, Goods.State.OFFLINE, pageable);
		//"/myshop/offlinePage/"
	}

	@Transactional
	public GoodsResult copy(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.isOffline()) {
			return GoodsResult.fail(goods);
		}
		
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
	public Page<Goods> findHomePage(Pageable pageable) {
		return dao.findByState(Goods.State.ONLINE, pageable);
		//"/home/");
	}

	@Transactional(readOnly=true)
	public Page<Rating> findRatings(final Long goodsId, Pageable pageable) {
		return ratingDao.findByGoodsId(goodsId, pageable);
		//"/goods/" + goodsId + "/rating/page/");
	}
}
