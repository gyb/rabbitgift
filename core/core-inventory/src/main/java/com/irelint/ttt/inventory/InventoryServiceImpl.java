package com.irelint.ttt.inventory;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.aop.OptimisticLockRetry;
import com.irelint.ttt.dto.InventoryDto;
import com.irelint.ttt.event.GoodsCreatedEvent;
import com.irelint.ttt.event.OrderCanceledEvent;
import com.irelint.ttt.event.OrderConfirmedEvent;
import com.irelint.ttt.event.OrderCreatedEvent;
import com.irelint.ttt.service.InventoryService;
import com.irelint.ttt.util.Constants;

@Service
@Transactional
@DubboService(interfaceClass=InventoryService.class)
public class InventoryServiceImpl implements InventoryService {
	
	@Autowired
	private InventoryDao dao;
	@Autowired
	private AmqpTemplate amqpTemplate;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#initInventory(com.irelint.ttt.event.GoodsCreatedEvent)
	 */
	@Override
	@RabbitListener(queues=Constants.QUEUE_GOODSCREATED_INVENTORY)
	public void initInventory(GoodsCreatedEvent event) {
		Inventory inventory = new Inventory();
		inventory.setGoodsId(event.getGoodsId());
		dao.save(inventory);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#orderCreated(com.irelint.ttt.event.OrderCreatedEvent)
	 */
	@Override
	@OptimisticLockRetry
	@RabbitListener(queues=Constants.QUEUE_ORDERCREATED_INVENTORY)
	public void orderCreated(OrderCreatedEvent event) {
		Inventory inventory = dao.findByGoodsId(event.getGoodsId());
		if (inventory.sell(event.getGoodsNumber())) {
			amqpTemplate.convertAndSend(Constants.EXCHANGE_NAME, Constants.EVENT_ORDERCONFIRMED,
					new OrderConfirmedEvent(event.getOrderId()));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.inventory.InventoryService#orderCanceled(com.irelint.ttt.event.OrderCanceledEvent)
	 */
	@Override
	@OptimisticLockRetry
	@RabbitListener(queues=Constants.QUEUE_ORDERCANCELED_INVENTORY)
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
	public InventoryDto findByGoodsId(Long goodsId) {
		return dao.findByGoodsId(goodsId).toDto();
	}

}
