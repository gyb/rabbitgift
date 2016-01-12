package com.irelint.ttt.goods;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;
import com.irelint.ttt.dto.State;
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.GoodsRatedEvent;
import com.irelint.ttt.event.GoodsUpdatedEvent;
import com.irelint.ttt.goods.model.Goods;
import com.irelint.ttt.service.GoodsService;

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
	public void create(GoodsDto dto) {
		Goods goods = Goods.fromDto(dto);
		dao.save(goods);
		publisher.publishEvent(new GoodsCreatedEvent(this, goods.getId(), goods.getOwnerId(), goods.getPrice(), State.CREATED));
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findCreatedPage(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<GoodsDto> findCreatedPage(final Long userId, Pageable pageable) {
		Page<Goods> page = dao.findByOwnerIdAndState(userId, State.CREATED, pageable);
		return new PageImpl<GoodsDto>(
				page.getContent().stream().map(g -> g.toDto()).collect(Collectors.toList()),
				pageable, page.getTotalElements());
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#get(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public GoodsDto get(Long goodsId) {
		return dao.findOne(goodsId).toDto();
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#putOnline(java.lang.Long)
	 */
	@Override
	@Transactional
	@OptimisticLockRetry
	public GoodsResult putOnline(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.putOnline()) {
			return GoodsResult.fail(goods.toDto());
		}
		
		publisher.publishEvent(new GoodsUpdatedEvent(this, goodsId, goods.getPrice(), goods.getState()));
		return GoodsResult.success(goods.toDto());
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findOnlinePage(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<GoodsDto> findOnlinePage(final Long userId, Pageable pageable) {
		Page<Goods> page = dao.findByOwnerIdAndState(userId, State.ONLINE, pageable);
		return new PageImpl<GoodsDto>(
				page.getContent().stream().map(g -> g.toDto()).collect(Collectors.toList()),
				pageable, page.getTotalElements());
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#putOffline(java.lang.Long)
	 */
	@Override
	@Transactional
	public GoodsResult putOffline(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.putOffline()) {
			return GoodsResult.fail(goods.toDto());
		}

		publisher.publishEvent(new GoodsUpdatedEvent(this, goodsId, goods.getPrice(), goods.getState()));
		return GoodsResult.success(goods.toDto());
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findOfflinePage(java.lang.Long, org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<GoodsDto> findOfflinePage(final Long userId, Pageable pageable) {
		Page<Goods> page = dao.findByOwnerIdAndState(userId, State.OFFLINE, pageable);
		return new PageImpl<GoodsDto>(
				page.getContent().stream().map(g -> g.toDto()).collect(Collectors.toList()),
				pageable, page.getTotalElements());
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#copy(java.lang.Long)
	 */
	@Override
	@Transactional
	public GoodsResult copy(Long goodsId) {
		Goods goods = dao.findOne(goodsId);
		if (!goods.isOffline()) {
			return GoodsResult.fail(goods.toDto());
		}
		
		Goods copy = goods.createCopy();
		dao.save(copy);
		publisher.publishEvent(new GoodsCreatedEvent(this, copy.getId(), copy.getOwnerId(), copy.getPrice(), State.CREATED));
		return GoodsResult.success(copy.toDto());
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.goods.service.GoodsServic#findHomePage(org.springframework.data.domain.Pageable)
	 */
	@Override
	@Transactional(readOnly=true)
	public Page<GoodsDto> findHomePage(Pageable pageable) {
		Page<Goods> page = dao.findByState(State.ONLINE, pageable);
		return new PageImpl<GoodsDto>(
				page.getContent().stream().map(g -> g.toDto()).collect(Collectors.toList()),
				pageable, page.getTotalElements());
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
