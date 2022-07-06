package com.farawaybr.portal.security.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import com.farawaybr.portal.dto.ConnectionSession;
import com.farawaybr.portal.dto.ConnectionSessionBuilder;
import com.farawaybr.portal.dto.LoginProtheusForm;
import com.farawaybr.portal.service.UserService;

@SessionScoped
public class JsfAuthenticationFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AuthenticationService authService;
	@Inject
	private UserService userService;

	@Inject
	@Push
	private PushContext loginChannel;

	@Inject
	private Event<LoggedEvent> loggedEvent;

	@Inject
	private Event<ConnectionSession> connectionEvent;

	@Inject
	private HttpSession httpSession;

	@Inject
	private HttpServletRequest request;

	
	public void authenticate(final LoginProtheusForm form) {
		authService.authenticate(form);
		this.sendMessage(0, "Usuário autenticado!Obtendo informações...");
		userService.getInfo();
		this.sendMessage(1, "Informações obtidas. Customizando layout...");
		connectionEvent.fireAsync(ConnectionSessionBuilder.getInstance().withId(httpSession.getId())
				.withIp(request.getRemoteAddr()).withLocale(request.getLocale()).withUser(form.getUsername())
				.withUserAgent(request.getHeader("User-Agent")).withEnvironment(form.getEnvironment()).build());
		loggedEvent.fire(new LoggedEvent(form.getEnvironment()));

	}

	private void sendMessage(int state, String message) {
		JsonObjectBuilder object = Json.createObjectBuilder();
		object.add("state", state + "");
		object.add("message", message);
		loginChannel.send(object.build());
	}
}
