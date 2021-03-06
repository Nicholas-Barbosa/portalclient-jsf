package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.primefaces.PrimeFaces;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.http.session.DestroySessionEvent;
import com.farawaybr.portal.repository.ConnectionSessionRepository;
import com.farawaybr.portal.service.jsonb.JsonbService;
import com.farawaybr.portal.websocket.service.ChatService;

@ApplicationScoped
@Named
public class ConnectionsSessionsMonitorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ConnectionSessionRepository repository;
	@Inject
	private ChatService chatService;
	@Inject
	private HttpSession httpSession;
	private Set<ConnectionSession> activeConnections;
	@Inject
	private JsonbService jsonbService;

	@PostConstruct
	public void postDI() {
		this.activeConnections = repository.findAll();
	}

	public void onNewConnection(@ObservesAsync ConnectionSession cnn) {
		this.activeConnections.add(cnn);
	}

	public void onCloseConnection(@ObservesAsync @DestroySessionEvent HttpSessionEvent event) {
		this.activeConnections = repository.findAll();
	}

	public void toJson(ConnectionSession connectionSession) {
		PrimeFaces.current().ajax().addCallbackParam("ipInfo", jsonbService.toJson(connectionSession.getIp()));
	}

	public void startChat(ConnectionSession connectionSession) {
		String conversation = chatService.addConversation(httpSession.getId(), connectionSession.getId());
		PrimeFaces.current().ajax().addCallbackParam("conversation_id", conversation);
	}

	public Set<ConnectionSession> getActiveConnections() {
		return activeConnections;
	}
}
