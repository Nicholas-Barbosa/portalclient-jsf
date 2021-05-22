package com.portal.client.rest.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.providers.filter.OAuth2Support;
import com.portal.exception.IllegalResponseStatusException;
import com.portal.security.UserPropertyHolder;
import com.portal.security.api.OAuth2ServiceApi;
import com.portal.security.api.ServiceApi;

@OAuth2RestAuth
public class OAuth2AuthenticatedRestClient implements AuthenticatedRestClient, Serializable {

	private static final long serialVersionUID = 2516211534967007393L;

	private final UserPropertyHolder userPropertyHolder;

	public OAuth2AuthenticatedRestClient() {
		this(null);
	}

	@Inject
	public OAuth2AuthenticatedRestClient(UserPropertyHolder userPropertyHolder) {
		super();
		this.userPropertyHolder = userPropertyHolder;

	}

	@Override
	public <T> T getForEntity(String serviceApiKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, MediaType media)
			throws ProcessingException {

		OAuth2ServiceApi oAuthApi = getService(serviceApiKey);

		Client client = null;
		try {
			OAuth2Support oAuth2Provider = new OAuth2Support(oAuthApi.getToken());
			client = getClientFollowingMediaType(media);
			WebTarget resource = client.target(oAuthApi.getBasePath()).path(endpoint).register(oAuth2Provider);
			if (pathParams != null) {
				resource = resource.resolveTemplatesFromEncoded(pathParams);
			}
			if (queryParams != null) {
				Set<String> paramsInSet = queryParams.keySet();
				for (String st : paramsInSet) {
					try {
						resource = resource.queryParam(st, URLEncoder.encode(queryParams.get(st).toString(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			Response rawResponse = resource.request().accept(media).get();
			T t = rawResponse.readEntity(responseType);
			return t;
		} catch (ProcessingException e) {
			if (e.getCause() instanceof IllegalResponseStatusException) {
				return this.getForEntity(serviceApiKey, endpoint, responseType, queryParams, pathParams, media);
			}
			depurateProcessingException(e);
			throw e;
			// handleProcessingException(e);
		} finally {
			if (client != null)
				client.close();
		}

	}

	@Override
	public <T, U> T post(String serviceApiKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, MediaType media, U requestBody) throws ProcessingException {
		Client client = null;
		OAuth2ServiceApi service = getService(serviceApiKey);
		try {
			client = getClientFollowingMediaType(media);
			OAuth2Support oAuth2Provider = new OAuth2Support(service.getToken());

			WebTarget resource = client.target(service.getBasePath()).path(endpoint).register(oAuth2Provider);

			return resource.request().accept(media).post(Entity.entity(requestBody, media), responseType);

		} catch (ProcessingException e) {
			if (e.getCause() instanceof IllegalResponseStatusException) {
				return this.post(serviceApiKey, endpoint, responseType, queryParams, pathParams, media, requestBody);

			}
			depurateProcessingException(e);
			throw e;

		} finally {
			client.close();
		}
	}

	private OAuth2ServiceApi getService(String key) {
		if (userPropertyHolder.containsService(key)) {
			ServiceApi service = userPropertyHolder.findServiceApi(key);
			if (service instanceof OAuth2ServiceApi) {
				return (OAuth2ServiceApi) service;
			} else
				throw new IllegalArgumentException(
						"the ServiceApi object retrieved from the key,its not type of OAuth2AuthenticatedRestClient or covariant!");
		} else
			throw new IllegalArgumentException("This service is not registered!");
	}
}
