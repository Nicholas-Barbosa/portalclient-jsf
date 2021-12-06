package com.portal.client.security.api.helper;

import com.portal.client.security.api.ApiData;
import com.portal.client.security.user.RepresentativeUser;

public interface APIHelper {

	String getToken();

	String getTokenPrefix();

	String buildEndpoint(String endpoint);

	String getBaseUrl();

	ApiData getData();

	String getKey();

	RepresentativeUser getUser();

}
