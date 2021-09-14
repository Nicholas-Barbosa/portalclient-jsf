package com.portal.client.repository;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.OrderPersisted;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.dto.OrderToPersist;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderRepositoryImpl implements OrderRepository {

	private RestClient restClient;

	private final ServerAPI ORCMANETO_API;
	private final String basePath;

	@Inject
	public OrderRepositoryImpl(RestClient restClient, UserSessionAPIManager apiManager,
			ConfigPropertyResolver properties) {
		super();
		this.restClient = restClient;
		this.ORCMANETO_API = apiManager.getAPI("ORCAMENTO_API");
		this.basePath = properties.getProperty("neworder_endpoint");
		;
	}

	@Override
	public void persist(Order order) {
		OrderToPersist transientOrder = OrderToPersist.of(order);
		OrderPersisted managedOrder = restClient.post(basePath, ORCMANETO_API.getToken(),
				ORCMANETO_API.getTokenPrefix(), OrderPersisted.class, null, null, transientOrder,
				MediaType.APPLICATION_JSON);
		order.setCode(managedOrder.getCode());
	}

	@Override
	public OrderSemiProjectionPage findAll(int page, int pageSize) {
		Map<String, Object> queryParams = Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC");
		return restClient.get(basePath, ORCMANETO_API.getToken(), ORCMANETO_API.getTokenPrefix(),
				OrderSemiProjectionPage.class, queryParams, null, "application/json");
	}

}
