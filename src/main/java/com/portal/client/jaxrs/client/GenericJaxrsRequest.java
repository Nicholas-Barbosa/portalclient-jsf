package com.portal.client.jaxrs.client;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public interface GenericJaxrsRequest {

	<RESP, RQS> RESP request(String uri, String token, String tokenPrefix, Class<RESP> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, RQS requestBody, String mediaType,
			HttpVerb httpVerb);

	Client getClient(String token, String tokenPrefix, String mediaType);

	Client getClientFollowingMediaType(String media);

	WebTarget getWebTarget(Client client, String url, Map<String, Object> queryParams, Map<String, Object> pathParams);
}
