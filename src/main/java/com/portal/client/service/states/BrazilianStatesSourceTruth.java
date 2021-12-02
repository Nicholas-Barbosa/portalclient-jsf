package com.portal.client.service.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.portal.client.dto.BrazilianState;

@ApplicationScoped
public class BrazilianStatesSourceTruth {

	private final Map<String, BrazilianState> states = new ConcurrentHashMap<>();

	@PreDestroy
	public void preDestroy() {
//		OutputStream out = new BufferedOutputStream(new FileOutputStream(""));
//		new FileInputStream(null)
	}
	void loadCacheSource(List<BrazilianState> states) {
		this.states.putAll(states.parallelStream().collect(Collectors.toConcurrentMap(k -> k.getName(), v -> v)));
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
