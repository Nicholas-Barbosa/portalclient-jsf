package com.portal.java.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.java.cdi.qualifier.OAuth2RestAuth;
import com.portal.java.client.rest.auth.AuthenticatedRestClient;
import com.portal.java.dto.FinancialTitlePageDTO;

public class FinancialTitleRepositoryImpl implements FinancialTitleRepository {

	@Inject
	@OAuth2RestAuth
	private AuthenticatedRestClient restClient;

	@Override
	public FinancialTitlePageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		return restClient.get("ORCAMENTO_API", "titles", FinancialTitlePageDTO.class, queryParams, null,
				MediaType.APPLICATION_JSON);
	}

}
