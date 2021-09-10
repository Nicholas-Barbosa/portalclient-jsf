package com.portal.client.repository;

import java.util.List;

import com.portal.client.vo.Order;

public interface OrderRepository {

	void persist(Order order);

	List<Order> findAll(int page, int pageSize);
}
