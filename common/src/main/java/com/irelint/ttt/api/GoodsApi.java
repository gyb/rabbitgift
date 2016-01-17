package com.irelint.ttt.api;

import java.util.Map;

import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;
import com.irelint.ttt.dto.PageDto;

public interface GoodsApi {

	GoodsResult copy(Long goodsId);

	GoodsResult putOffline(Long goodsId);

	GoodsResult putOnline(Long goodsId);

	PageDto<GoodsDto> findOfflinePage(Long userId, int page, int size);

	PageDto<GoodsDto> findOnlinePage(Long userId, int page, int size);

	PageDto<GoodsDto> findCreatedPage(Long userId, int page, int size);

	PageDto<GoodsDto> findHomePage(int page, int size);

	void create(GoodsDto goods);

	Map<Integer, String> getCategory();

	String getCategory(Integer categoryId);

	GoodsDto get(Long id);
}
