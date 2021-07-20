package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.client.rest.auth.AuthenticatedRestClient;
import com.portal.client.vo.BudgetListPage;

@ApplicationScoped
public class BudgetRepositoryImpl implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final AuthenticatedRestClient restClient;

	public BudgetRepositoryImpl() {
		this(null);
	}

	@Inject
	public BudgetRepositoryImpl(@OAuth2RestAuth AuthenticatedRestClient restClient) {
		super();
		this.restClient = restClient;
	}

	@Override
	public BudgetListPage findAll(int page, int pageSize) throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return restClient.get("ORCAMENTO_API", "budgets", BudgetListPage.class,
				Map.of("page", page, "pageSize", pageSize), null, MediaType.APPLICATION_JSON);
	}

}
