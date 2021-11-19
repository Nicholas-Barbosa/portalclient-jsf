package com.portal.client.security.api.helper;

import com.portal.client.security.api.ApiData;
import com.portal.client.security.user.User;

public interface APIHelper {

	String getToken();

	String getTokenPrefix();

	String buildEndpoint(String endpoint);

	String getBaseUrl();
	
	ApiData getSourceAPI();

	String getSourceAPIKey();
	
	User getUser();
	
	boolean isUserDataComplete();
}
