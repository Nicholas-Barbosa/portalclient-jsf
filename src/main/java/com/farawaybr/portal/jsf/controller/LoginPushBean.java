package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;
import com.farawaybr.portal.security.auth.LoggedEvent;
import com.farawaybr.portal.websocket.IntraNotification;

@ApplicationScoped
@Named
public class LoginPushBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@Push
	private PushContext loginChannel;
	@Inject
	private Event<LoggedEvent> authenticatedEvent;

	public void sendLoginMessage() {
		loginChannel.send("Nova mensagem!" + LocalDateTime.now());
	}

	public void sendObserver() {
		authenticatedEvent.fire(new LoggedEvent(ProtheusApiEnviroment.CDG));
	}
}
