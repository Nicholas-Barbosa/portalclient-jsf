package com.portal.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class UserPropertyHolder {

	private String name;
	private final Map<String, ServiceApi> authenticatedServices = new ConcurrentHashMap<>();

	public void registerAuthenticatedService(String key, ServiceApi service) {
		this.authenticatedServices.putIfAbsent(key, service);
	}

	public void unRegisterAuthenticatedService(String key) {
		this.authenticatedServices.remove(key);
	}

	public ServiceApi findServiceApi(String key) {
		return this.authenticatedServices.get(key);
	}
}
