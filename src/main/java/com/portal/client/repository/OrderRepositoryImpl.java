package com.portal.client.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.OrderPersisted;
import com.portal.client.dto.OrderToPersist;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderRepositoryImpl implements OrderRepository {

	@Inject
	private RestClient restClient;
	@Inject
	private UserSessionAPIManager apiManager;

	@Override
	public void save(Order order) {
		OrderToPersist transientOrder = OrderToPersist.of(order);
		ServerAPI api = apiManager.getAPI("ORCMANETO_API");
		OrderPersisted managedOrder = restClient.post(apiManager.buildEndpoint(api, "orders"), api.getToken(),
				api.getTokenPrefix(), OrderPersisted.class, null, null, transientOrder, MediaType.APPLICATION_JSON);
		order.setCode(managedOrder.getCode());
	}

	@Override
	public List<Order> findAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
