package com.irelint.ttt.goods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.irelint.ttt.goods.GoodsResult;
import com.irelint.ttt.goods.model.Goods;

public interface GoodsService {

	void create(Goods goods);

	Page<Goods> findCreatedPage(Long userId, Pageable pageable);

	Goods get(Long goodsId);

	GoodsResult putOnline(Long goodsId);

	Page<Goods> findOnlinePage(Long userId, Pageable pageable);

	GoodsResult putOffline(Long goodsId);

	Page<Goods> findOfflinePage(Long userId, Pageable pageable);

	GoodsResult copy(Long goodsId);

	Page<Goods> findHomePage(Pageable pageable);

}