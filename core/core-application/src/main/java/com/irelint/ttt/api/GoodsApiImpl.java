package com.irelint.ttt.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.dto.GoodsDto;
import com.irelint.ttt.dto.GoodsResult;
import com.irelint.ttt.service.GoodsService;

@Service
@DubboService(interfaceClass=GoodsApi.class)
public class GoodsApiImpl implements GoodsApi {

	@Autowired
	private CategoryMap categoryMap;
	@Autowired
	private GoodsService goodsService;

	@Override
	public GoodsDto get(Long id) {
		return goodsService.get(id);
	}

	@Override
	public String getCategory(Integer categoryId) {
		return categoryMap.getMap().get(categoryId);
	}

	@Override
	public Map<Integer, String> getCategory() {
		return categoryMap.getMap();
	}

	@Override
	public void create(GoodsDto goods) {
		goodsService.create(goods);
	}

	@Override
	public Page<GoodsDto> findHomePage(Pageable pageable) {
		return goodsService.findHomePage(pageable);
	}

	@Override
	public Page<GoodsDto> findCreatedPage(Long userId, Pageable pageable) {
		return goodsService.findCreatedPage(userId, pageable);
	}

	@Override
	public Page<GoodsDto> findOnlinePage(Long userId, Pageable pageable) {
		return goodsService.findOnlinePage(userId, pageable);
	}

	@Override
	public Page<GoodsDto> findOfflinePage(Long userId, Pageable pageable) {
		return goodsService.findOfflinePage(userId, pageable);
	}

	@Override
	public GoodsResult putOnline(Long goodsId) {
		return goodsService.putOnline(goodsId);
	}

	@Override
	public GoodsResult putOffline(Long goodsId) {
		return goodsService.putOffline(goodsId);
	}

	@Override
	public GoodsResult copy(Long goodsId) {
		return goodsService.copy(goodsId);
	}

}
