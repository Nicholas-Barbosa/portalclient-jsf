package com.portal.client.service.crud;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.repository.OrderRepository;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderCrudServiceImpl implements OrderCrudService {

	@Inject
	private OrderRepository repository;

	@Override
	public void persist(Order order) {
		repository.persist(order);

	}

}
