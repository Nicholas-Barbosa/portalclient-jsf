package com.portal.client.service.crud;

import java.util.Optional;

import com.portal.client.dto.OrderFullProjection;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Order;

public interface OrderCrudService extends CrudService {

	void persist(Order order) throws OrderBadRequestExcpetion;
	
	Order persistFromBudget(Budget budget) throws OrderBadRequestExcpetion;

	Optional<OrderSemiProjectionPage> findAll(String nameOrCnpj, int page, int pageSize);

	Optional<OrderFullProjection> findByCode(String code);
}
