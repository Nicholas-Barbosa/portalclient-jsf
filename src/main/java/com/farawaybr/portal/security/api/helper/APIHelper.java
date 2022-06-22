package com.farawaybr.portal.security.api.helper;

import com.farawaybr.portal.security.api.ProtheusApiData;
import com.farawaybr.portal.security.user.ProtheusUser;

public interface APIHelper {

	String getToken();

	String getTokenPrefix();

	String buildEndpoint(String endpoint);

	String getBaseUrl();

	ProtheusApiData getData();

	String getKey();

	ProtheusUser getUser();

}
