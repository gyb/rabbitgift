package com.irelint.ttt.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.goods.GoodsResult;
import com.irelint.ttt.goods.GoodsService;
import com.irelint.ttt.goods.model.CategoryMap;
import com.irelint.ttt.goods.model.Goods;

@Service
@Transactional
public class GoodsApi {
	@Autowired 
	private CategoryMap categoryMap;
	@Autowired 
	private GoodsService goodsService;

	@Transactional(readOnly=true)
	public Goods get(Long id) {
		return goodsService.get(id);
	}
	
	public String getCategory(Integer categoryId) {
		return categoryMap.getMap().get(categoryId);
	}
	
	public Map<Integer, String> getCategory() {
		return categoryMap.getMap();
	}

	public void create(Goods goods) {
		goodsService.create(goods);
	}

	@Transactional(readOnly=true)
	public Page<Goods> findHomePage(Pageable pageable) {
		return goodsService.findHomePage(pageable);
	}

	@Transactional(readOnly=true)
	public Page<Goods> findCreatedPage(Long userId, Pageable pageable) {
		return goodsService.findCreatedPage(userId, pageable);
	}

	@Transactional(readOnly=true)
	public Page<Goods> findOnlinePage(Long userId, Pageable pageable) {
		return goodsService.findOnlinePage(userId, pageable);
	}

	@Transactional(readOnly=true)
	public Page<Goods> findOfflinePage(Long userId, Pageable pageable) {
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
