package com.portal.client.repository;

import com.portal.client.security.api.ServerAPI;
import com.portal.client.security.user.User;

public interface APIHelper {

	String getToken();

	String getPrefixToken();

	String buildEndpoint(String endpoint);

	String getBasePath();
	
	ServerAPI getSourceAPI();

	String getSourceAPIKey();
	
	User getUser();
	
	boolean isUserDataComplete();
}
