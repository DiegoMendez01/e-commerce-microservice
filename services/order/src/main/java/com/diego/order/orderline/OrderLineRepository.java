package com.diego.order.orderline;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLine, Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
