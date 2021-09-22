package com.portal.client.service.crud;

import java.util.Optional;

import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.vo.Order;

public interface OrderCrudService extends CrudService {

	void persist(Order order);

	Optional<OrderSemiProjectionPage> findAll(int page, int pageSize);
}
