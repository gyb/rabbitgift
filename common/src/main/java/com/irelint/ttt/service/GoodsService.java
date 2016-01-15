package com.irelint.ttt.service;

import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;
import com.irelint.ttt.dto.PageDto;
import com.irelint.ttt.event.GoodsRatedEvent;

public interface GoodsService {

	void create(GoodsDto goods);

	PageDto<GoodsDto> findCreatedPage(Long userId, int page, int size);

	GoodsDto get(Long goodsId);

	GoodsResult putOnline(Long goodsId);

	PageDto<GoodsDto> findOnlinePage(Long userId, int page, int size);

	GoodsResult putOffline(Long goodsId);

	PageDto<GoodsDto> findOfflinePage(Long userId, int page, int size);

	GoodsResult copy(Long goodsId);

	PageDto<GoodsDto> findHomePage(int page, int size);

	void addRating(GoodsRatedEvent event);
}