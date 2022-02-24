package com.portal.client.service.crud;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.OrderFullProjection;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.repository.OrderRepository;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderCrudServiceImpl implements OrderCrudService {

	@Inject
	private OrderRepository repository;

	@Override
	public void persist(Order order) throws OrderBadRequestExcpetion {
		repository.persist(order);

	}

	@Override
	public Optional<OrderSemiProjectionPage> findAll(int page, int pageSize) {
		return repository.findAll(page, pageSize);
	}

	@Override
	public Optional<OrderFullProjection> findByCode(String code) {
		Optional<OrderFullProjection> maybe = repository.findByCode(code);
		return maybe;
	}

	@Override
	public Optional<OrderSemiProjectionPage> findByNameOrCnpj(String nameOrCnpj, int page, int pageSize) {
		// TODO Auto-generated method stub
		return repository.findByNameOrCnpj(nameOrCnpj, page, pageSize);
	}

}
