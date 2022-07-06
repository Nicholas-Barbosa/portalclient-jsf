package com.farawaybr.portal.repository;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.session.listener.DestroySessionEvent;

@ApplicationScoped
public class InMemoryActiveSessionsRepository implements ActiveSessionsRepository {

	private final Set<ConnectionSession> connections = new ConcurrentSkipListSet<>();

	@Override
	public void persist(@ObservesAsync ConnectionSession connection) {
		// TODO Auto-generated method stub
		connections.add(connection);
	}

	@Override
	public void remove(ConnectionSession connection) {
		// TODO Auto-generated method stub
		connections.remove(connection);
	}

	@Override
	public Set<ConnectionSession> findAll() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableSet(connections);
	}

	public void onSessionDestroyEvent(@Observes DestroySessionEvent event) {
		connections.removeIf(c -> c.getId().equals(event.getSession().getId()));
	}
}
