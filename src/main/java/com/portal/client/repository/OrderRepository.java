package com.portal.client.repository;

import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.vo.Order;

public interface OrderRepository {

	void persist(Order order);

	OrderSemiProjectionPage findAll(int page, int pageSize);
}
