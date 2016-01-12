package com.irelint.ttt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.DubboService;
import com.irelint.ttt.dto.InventoryDto;
import com.irelint.ttt.service.InventoryService;

@Service
@DubboService
public class InventoryApiImpl implements InventoryApi {
	
	@Autowired
	private InventoryService inventoryService;

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.InventoryApi#findByGoodsId(java.lang.Long)
	 */
	@Override
	public InventoryDto findByGoodsId(Long goodsId) {
		return inventoryService.findByGoodsId(goodsId);
	}

	/* (non-Javadoc)
	 * @see com.irelint.ttt.api.InventoryApi#add(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void add(Long goodsId, Integer number) {
		inventoryService.add(goodsId, number);
	}

}
