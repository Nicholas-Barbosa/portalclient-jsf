package com.farawaybr.portal.security.api;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * All ServerAPI which the current session client has been authenticated.
 * 
 * @author nicholas-barbosa
 *
 */
@SessionScoped
public class APIsManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6271296356737609480L;

	private final Map<String, ProtheusApiData> authenticatedServices = new ConcurrentHashMap<>();

	private final Logger logger = LoggerFactory.getLogger(APIsManager.class);

	/**
	 * Register this service to the hash table.
	 * 
	 * @param key
	 * @param service
	 */
	public void registerAuthenticatedService(String key, ProtheusApiData service) {
		logger.debug("Registering server API with key " + key + " with token " + service.getToken());
		this.authenticatedServices.putIfAbsent(key, service);
	}

	/**
	 * remove this service from hash table.
	 * 
	 * @param key
	 */
	public void unRegisterAuthenticatedService(String key) {
		logger.debug("Unregistering server API with key " + key);
		this.authenticatedServices.remove(key);
	}

	/**
	 * Get the service by the key.
	 * 
	 * @param key
	 * @return
	 */
	public ProtheusApiData getAPI(String key) {
		return this.authenticatedServices.get(key);
	}

	public boolean containsService(String key) {
		return authenticatedServices.containsKey(key);
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

	
}
