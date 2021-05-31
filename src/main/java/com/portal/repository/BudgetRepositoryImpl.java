package com.portal.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimatedDTO;

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
	public BudgetEstimatedDTO estimate(BudgetEstimateForm form) throws ProcessingException {
		return restClient.post("ORCAMENTO_API", "estimate", BudgetEstimatedDTO.class, null, null,
				MediaType.APPLICATION_JSON, form);

	}

}
