package com.portal.client.service.crud;

import com.portal.client.vo.Order;

public interface OrderCrudService extends CrudService {

	void persist(Order order);
}
