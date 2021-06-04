package com.portal.repository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.FinancialTitlePageDTO;

public class FinancialTitleRepositoryImpl implements FinancialTitleRepository {

	@Inject
	@OAuth2RestAuth
	private AuthenticatedRestClient restClient;

	@Override
	public FinancialTitlePageDTO find(int page, int pageSize) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		return restClient.get("ORCAMENTO_GAUSS", "titles", FinancialTitlePageDTO.class, queryParams, null,
				MediaType.APPLICATION_JSON);
	}

}
