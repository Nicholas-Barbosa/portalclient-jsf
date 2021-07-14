package com.portal.java.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.java.cdi.qualifier.OAuth2RestAuth;
import com.portal.java.client.rest.auth.AuthenticatedRestClient;

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

}
