package com.portal.client.security.api.helper;

import com.portal.client.security.api.ServerAPI;
import com.portal.client.security.user.User;

public interface APIHelper {

	String getToken();

	String getTokenPrefix();

	String buildEndpoint(String endpoint);

	String getBaseUrl();
	
	ServerAPI getSourceAPI();

	String getSourceAPIKey();
	
	User getUser();
	
	boolean isUserDataComplete();
}
