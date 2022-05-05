package com.farawaybr.portal.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.BudgetFullProjection;
import com.farawaybr.portal.dto.BudgetSavedResponse;
import com.farawaybr.portal.dto.BudgetSemiProjection;
import com.farawaybr.portal.dto.BudgetSemiProjectionPage;
import com.farawaybr.portal.dto.BudgetToSaveJsonSerializable;
import com.farawaybr.portal.dto.BudgetToUpdateDTO;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Page;
import com.nicholas.jaxrsclient.TokenedRestClient;

@ApplicationScoped
public class BudgetRepositoryImpl extends RepositoryInterceptors implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final TokenedRestClient restClient;
	private APIHelper protheusApiHelper;

	public BudgetRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public BudgetRepositoryImpl(TokenedRestClient restClient, APIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	public Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize, String key) {
		StringBuilder endpointURL = new StringBuilder(protheusApiHelper.getBaseUrl());
		endpointURL.append("/budgets");
		return Optional
				.of(restClient.get(endpointURL.toString(), protheusApiHelper.getToken(),
						protheusApiHelper.getTokenPrefix(), BudgetSemiProjectionPage.class, Map.of("page", page,
								"pageSize", pageSize, "searchOrder", "DESC", "searchKey", key == null ? "" : key),
						null, MediaType.APPLICATION_JSON));
	}

	@Override
	public void save(Budget budget) {
		BudgetSavedResponse response = restClient.post(protheusApiHelper.buildEndpoint("budgets"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), BudgetSavedResponse.class, null, null,
				BudgetToSaveJsonSerializable.of(budget), MediaType.APPLICATION_JSON);
		budget.setCode(response.getCode());
	}

	@Override
	public Optional<BudgetFullProjection> findByCode(String code) {
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("budgets/{code}"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), BudgetFullProjection.class, null,
				Map.of("code", code), MediaType.APPLICATION_JSON));

	}

	@Override
	public void update(Budget budget) {
		BudgetToUpdateDTO toUpdate = new BudgetToUpdateDTO(budget);
		restClient.put(protheusApiHelper.buildEndpoint("budgets/{id}"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), String.class, null, Map.of("id", budget.getCode()), toUpdate,
				"application/json");
	}
}
