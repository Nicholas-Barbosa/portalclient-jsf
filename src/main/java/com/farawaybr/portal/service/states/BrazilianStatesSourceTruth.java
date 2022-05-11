package com.farawaybr.portal.service.states;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.farawaybr.portal.dto.BrazilianState;

@ApplicationScoped
public class BrazilianStatesSourceTruth {

	private final List<BrazilianState> states = new CopyOnWriteArrayList<>();

	@PreDestroy
	public void preDestroy() {
//		OutputStream out = new BufferedOutputStream(new FileOutputStream(""));
//		new FileInputStream(null)
	}

	void setStates(List<BrazilianState> states) {
		this.states.addAll(states);
		Collections.sort(states, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
	}

	public List<BrazilianState> getStates() {
		return Collections.unmodifiableList(states);
	}

	public boolean isEmpty() {
		return states.isEmpty();
	}
}
