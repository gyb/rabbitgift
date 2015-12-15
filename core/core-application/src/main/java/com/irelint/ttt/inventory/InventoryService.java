package com.irelint.ttt.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.OrderCanceledEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderCreatedEvent;

@Service
@Transactional
public class InventoryService implements ApplicationEventPublisherAware {
	
	@Autowired
	private InventoryDao dao;
	
	private ApplicationEventPublisher publisher;

	@EventListener
	public void initInventory(GoodsCreatedEvent event) {
		Inventory inventory = new Inventory();
		inventory.setGoodsId(event.getGoodsId());
		dao.save(inventory);
	}
	
	@EventListener
	@OptimisticLockRetry
	public void orderCreated(OrderCreatedEvent event) {
		Inventory inventory = dao.findByGoodsId(event.getGoodsId());
		if (inventory.sell(event.getGoodsNumber())) {
			publisher.publishEvent(new OrderConfirmedEvent(this, event.getOrderId()));
		}
	}
	
	@EventListener
	@OptimisticLockRetry
	public void orderCanceled(OrderCanceledEvent event) {
		Inventory inventory = dao.findByGoodsId(event.getGoodsId());
		inventory.cancel(event.getGoodsNumber());
	}

	@OptimisticLockRetry
	public void add(Long goodsId, Integer number) {
		Inventory inventory = dao.findByGoodsId(goodsId);
		inventory.add(number);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@Transactional(readOnly=true)
	public Inventory findByGoodsId(Long goodsId) {
		return dao.findByGoodsId(goodsId);
	}
}
