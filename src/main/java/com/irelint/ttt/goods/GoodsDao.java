package com.irelint.ttt.goods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.irelint.ttt.goods.Goods.State;

public interface GoodsDao extends PagingAndSortingRepository<Goods, Long> {
	
	Page<Goods> findByUserIdAndState(Long userId, State state, Pageable pageable);
	
	Page<Goods> findByState(State state, Pageable pageable);
}
