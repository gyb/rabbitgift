package com.irelint.ttt.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;

public interface GoodsApi {

	GoodsResult copy(Long goodsId);

	GoodsResult putOffline(Long goodsId);

	GoodsResult putOnline(Long goodsId);

	Page<GoodsDto> findOfflinePage(Long userId, int page, int size);

	Page<GoodsDto> findOnlinePage(Long userId, int page, int size);

	Page<GoodsDto> findCreatedPage(Long userId, int page, int size);

	Page<GoodsDto> findHomePage(int page, int size);

	void create(GoodsDto goods);

	Map<Integer, String> getCategory();

	String getCategory(Integer categoryId);

	GoodsDto get(Long id);
}
