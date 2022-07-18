package com.farawaybr.portal.jsf.controller;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.servlet.http.HttpSessionEvent;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.http.session.DestroySessionEvent;

@ApplicationScoped
public class ConnectionSessionMonitorWSPushBean {

	@Inject
	@Push
	private PushContext connectionMonitorChannel;

	public void onNewConnection(@Priority(1) @ObservesAsync ConnectionSession cnn) {
		connectionMonitorChannel.send("new connection!");
	}

	public void onClosedConnection(@ObservesAsync @Priority(3) @DestroySessionEvent HttpSessionEvent cnn) {
		connectionMonitorChannel.send("close connection!");
	}
}
