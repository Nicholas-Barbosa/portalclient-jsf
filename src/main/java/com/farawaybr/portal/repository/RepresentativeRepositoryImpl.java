package com.farawaybr.portal.repository;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.cdi.aop.annotations.IllegalResponsePointCutJoinPoint;
import com.farawaybr.portal.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.farawaybr.portal.dto.RepresentativeData;
import com.farawaybr.portal.dto.WrapperRepresentativeData;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.APIHelper;

@ApplicationScoped
@IllegalResponsePointCutJoinPoint
@NotAuthorizedJoinPointCut
public class RepresentativeRepositoryImpl implements RepresentativeRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735726969594990824L;

	@Inject
	private RestClient restClient;

	@Inject
	private APIHelper protheusApiHelper;

	@Override
	public RepresentativeData loadData() {
		// TODO Auto-generated method stub
		return restClient
				.get(protheusApiHelper.buildEndpoint("representative"), WrapperRepresentativeData.class, null, null,
						MediaType.APPLICATION_JSON, Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken()))
				.getData();

	}

}
