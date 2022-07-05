package com.farawaybr.portal.security.api;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * All ServerAPI which the current session client has been authenticated.
 * 
 * @author nicholas-barbosa
 *
 */
@ApplicationScoped
public class APIsManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6271296356737609480L;

	private final Map<String, ProtheusApiData> authenticatedServices = new ConcurrentHashMap<>();

	@Inject
	private HttpSession httpSession;

	/**
	 * Register this service to the hash table.
	 * 
	 * @param key
	 * @param service
	 */
	public void registerAuthenticatedService(String key, ProtheusApiData service) {
		this.authenticatedServices.putIfAbsent(httpSession.getId() + "-" + key, service);
	}

	/**
	 * remove this service from hash table.
	 * 
	 * @param key
	 */
	public void unRegisterAuthenticatedService(String key) {
		this.authenticatedServices.remove(httpSession.getId() + "-" + key);
	}

	public void unRegisterAuthenticatedService(@Observes HttpSession key) {
		this.authenticatedServices.remove(httpSession.getId() + "-" + key);
	}
	
	/**
	 * Get the service by the key.
	 * 
	 * @param key
	 * @return
	 */
	public ProtheusApiData getAPI(String key) {
		return this.authenticatedServices.get(httpSession.getId() + "-" + key);
	}

	public boolean containsService(String key) {
		return authenticatedServices.containsKey(httpSession.getId() + "-" + key);
	}

	public boolean isAuthenticated() {
		return !authenticatedServices.isEmpty();
	}

	public String buildEndpoint(String serverKey, String endpoint) {
		return new StringBuilder(getAPI(serverKey).getBaseUrl()).append("/" + endpoint).toString();
	}

	public String buildEndpoint(ProtheusApiData serverAPI, String endpoint) {
		return new StringBuilder(serverAPI.getBaseUrl()).append("/" + endpoint).toString();
	}

	public void onSessionDestroy(@Destroyed(SessionScoped.class) HttpSession session) {
		System.out.println("Session " + session.getId() + " destroyed");
	}
}
