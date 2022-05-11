package com.farawaybr.portal.service;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.pojo.ZipCode;
import com.farawaybr.portal.resources.ConfigPropertyResolver;

@ApplicationScoped
public class ZipCodeServiceImpl implements ZipCodeService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7183651257515145651L;

	private RestClient restClient;

	private ConfigPropertyResolver configPropertiesResolver;

	@Inject
	public ZipCodeServiceImpl(RestClient restClient, ConfigPropertyResolver resourcesReader) {
		super();
		this.restClient = restClient;
		this.configPropertiesResolver = resourcesReader;
	}

	@Override
	@NotFoundOptionalEmptyJoinPointCut
	public Optional<ZipCode> find(String cep) {
		// TODO Auto-generated method stub
		try {
			return Optional.of(restClient.get(configPropertiesResolver.getProperty("cep_api_url"), ZipCode.class, null,
					Map.of("cep", cep), MediaType.APPLICATION_JSON, null));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
