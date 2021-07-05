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
import com.portal.java.pojo.Cep;
import com.portal.java.resources.ResourcesReader;

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
	private ResourcesReader resourcesReader;

	@Override
	public Optional<Cep> find(String cep)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		// TODO Auto-generated method stub
		try {
			return Optional.of(restClient.get(resourcesReader.getProperty("cep_api_url"), Cep.class, null,
					Map.of("cep", cep), MediaType.APPLICATION_JSON));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

}
