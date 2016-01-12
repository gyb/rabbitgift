package com.irelint.ttt.order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.irelint.ttt.dto.HistoryType;

public interface OrderHistoryDao extends CrudRepository<OrderHistory, Long> {

	OrderHistory findByOrderIdAndType(Long orderId, HistoryType type);

	List<OrderHistory> findByOrderId(Long orderId);
}
