package com.farawaybr.portal.repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.servlet.http.HttpSessionEvent;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.http.session.DestroySessionEvent;

@ApplicationScoped
public class InMemoryConnectionSessions implements ConnectionSessionRepository {

	private final Map<String, ConnectionSession> connections = new ConcurrentHashMap<>();

	@Override
	public void persist(@Priority(0) @ObservesAsync ConnectionSession connection) {
		// TODO Auto-generated method stub
		connections.put(connection.getId(), connection);
	}

	@Override
	public void remove(ConnectionSession connection) {
		// TODO Auto-generated method stub
		connections.remove(connection.getId());
	}

	@Override
	public Set<ConnectionSession> findAll() {
		// TODO Auto-generated method stub
		return new ConcurrentSkipListSet<>(connections.values());
	}

	public void onSessionDestroyEvent(@ObservesAsync @DestroySessionEvent @Priority(2) HttpSessionEvent event) {
		connections.remove(event.getSession().getId());

	}

	@Override
	public boolean isActive(String id) {
		// TODO Auto-generated method stub
		return connections.containsKey(id);
	}
}