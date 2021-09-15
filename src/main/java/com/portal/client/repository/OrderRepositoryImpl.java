package com.portal.client.repository;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.OrderPersisted;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.dto.OrderToPersist;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderRepositoryImpl implements OrderRepository {

	private TokenedRestClient restClient;
	private OrcamentoAPIHelper orcamentoAPI;

	private final String basePath;

	@Inject
	public OrderRepositoryImpl(TokenedRestClient restClient, OrcamentoAPIHelper orcamentoAPI,
			ConfigPropertyResolver properties) {
		super();
		this.restClient = restClient;
		this.orcamentoAPI = orcamentoAPI;
		this.basePath = properties.getProperty("neworder_endpoint");
	}

	@Override
	public void persist(Order order) {
		OrderToPersist transientOrder = OrderToPersist.of(order);
		OrderPersisted managedOrder = restClient.post(basePath, orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(),
				OrderPersisted.class, null, null, transientOrder, MediaType.APPLICATION_JSON);
		order.setCode(managedOrder.getCode());
	}

	@Override
	public OrderSemiProjectionPage findAll(int page, int pageSize) {
		Map<String, Object> queryParams = Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC");
		return restClient.get(basePath, orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(),
				OrderSemiProjectionPage.class, queryParams, null, "application/json");
	}

}
