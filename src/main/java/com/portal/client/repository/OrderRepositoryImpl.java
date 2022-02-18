package com.portal.client.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.cdi.aop.annotations.OrderBadRequestJoinPointCut;
import com.portal.client.dto.OrderFullProjection;
import com.portal.client.dto.OrderPersisted;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.dto.OrderToPersist;
import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderRepositoryImpl extends OptionalEmptyRepository implements OrderRepository {

	private TokenedRestClient restClient;
	private APIHelper protheusApiHelper;

	@Inject
	public OrderRepositoryImpl(TokenedRestClient restClient, ProtheusAPIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	@OrderBadRequestJoinPointCut
	public void persist(Order order) {
		OrderToPersist transientOrder = OrderToPersist.of(order);
		OrderPersisted managedOrder = restClient.post(protheusApiHelper.buildEndpoint("orders"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), OrderPersisted.class, null, null, transientOrder,
				MediaType.APPLICATION_JSON);
		order.setCode(managedOrder.getCode());

	}

	@Override
	public Optional<OrderSemiProjectionPage> findAll(int page, int pageSize) {
		Map<String, Object> queryParams = Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC");
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("orders"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), OrderSemiProjectionPage.class, queryParams, null, "application/json"));
	}

	@Override
	public Optional<OrderFullProjection> findByCode(String code) {
		// TODO Auto-generated method stub
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("orders/{code}"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), OrderFullProjection.class, null, Map.of("code", code),
				"application/json"));
	}

}
