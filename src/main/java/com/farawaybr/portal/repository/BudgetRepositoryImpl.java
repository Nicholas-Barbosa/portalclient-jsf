package com.farawaybr.portal.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.BudgetFullProjection;
import com.farawaybr.portal.dto.BudgetSavedResponse;
import com.farawaybr.portal.dto.BudgetSemiProjection;
import com.farawaybr.portal.dto.BudgetSemiProjectionPage;
import com.farawaybr.portal.dto.BudgetToSaveJsonSerializable;
import com.farawaybr.portal.dto.BudgetToUpdateDTO;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Page;

@ApplicationScoped
public class BudgetRepositoryImpl extends RepositoryInterceptors implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final RestClient restClient;
	private APIHelper protheusApiHelper;

	public BudgetRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public BudgetRepositoryImpl(RestClient restClient, APIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	public Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize, String key) {
		StringBuilder endpointURL = new StringBuilder(protheusApiHelper.getBaseUrl());
		endpointURL.append("/budgets");
		return Optional
				.of(restClient.get(endpointURL.toString(), BudgetSemiProjectionPage.class,
						Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC", "searchKey",
								key == null ? "" : key),
						null, MediaType.APPLICATION_JSON,
						Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken())));
	}

	@Override
	public void save(Budget budget) {
		BudgetSavedResponse response = restClient.post(protheusApiHelper.buildEndpoint("budgets"),
				BudgetSavedResponse.class, null, null, BudgetToSaveJsonSerializable.of(budget),
				MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));

		budget.setCode(response.getCode());
	}

	@Override
	public Optional<BudgetFullProjection> findByCode(String code) {
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("budgets/{code}"), BudgetFullProjection.class,
				null, Map.of("code", code), MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken())));

	}

	@Override
	public void update(Budget budget) {
		BudgetToUpdateDTO toUpdate = new BudgetToUpdateDTO(budget);
		restClient.put(protheusApiHelper.buildEndpoint("budgets/{id}"), String.class, null,
				Map.of("id", budget.getCode()), toUpdate, "application/json",
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));
	}
}
