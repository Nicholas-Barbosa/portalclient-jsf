package com.portal.client.service.crud;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.OrderFullProjection;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.repository.OrderRepository;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderCrudServiceImpl implements OrderCrudService {

	@Inject
	private OrderRepository repository;

	@Inject
	private OrderCommonBehaviorHelper orderHelper;

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
		maybe.ifPresent(orderHelper::sumStValue);
		return maybe;
	}

}
