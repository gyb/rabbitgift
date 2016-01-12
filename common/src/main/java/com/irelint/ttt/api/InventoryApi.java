package com.irelint.ttt.api;

import com.irelint.ttt.dto.InventoryDto;

public interface InventoryApi {

	InventoryDto findByGoodsId(Long goodsId);

	void add(Long goodsId, Integer number);

}