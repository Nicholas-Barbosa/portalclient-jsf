package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.repository.ConnectionSessionRepository;

@ViewScoped
@Named
public class ActiveConnectionsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ConnectionSessionRepository repository;
	private Set<ConnectionSession> activeConnections;

	@PostConstruct
	public void postDI() {
		this.findConnections();
	}

	public void findConnections() {
		activeConnections = repository.findAll();
	}

	public Set<ConnectionSession> getActiveConnections() {
		return activeConnections;
	}
}
