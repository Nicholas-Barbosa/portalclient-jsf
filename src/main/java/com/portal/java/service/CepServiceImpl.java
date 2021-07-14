package com.portal.java.service;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.java.cdi.qualifier.Simple;
import com.portal.java.client.rest.RestClient;
import com.portal.java.pojo.ZipCode;
import com.portal.java.resources.ConfigPropertyResolver;

@ApplicationScoped
public class CepServiceImpl implements CepService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7183651257515145651L;

	@Inject
	@Simple
	private RestClient restClient;

	@EJB
	private ConfigPropertyResolver resourcesReader;

	@Override
	public Optional<ZipCode> find(String cep)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		// TODO Auto-generated method stub
		try {
			return Optional.of(restClient.get(resourcesReader.getProperty("cep_api_url"), ZipCode.class, null,
					Map.of("cep", cep), MediaType.APPLICATION_JSON));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
