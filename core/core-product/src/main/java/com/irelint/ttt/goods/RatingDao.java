package com.irelint.ttt.goods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.irelint.ttt.goods.model.Rating;

public interface RatingDao extends PagingAndSortingRepository<Rating, Long> {

	Page<Rating> findByGoodsId(Long goodsId, Pageable pageable);
}
