package com.portal.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class UserPropertyHolder {

	private String name;
	private final Map<String, ServiceApi> authenticatedServices = new ConcurrentHashMap<>();
	
	
}
