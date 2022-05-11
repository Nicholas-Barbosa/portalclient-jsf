package com.farawaybr.portal.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.cdi.aop.annotations.OrderBadRequestJoinPointCut;
import com.farawaybr.portal.dto.OrderFullProjection;
import com.farawaybr.portal.dto.OrderPersisted;
import com.farawaybr.portal.dto.OrderSemiProjectionPage;
import com.farawaybr.portal.dto.OrderToPersist;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.security.api.helper.ProtheusAPIHelper;
import com.farawaybr.portal.vo.Order;

@ApplicationScoped
public class OrderRepositoryImpl extends RepositoryInterceptors implements OrderRepository {

	private RestClient restClient;
	private APIHelper protheusApiHelper;

	@Inject
	public OrderRepositoryImpl(RestClient restClient, ProtheusAPIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	@OrderBadRequestJoinPointCut
	public void persist(Order order) {
		OrderToPersist transientOrder = OrderToPersist.of(order);
		OrderPersisted managedOrder = restClient.post(protheusApiHelper.buildEndpoint("orders"), OrderPersisted.class,
				null, null, transientOrder, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken()));
		order.setCode(managedOrder.getCode());

	}

	@Override
	public Optional<OrderFullProjection> findByCode(String code) {
		// TODO Auto-generated method stub
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("orders/{code}"), OrderFullProjection.class,
				null, Map.of("code", code), "application/json",
				Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())));
	}

	@Override
	public Optional<OrderSemiProjectionPage> findAll(String nameOrCnpj, int page, int pageSize) {
		Map<String, Object> queryParams = Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC", "searchKey",
				nameOrCnpj == null ? "" : nameOrCnpj);
		return Optional.of(
				restClient.get(protheusApiHelper.buildEndpoint("orders"), OrderSemiProjectionPage.class, queryParams,
						null, "application/json", Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())));
	}

}
