package com.irelint.ttt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;
import com.irelint.ttt.event.GoodsRatedEvent;

public interface GoodsService {

	void create(GoodsDto goods);

	Page<GoodsDto> findCreatedPage(Long userId, Pageable pageable);

	GoodsDto get(Long goodsId);

	GoodsResult putOnline(Long goodsId);

	Page<GoodsDto> findOnlinePage(Long userId, Pageable pageable);

	GoodsResult putOffline(Long goodsId);

	Page<GoodsDto> findOfflinePage(Long userId, Pageable pageable);

	GoodsResult copy(Long goodsId);

	Page<GoodsDto> findHomePage(Pageable pageable);

	void addRating(GoodsRatedEvent event);
}