package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateDTO.EstimatedItem;
import com.portal.dto.BudgetEstimateForm;

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
	public BudgetEstimateDTO estimate(BudgetEstimateForm form) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, SocketException, TimeoutException {
		return restClient.post("ORCAMENTO_API", "estimate", BudgetEstimateDTO.class, null, null,
				MediaType.APPLICATION_JSON_TYPE, form);

	}

	
	public BudgetEstimateDTO recalculateEstimate(BudgetEstimateDTO form) {
		form.getEstimatedItemValues().parallelStream().forEach(EstimatedItem::recalculateTotales);
		form.reCalculateTotales();
		return form;
	}

}
