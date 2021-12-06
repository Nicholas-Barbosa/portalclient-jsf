package com.portal.client.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.RepresentativeData;
import com.portal.client.repository.RepresentativeRepository;
import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.security.user.InternalRepresentativeUser;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.security.user.RepresentativeUser.FetchStatus;

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
	public RepresentativeUser find() {
		if (protheusApi.getUser().getFetchStatus() == null
				|| protheusApi.getUser().getFetchStatus() == FetchStatus.NOT_FETCHED) {
			RepresentativeData data = repository.find();
			RepresentativeUser user = (RepresentativeUser) protheusApi.getUser();
			user.setCode(data.getCode());
			user.setFantasyName(data.getFantasyname());
			user.setName(data.getName());
			user.setEmail(data.getEmail());
			user.setType(data.getType());
			user.setFetchStatus(FetchStatus.FETCHED);
			switch (user.getType()) {
			case INTERNO:
				InternalRepresentativeUser internal = new InternalRepresentativeUser(user);
				protheusApi.getData().setUser(internal);
				break;

			default:
				break;
			}
		}
		return protheusApi.getUser();
	}
}
