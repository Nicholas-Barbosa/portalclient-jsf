package com.portal.client.repository;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.cdi.aop.annotations.IllegalResponsePointCutJoinPoint;
import com.portal.client.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.portal.client.dto.RepresentativeData;
import com.portal.client.dto.WrapperRepresentativeData;
import com.portal.client.security.api.helper.APIHelper;

@ApplicationScoped
@IllegalResponsePointCutJoinPoint
@NotAuthorizedJoinPointCut
public class RepresentativeRepositoryImpl implements RepresentativeRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735726969594990824L;

	@Inject
	private TokenedRestClient restClient;

	@Inject
	private APIHelper protheusApiHelper;

	@Override
	public RepresentativeData loadData() {
		// TODO Auto-generated method stub
		System.out.println("Find representative data");
		return restClient.get(protheusApiHelper.buildEndpoint("representative"), protheusApiHelper.getToken(),
				protheusApiHelper.getTokenPrefix(), WrapperRepresentativeData.class, null, null,
				MediaType.APPLICATION_JSON).getData();

	}

}
