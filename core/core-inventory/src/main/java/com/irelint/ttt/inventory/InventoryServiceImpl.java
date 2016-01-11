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
public class InventoryServiceImpl implements ApplicationEventPublisherAware, InventoryService {
	
	@Autowired
	private InventoryDao dao;
	
	private ApplicationEventPublisher publisher;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#initInventory(com.irelint.ttt.event.GoodsCreatedEvent)
	 */
	@Override
	@EventListener
	public void initInventory(GoodsCreatedEvent event) {
		Inventory inventory = new Inventory();
		inventory.setGoodsId(event.getGoodsId());
		dao.save(inventory);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#orderCreated(com.irelint.ttt.event.OrderCreatedEvent)
	 */
	@Override
	@EventListener
	@OptimisticLockRetry
	public void orderCreated(OrderCreatedEvent event) {
		Inventory inventory = dao.findByGoodsId(event.getGoodsId());
		if (inventory.sell(event.getGoodsNumber())) {
			publisher.publishEvent(new OrderConfirmedEvent(this, event.getOrderId()));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#orderCanceled(com.irelint.ttt.event.OrderCanceledEvent)
	 */
	@Override
	@EventListener
	@OptimisticLockRetry
	public void orderCanceled(OrderCanceledEvent event) {
		Inventory inventory = dao.findByGoodsId(event.getGoodsId());
		inventory.cancel(event.getGoodsNumber());
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#add(java.lang.Long, java.lang.Integer)
	 */
	@Override
	@OptimisticLockRetry
	public void add(Long goodsId, Integer number) {
		Inventory inventory = dao.findByGoodsId(goodsId);
		inventory.add(number);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#findByGoodsId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly=true)
	public Inventory findByGoodsId(Long goodsId) {
		return dao.findByGoodsId(goodsId);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
