package com.farawaybr.portal.websocket;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

@SessionScoped
public class ApplicationPusherMessagesImpl implements ApplicationPusherMessages, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	@Push
	private PushContext messageChannel;

	@Override
	public void onMessage(@Observes MessageEvent event) {
		// TODO Auto-generated method stub
		System.out.println("onMessage!!");
		messageChannel.send(messageChannel);
	}

}
