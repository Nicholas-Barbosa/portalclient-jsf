package com.portal.client.service;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.RestClient;
import com.nicholas.jaxrsclient.diqualifier.Simple;
import com.portal.client.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;
import com.portal.client.pojo.ZipCode;
import com.portal.client.resources.ConfigPropertyResolver;

@ApplicationScoped
public class ZipCodeServiceImpl implements ZipCodeService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7183651257515145651L;

	private RestClient restClient;

	private ConfigPropertyResolver configPropertiesResolver;

	@Inject
	public ZipCodeServiceImpl(@Simple RestClient restClient, ConfigPropertyResolver resourcesReader) {
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
					Map.of("cep", cep), MediaType.APPLICATION_JSON));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
