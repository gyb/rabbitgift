package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.inventory.Inventory;
import com.irelint.ttt.inventory.InventoryService;

@Service
@Transactional
public class InventoryApi {
	
	@Autowired
	private InventoryService inventoryService;

	@Transactional(readOnly=true)
	public Inventory findByGoodsId(Long goodsId) {
		return inventoryService.findByGoodsId(goodsId);
	}

	public void add(Long goodsId, Integer number) {
		inventoryService.add(goodsId, number);
	}

}
