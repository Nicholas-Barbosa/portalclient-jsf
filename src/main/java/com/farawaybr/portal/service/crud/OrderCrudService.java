package com.farawaybr.portal.service.crud;

import java.util.Optional;

import com.farawaybr.portal.dto.OrderFullProjection;
import com.farawaybr.portal.dto.OrderSemiProjectionPage;
import com.farawaybr.portal.repository.OrderBadRequestExcpetion;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Order;

public interface OrderCrudService extends CrudService {

	void persist(Order order) throws OrderBadRequestExcpetion;
	
	Order persistFromBudget(Budget budget) throws OrderBadRequestExcpetion;

	Optional<OrderSemiProjectionPage> findAll(String nameOrCnpj, int page, int pageSize);

	Optional<OrderFullProjection> findByCode(String code);
}
