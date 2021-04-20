package com.portal.security;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.SessionScoped;

import com.portal.security.api.ServiceApi;

@SessionScoped
public class UserPropertyHolder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private final Map<String, ServiceApi> authenticatedServices = new ConcurrentHashMap<>();

	/**
	 * Register this service to the hash table.
	 * 
	 * @param key
	 * @param service
	 */
	public void registerAuthenticatedService(String key, ServiceApi service) {
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
	public ServiceApi findServiceApi(String key) {
		return this.authenticatedServices.get(key);
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
