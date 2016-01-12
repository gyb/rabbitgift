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

	Page<GoodsDto> findOfflinePage(Long userId, Pageable pageable);

	Page<GoodsDto> findOnlinePage(Long userId, Pageable pageable);

	Page<GoodsDto> findCreatedPage(Long userId, Pageable pageable);

	Page<GoodsDto> findHomePage(Pageable pageable);

	void create(GoodsDto goods);

	Map<Integer, String> getCategory();

	String getCategory(Integer categoryId);

	GoodsDto get(Long id);
}
