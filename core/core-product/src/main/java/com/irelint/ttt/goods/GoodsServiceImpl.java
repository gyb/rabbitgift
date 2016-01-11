package com.irelint.ttt.goods;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.irelint.ttt.event.State;
import com.irelint.ttt.goods.GoodsDao;
import com.irelint.ttt.goods.GoodsResult;
import com.irelint.ttt.goods.model.Goods;

@Service
public class GoodsServiceImpl implements GoodsService, ApplicationEventPublisherAware {
	@Autowired 
	private GoodsDao dao;
	
	private ApplicationEventPublisher publisher;
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#create(com.irelint.ttt.goods.model.Goods)
	 */
	@Override
	@Transactional
	public void create(Goods goods) {
		dao.save(goods);
		publisher.publishEvent(new GoodsCreatedEvent(this, goods.getId(), goods.getOwnerId(), goods.getPrice(), State.CREATED));
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findCreatedPage(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Goods> findCreatedPage(final Long userId, Pageable pageable) {
		return dao.findByOwnerIdAndState(userId, State.CREATED, pageable);
		//"/myshop/createdPage/"
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	@Cacheable("goods")
	public Goods get(Long goodsId) {
		return dao.findOne(goodsId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#putOnline(java.lang.Long)
	 */
	@Override
	@Transactional
	@OptimisticLockRetry
	@CacheEvict("goods")
	public GoodsResult putOnline(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.putOnline()) {
			return GoodsResult.fail(goods);
		}
		
		return GoodsResult.success(goods);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findOnlinePage(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Goods> findOnlinePage(final Long userId, Pageable pageable) {
		return dao.findByOwnerIdAndState(userId, State.ONLINE, pageable);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#putOffline(java.lang.Long)
	 */
	@Override
	@Transactional
	@CacheEvict("goods")
	public GoodsResult putOffline(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.putOffline()) {
			return GoodsResult.fail(goods);
		}

		return GoodsResult.success(goods);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findOfflinePage(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Goods> findOfflinePage(final Long userId, Pageable pageable) {
		return dao.findByOwnerIdAndState(userId, State.OFFLINE, pageable);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#copy(java.lang.Long)
	 */
	@Override
	@Transactional
	public GoodsResult copy(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.isOffline()) {
			return GoodsResult.fail(goods);
		}
		
		Goods copy = goods.createCopy();
		dao.save(copy);
		publisher.publishEvent(new GoodsCreatedEvent(this, copy.getId(), copy.getOwnerId(), copy.getPrice(), State.CREATED));
		return GoodsResult.success(copy);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findHomePage(org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<Goods> findHomePage(Pageable pageable) {
		return dao.findByState(State.ONLINE, pageable);
	}

	@Override
	@Transactional
	@EventListener
	public void addRating(GoodsRatedEvent event) {
		Goods goods = dao.findOne(event.getGoodsId());
		goods.addRating(event.getRatingNumber());
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}