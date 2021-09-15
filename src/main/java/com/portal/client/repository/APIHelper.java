package com.portal.client.repository;

import com.portal.client.security.api.ServerAPI;

public interface APIHelper {

	String getToken();

	String getPrefixToken();

	String buildEndpoint(String endpoint);

	String getBasePath();
	
	ServerAPI getSourceAPI();

	String getSourceAPIKey();
}
