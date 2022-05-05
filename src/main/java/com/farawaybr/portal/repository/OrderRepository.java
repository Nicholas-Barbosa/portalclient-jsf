package com.farawaybr.portal.repository;

import java.util.Optional;

import com.farawaybr.portal.dto.OrderFullProjection;
import com.farawaybr.portal.dto.OrderSemiProjectionPage;
import com.farawaybr.portal.vo.Order;

public interface OrderRepository {

	void persist(Order order)throws OrderBadRequestExcpetion;

	Optional<OrderSemiProjectionPage> findAll(String nameOrCnpj,int page, int pageSize);
	
	Optional<OrderFullProjection> findByCode(String code);
}
