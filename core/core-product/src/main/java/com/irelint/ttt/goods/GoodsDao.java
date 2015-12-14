package com.irelint.ttt.goods;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.irelint.ttt.goods.model.Goods;
import com.irelint.ttt.goods.model.Goods.State;

public interface GoodsDao extends PagingAndSortingRepository<Goods, Long> {
	
	Page<Goods> findByOwnerIdAndState(Long ownerId, State state, Pageable pageable);
	
	Page<Goods> findByState(State state, Pageable pageable);
}
