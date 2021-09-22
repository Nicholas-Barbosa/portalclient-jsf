package com.portal.client.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.OrderPersisted;
import com.portal.client.dto.OrderSemiProjectionPage;
import com.portal.client.dto.OrderToPersist;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.repository.aop.OptionalEmptyRepository;
import com.portal.client.security.api.helper.OrcamentoAPIHelper;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderRepositoryImpl extends OptionalEmptyRepository implements OrderRepository {

	private TokenedRestClient restClient;
	private OrcamentoAPIHelper orcamentoAPI;

	@Inject
	public OrderRepositoryImpl(TokenedRestClient restClient, OrcamentoAPIHelper orcamentoAPI) {
		super();
		this.restClient = restClient;
		this.orcamentoAPI = orcamentoAPI;
	}

	@Override
	public void persist(Order order) {
		OrderToPersist transientOrder = OrderToPersist.of(order);
		OrderPersisted managedOrder = restClient.post(orcamentoAPI.buildEndpoint("orders"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), OrderPersisted.class, null, null, transientOrder,
				MediaType.APPLICATION_JSON);
		order.setCode(managedOrder.getCode());
	}

	@Override
	public Optional<OrderSemiProjectionPage> findAll(int page, int pageSize) {
		Map<String, Object> queryParams = Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC");
		return Optional.of(restClient.get(orcamentoAPI.buildEndpoint("orders"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), OrderSemiProjectionPage.class, queryParams, null, "application/json"));
	}

}
