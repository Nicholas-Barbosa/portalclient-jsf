package com.farawaybr.portal.jsf.controller;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.session.listener.DestroySessionEvent;

@ApplicationScoped
public class ConnectionSessionMonitorWSPushBean {

	@Inject
	@Push
	private PushContext connectionMonitorChannel;

	public void onNewConnection(@Priority(1) @ObservesAsync ConnectionSession cnn) {
		connectionMonitorChannel.send("new connection!");
	}

	public void onNewConnection(@ObservesAsync @Priority(1) DestroySessionEvent cnn) {
		connectionMonitorChannel.send("close connection!");
	}
}
