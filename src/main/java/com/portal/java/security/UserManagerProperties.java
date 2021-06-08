package com.portal.java.security;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.SessionScoped;

import com.portal.java.security.api.ExternalApiResource;

@SessionScoped
public class UserManagerProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6271296356737609480L;

	private String name;
	private final Map<String, ExternalApiResource> authenticatedServices = new ConcurrentHashMap<>();

	
	/**
	 * Register this service to the hash table.
	 * 
	 * @param key
	 * @param service
	 */
	public void registerAuthenticatedService(String key, ExternalApiResource service) {
		this.authenticatedServices.putIfAbsent(key, service);
	}

	/**
	 * remove this service from hash table.
	 * 
	 * @param key
	 */
	public void unRegisterAuthenticatedService(String key) {
		this.authenticatedServices.remove(key);
	}

	/**
	 * Get the service by the key.
	 * 
	 * @param key
	 * @return
	 */
	public ExternalApiResource findServiceApi(String key) {
		return this.authenticatedServices.get(key);
	}

	public boolean containsService(String key) {
		return authenticatedServices.containsKey(key);
	}

	/**
	 * Set the global name for the session user.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the name of global user.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public boolean isAuthenticated() {
		return !authenticatedServices.isEmpty();
	}
}
