package com.portal.java.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.java.cdi.qualifier.OAuth2RestAuth;
import com.portal.java.client.rest.auth.AuthenticatedRestClient;
import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;

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
	public BudgetEstimatedDTO estimate(BudgetEstimateForm form)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException {
		return restClient.post("ORCAMENTO_API", "estimate", BudgetEstimatedDTO.class, null, null,
				MediaType.APPLICATION_JSON, form);

	}

}
