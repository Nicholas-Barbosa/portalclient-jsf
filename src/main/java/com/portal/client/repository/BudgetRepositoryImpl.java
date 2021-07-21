package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.client.rest.RestClient;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.vo.BudgetPage;

@ApplicationScoped
public class BudgetRepositoryImpl implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final RestClient restClient;
	private final UserSessionAPIManager apiManager;

	public BudgetRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public BudgetRepositoryImpl(RestClient restClient, UserSessionAPIManager apiManager) {
		super();
		this.restClient = restClient;
		this.apiManager = apiManager;
	}

	@Override
	public BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		ServerAPI api = apiManager.getAPI("ORCAMENTO_API");
		StringBuilder endpointURL = new StringBuilder(api.getBasePath());
		endpointURL.append("/budgets");
		return restClient.get(endpointURL.toString(), api.getToken(), api.getTokenPrefix(), BudgetPage.class,
				Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC"), null, MediaType.APPLICATION_JSON);
	}

}
