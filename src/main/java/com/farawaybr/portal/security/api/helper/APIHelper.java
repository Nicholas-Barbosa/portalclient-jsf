package com.farawaybr.portal.security.api.helper;

import com.farawaybr.portal.security.api.ProtheusApiData;
import com.farawaybr.portal.security.user.RepresentativeUser;

public interface APIHelper {

	String getToken();

	String getTokenPrefix();

	String buildEndpoint(String endpoint);

	String getBaseUrl();

	ProtheusApiData getData();

	String getKey();

	RepresentativeUser getUser();

}
