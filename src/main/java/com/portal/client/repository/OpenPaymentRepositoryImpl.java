package com.portal.client.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.dto.OpenPaymentsPage;
import com.portal.client.security.api.helper.APIHelper;

public class OpenPaymentRepositoryImpl extends OptionalEmptyRepository implements OpenPaymentRepository {

	private TokenedRestClient restClient;
	private APIHelper protheusApiHelper;
	
	@Inject
	public OpenPaymentRepositoryImpl(TokenedRestClient restClient, APIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	public Optional<OpenPaymentsPage> find(int page, int pageSize) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("titles"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), OpenPaymentsPage.class, queryParams, null, MediaType.APPLICATION_JSON));

	}

	@Override
	public Optional<OpenPaymentsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");

		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("titles/{code}/loja/{store}"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), OpenPaymentsPage.class, queryParams,
				Map.of("code", code, "store", store), MediaType.APPLICATION_JSON));

	}

}
