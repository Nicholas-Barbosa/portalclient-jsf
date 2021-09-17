package com.portal.client.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.repository.APIHelper;
import com.portal.client.repository.RepresentativeRepository;
import com.portal.client.security.user.User;

@ApplicationScoped
public class RepresentativeServiceImpl implements RepresentativeService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8453047923566208460L;

	@Inject
	private APIHelper orcamentoApiHelper;

	@Inject
	private RepresentativeRepository repository;

	@Override
	public User getAdditionalData() {
		if (!orcamentoApiHelper.isUserDataComplete()) {
			repository.getAdditionalData();
		}
		return orcamentoApiHelper.getUser();
	}

}
