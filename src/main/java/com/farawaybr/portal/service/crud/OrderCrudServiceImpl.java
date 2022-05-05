package com.farawaybr.portal.service.crud;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.dto.OrderFullProjection;
import com.farawaybr.portal.dto.OrderSemiProjectionPage;
import com.farawaybr.portal.repository.OrderBadRequestExcpetion;
import com.farawaybr.portal.repository.OrderRepository;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Order;

@ApplicationScoped
public class OrderCrudServiceImpl implements OrderCrudService {

	@Inject
	private OrderRepository repository;

	@Override
	public void persist(Order order) throws OrderBadRequestExcpetion {
		repository.persist(order);

	}

	@Override
	public Optional<OrderSemiProjectionPage> findAll(String nameOrCnpj, int page, int pageSize) {
		return repository.findAll(nameOrCnpj, page, pageSize);
	}

	@Override
	public Optional<OrderFullProjection> findByCode(String code) {
		Optional<OrderFullProjection> maybe = repository.findByCode(code);
		maybe.ifPresent(order -> order.setCustomerNumOrder(order.getItems().get(0).getCustomerOrder()));
		return maybe;
	}

	@Override
	public Order persistFromBudget(Budget budget) throws OrderBadRequestExcpetion {
		Order persitedOrder = new Order(budget);
		persitedOrder.getItems().parallelStream().forEach(item -> item.setCustomerOrder(budget.getCustomerNumOrder()));
		this.persist(persitedOrder);
		return persitedOrder;
	}

}
