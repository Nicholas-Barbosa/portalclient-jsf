package com.farawaybr.portal.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

@SessionScoped
public class NotAuthorizedExceptionPusher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	@Push
	private PushContext notAuthorizedExceptionChannel;

	public void onNotAuthorizedExceptionEvent(@Observes NotAuthorizedExceptionEvent event) {
		notAuthorizedExceptionChannel.send(event);
	}
}
