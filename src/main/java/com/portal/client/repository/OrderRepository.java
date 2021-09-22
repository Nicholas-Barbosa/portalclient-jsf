package com.portal.client.repository;

import java.util.Optional;

import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.vo.Order;

public interface OrderRepository {

	void persist(Order order);

	Optional<OrderSemiProjectionPage> findAll(int page, int pageSize);
}
