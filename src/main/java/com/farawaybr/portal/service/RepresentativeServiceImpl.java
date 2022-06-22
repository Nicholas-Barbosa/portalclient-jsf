package com.farawaybr.portal.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.dto.RepresentativeData;
import com.farawaybr.portal.repository.RepresentativeRepository;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.security.user.InternalProtheusUser;
import com.farawaybr.portal.security.user.ProtheusUser;
import com.farawaybr.portal.security.user.ProtheusUser.FetchStatus;

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
	public ProtheusUser find() {
		if (protheusApi.getUser().getFetchStatus() == null
				|| protheusApi.getUser().getFetchStatus() == FetchStatus.NOT_FETCHED) {
			RepresentativeData data = repository.loadData();
			ProtheusUser user = (ProtheusUser) protheusApi.getUser();
			user.setCode(data.getCode());
			user.setFantasyName(data.getFantasyname());
			user.setName(data.getName());
			user.setEmail(data.getEmail());
			user.setType(data.getType());
			user.setFetchStatus(FetchStatus.FETCHED);
			switch (user.getType()) {
			case INTERNO:
				InternalProtheusUser internal = new InternalProtheusUser(user);
				protheusApi.getData().setUser(internal);
				break;

			default:
				break;
			}
		}
		return protheusApi.getUser();
	}
}
