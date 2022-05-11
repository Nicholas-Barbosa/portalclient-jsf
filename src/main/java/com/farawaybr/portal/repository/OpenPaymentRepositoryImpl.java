package com.farawaybr.portal.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.OpenPaymentsPage;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.APIHelper;

@ApplicationScoped
public class OpenPaymentRepositoryImpl extends RepositoryInterceptors implements OpenPaymentRepository {

	private RestClient restClient;
	private APIHelper protheusApiHelper;

	@Inject
	public OpenPaymentRepositoryImpl(RestClient restClient, APIHelper protheusApiHelper) {
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
		return Optional
				.of(restClient.get(protheusApiHelper.buildEndpoint("titles"), OpenPaymentsPage.class, queryParams, null,
						MediaType.APPLICATION_JSON, Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())));

	}

	@Override
	public Optional<OpenPaymentsPage> findByCustomerCodeStore(int page, int pageSize, String code, String store) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchOrder", "DESC");

		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("titles/{code}/loja/{store}"),
				OpenPaymentsPage.class, queryParams, Map.of("code", code, "store", store), MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())));

	}

}
