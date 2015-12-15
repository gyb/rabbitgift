package com.irelint.ttt.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RatingDao extends PagingAndSortingRepository<Rating, Long> {

	Page<Rating> findByGoodsId(Long goodsId, Pageable pageable);
}
