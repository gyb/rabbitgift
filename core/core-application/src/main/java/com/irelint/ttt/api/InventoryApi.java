package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.irelint.ttt.inventory.Inventory;
import com.irelint.ttt.inventory.InventoryService;

public class InventoryApi {
	
	@Autowired
	private InventoryService inventoryService;

	public Inventory findByGoodsId(Long goodsId) {
		return inventoryService.findByGoodsId(goodsId);
	}

	public void add(Long goodsId, Integer number) {
		inventoryService.add(goodsId, number);
	}

}
