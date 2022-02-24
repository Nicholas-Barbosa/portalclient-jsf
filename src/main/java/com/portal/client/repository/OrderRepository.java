package com.portal.client.repository;

import java.util.Optional;

import com.portal.client.dto.OrderFullProjection;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.vo.Order;

public interface OrderRepository {

	void persist(Order order)throws OrderBadRequestExcpetion;

	Optional<OrderSemiProjectionPage> findAll(int page, int pageSize);

	Optional<OrderSemiProjectionPage> findByNameOrCnpj(String nameOrCnpj,int page, int pageSize);
	
	Optional<OrderFullProjection> findByCode(String code);
}
