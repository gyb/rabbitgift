package com.irelint.ttt.service;

import com.irelint.ttt.dto.InventoryDto;
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.OrderCanceledEvent;
import com.irelint.ttt.event.OrderCreatedEvent;

public interface InventoryService {

	void initInventory(GoodsCreatedEvent event);

	void orderCreated(OrderCreatedEvent event);

	void orderCanceled(OrderCanceledEvent event);

	void add(Long goodsId, Integer number);

	InventoryDto findByGoodsId(Long goodsId);

}