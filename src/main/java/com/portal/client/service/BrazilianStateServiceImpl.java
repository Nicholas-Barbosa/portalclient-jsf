package com.portal.client.service;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;

import com.portal.client.cdi.qualifier.Simple;
import com.portal.client.client.rest.RestClient;
import com.portal.client.dto.BrazilianState;

@ApplicationScoped
@Named
public class BrazilianStateServiceImpl implements BrazilianStateService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2291733930113912143L;

	private final Map<String, BrazilianState> states = new ConcurrentHashMap<>();

	private final RestClient restClient;

	@Inject
	public BrazilianStateServiceImpl(@Simple RestClient restClient) {
		super();
		this.restClient = restClient;
		loadStates();
	}

	@Override
	public BrazilianState findByName(String name) {
		return states.get(name);
	}

	@Override
	public BrazilianState findByAcronym(String name) {
		return states.values().parallelStream().filter(s -> s.getAcronym().equals(name)).findAny().orElse(null);
	}

	@Override
	public List<BrazilianState> findAll() {
		List<BrazilianState> states = new ArrayList<>(List.copyOf(this.states.values()));
		Collections.sort(states, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
		return states;
	}

	private void loadStates() {
		try {
			List<BrazilianState> states = List
					.of(this.restClient.get("https://servicodados.ibge.gov.br/api/v1/localidades/estados",
							BrazilianState[].class, null, null, MediaType.APPLICATION_JSON));
			this.states.putAll(states.parallelStream().collect(Collectors.toConcurrentMap(k -> k.getName(), v -> v)));
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
