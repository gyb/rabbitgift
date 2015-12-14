package com.irelint.ttt.order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.irelint.ttt.order.OrderHistory.Type;

public interface OrderHistoryDao extends CrudRepository<OrderHistory, Long> {

	OrderHistory findByOrderIdAndType(Long orderId, Type type);

	List<OrderHistory> findByOrderId(Long orderId);
}
