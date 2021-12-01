package com.portal.client.service.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.portal.client.dto.BrazilianState;

@ApplicationScoped
public class BrazilianStatesSourceTruth {

	private final Map<String, BrazilianState> states = new ConcurrentHashMap<>();

	void loadCacheSource(List<BrazilianState> states) {
		this.states.putAll(states.parallelStream().collect(Collectors.toConcurrentMap(k -> k.getName(), v -> v)));
	}

	public BrazilianState getByName(String name) {
		return states.get(name);
	}

	public BrazilianState getByAcronym(String name) {
		return states.values().parallelStream().filter(s -> s.getAcronym().equals(name)).findAny().orElse(null);
	}

	public List<BrazilianState> getAll() {
		List<BrazilianState> states = new ArrayList<>(List.copyOf(this.states.values()));
		Collections.sort(states, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
		return states;
	}

	public boolean isEmpty() {
		return states.isEmpty();
	}
}
