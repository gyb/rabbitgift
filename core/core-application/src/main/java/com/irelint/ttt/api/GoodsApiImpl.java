package com.irelint.ttt.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public Page<GoodsDto> findHomePage(int page, int size) {
		return goodsService.findHomePage(page, size);
	}

	@Override
	public Page<GoodsDto> findCreatedPage(Long userId, int page, int size) {
		return goodsService.findCreatedPage(userId, page, size);
	}

	@Override
	public Page<GoodsDto> findOnlinePage(Long userId, int page, int size) {
		return goodsService.findOnlinePage(userId, page, size);
	}

	@Override
	public Page<GoodsDto> findOfflinePage(Long userId, int page, int size) {
		return goodsService.findOfflinePage(userId, page, size);
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
