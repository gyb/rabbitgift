package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.dto.InventoryDto;
import com.irelint.ttt.service.InventoryService;

@Service
@Transactional
public class InventoryApi {
	
	@Autowired
	private InventoryService inventoryService;

	@Transactional(readOnly=true)
	public InventoryDto findByGoodsId(Long goodsId) {
		return inventoryService.findByGoodsId(goodsId);
	}

	public void add(Long goodsId, Integer number) {
		inventoryService.add(goodsId, number);
	}

}
