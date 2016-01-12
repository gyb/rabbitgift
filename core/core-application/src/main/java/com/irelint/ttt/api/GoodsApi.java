package com.irelint.ttt.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;
import com.irelint.ttt.service.GoodsService;

@Service
@Transactional
public class GoodsApi {
	@Autowired 
	private CategoryMap categoryMap;
	@Autowired 
	private GoodsService goodsService;

	@Transactional(readOnly=true)
	public GoodsDto get(Long id) {
		return goodsService.get(id);
	}
	
	public String getCategory(Integer categoryId) {
		return categoryMap.getMap().get(categoryId);
	}
	
	public Map<Integer, String> getCategory() {
		return categoryMap.getMap();
	}

	public void create(GoodsDto goods) {
		goodsService.create(goods);
	}

	@Transactional(readOnly=true)
	public Page<GoodsDto> findHomePage(Pageable pageable) {
		return goodsService.findHomePage(pageable);
	}

	@Transactional(readOnly=true)
	public Page<GoodsDto> findCreatedPage(Long userId, Pageable pageable) {
		return goodsService.findCreatedPage(userId, pageable);
	}

	@Transactional(readOnly=true)
	public Page<GoodsDto> findOnlinePage(Long userId, Pageable pageable) {
		return goodsService.findOnlinePage(userId, pageable);
	}

	@Transactional(readOnly=true)
	public Page<GoodsDto> findOfflinePage(Long userId, Pageable pageable) {
		return goodsService.findOfflinePage(userId, pageable);
	}

	public GoodsResult putOnline(Long goodsId) {
		return goodsService.putOnline(goodsId);
	}

	public GoodsResult putOffline(Long goodsId) {
		return goodsService.putOffline(goodsId);
	}

	public GoodsResult copy(Long goodsId) {
		return goodsService.copy(goodsId);
	}
}
