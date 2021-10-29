package com.portal.client.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.repository.RepresentativeRepository;
import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.security.user.InternalRepresentativeUser;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.security.user.User;

@ApplicationScoped
public class RepresentativeServiceImpl implements RepresentativeService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8453047923566208460L;

	@Inject
	private APIHelper protheusApi;

	@Inject
	private RepresentativeRepository repository;

	@Override
	public User getAdditionalData() {
		if (!protheusApi.isUserDataComplete()) {
			repository.getAdditionalData();
		}
		RepresentativeUser user = (RepresentativeUser) protheusApi.getUser();
		switch (user.getType()) {
		case INTERNO:
			InternalRepresentativeUser interanal = new InternalRepresentativeUser(user);
			protheusApi.getSourceAPI().setUser(interanal);
			break;
		default:
			break;
		}
		return protheusApi.getUser();
	}

}
