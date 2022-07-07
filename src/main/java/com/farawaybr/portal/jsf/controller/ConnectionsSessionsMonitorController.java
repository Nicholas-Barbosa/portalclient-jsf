package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.repository.ConnectionSessionRepository;
import com.farawaybr.portal.session.listener.DestroySessionEvent;

@ViewScoped
@Named
public class ConnectionsSessionsMonitorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ConnectionSessionRepository repository;
	private Set<ConnectionSession> activeConnections;

	@PostConstruct
	public void postDI() {
		this.activeConnections = repository.findAll();

	}

	public void onNewConnection(@ObservesAsync ConnectionSession cnn) {
		this.activeConnections.add(cnn);
	}

	public void onCloseConnection(@ObservesAsync DestroySessionEvent event) {
		this.activeConnections.removeIf(c -> c.getId().equals(event.getSession().getId()));
	}

	public Set<ConnectionSession> getActiveConnections() {
		return activeConnections;
	}
}
