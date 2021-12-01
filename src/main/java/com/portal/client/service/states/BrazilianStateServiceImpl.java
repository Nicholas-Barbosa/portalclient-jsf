package com.portal.client.service.states;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.RestClient;
import com.portal.client.dto.BrazilianState;

@ApplicationScoped
public class BrazilianStateServiceImpl implements BrazilianStateService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2291733930113912143L;

	private final RestClient restClient;

	@Inject
	public BrazilianStateServiceImpl(RestClient restClient) {
		super();
		this.restClient = restClient;
	}

	@Override
	@StatesCache
	public List<BrazilianState> findAll() {
		List<BrazilianState> states = Arrays
				.asList(this.restClient.get("https://servicodados.ibge.gov.br/api/v1/localidades/estados",
						BrazilianState[].class, null, null, MediaType.APPLICATION_JSON));
		Collections.sort(states, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
		return states;
	}

}
