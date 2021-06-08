package com.portal.java.client.rest.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.portal.java.cdi.qualifier.OAuth2RestAuth;
import com.portal.java.client.rest.providers.filter.OAuth2Support;
import com.portal.java.exception.IllegalResponseStatusException;
import com.portal.java.security.UserManagerProperties;
import com.portal.java.security.api.ExternalApiResource;
import com.portal.java.security.api.ExternalOAuth2ApiResource;

@OAuth2RestAuth
@ApplicationScoped
public class OAuth2AuthenticatedRestClient implements AuthenticatedRestClient, Serializable {

	private static final long serialVersionUID = 2516211534967007393L;

	private final UserManagerProperties userManagerProperties;

	@Inject
	public OAuth2AuthenticatedRestClient(UserManagerProperties userManagerProperties) {
		super();
		this.userManagerProperties = userManagerProperties;
	}

	@Override
	public <T> T get(String serviceApiKey, String endpoint, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		ExternalOAuth2ApiResource oAuthApi = getService(serviceApiKey);
		return this.get(oAuthApi, endpoint, responseType, queryParams, pathParams, media);
	}

	@Override
	public <T> Future<T> getAsync(String externalServiceKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		ExecutorService executor = null;
		try {
			executor = Executors.newSingleThreadExecutor();
			ExternalOAuth2ApiResource resource = getService(externalServiceKey);
			return executor.submit(() -> {
				return this.get(resource, endpoint, responseType, queryParams, pathParams, media);
			});
		} finally {
			executor.shutdown();
		}
	}

	@Override
	public <T, U> T post(String externalServiceKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media, U requestBody)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		Client client = null;
		ExternalOAuth2ApiResource service = getService(externalServiceKey);
		try {
			client = getClientFollowingMediaType(media);
			OAuth2Support oAuth2Provider = new OAuth2Support(service.getToken());

			WebTarget resource = client.target(service.getBasePath()).path(endpoint).register(oAuth2Provider);

			return resource.request().accept(media).post(Entity.entity(requestBody, media), responseType);

		} catch (ProcessingException e) {
			if (e.getCause() instanceof IllegalResponseStatusException) {
				return this.post(externalServiceKey, endpoint, responseType, queryParams, pathParams, media,
						requestBody);

			}
			checkProcessingException(e);
			throw e;

		} finally {
			client.close();
		}
	}

	private ExternalOAuth2ApiResource getService(String key) {
		if (userManagerProperties.containsService(key)) {
			ExternalApiResource service = userManagerProperties.findServiceApi(key);
			if (service instanceof ExternalOAuth2ApiResource) {
				return (ExternalOAuth2ApiResource) service;
			} else
				throw new IllegalArgumentException(
						"the ServiceApi object retrieved from the key,its not type of OAuth2AuthenticatedRestClient or covariant!");
		} else
			throw new IllegalArgumentException("This service is not registered!");
	}

	private <T> T get(ExternalOAuth2ApiResource apiResource, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, String media)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		Client client = null;
		try {
			OAuth2Support oAuth2Provider = new OAuth2Support(apiResource.getToken());
			client = getClientFollowingMediaType(media);
			WebTarget resource = client.target(apiResource.getBasePath()).path(endpoint).register(oAuth2Provider);
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
				return this.get(apiResource, endpoint, responseType, queryParams, pathParams, media);
			}
			checkProcessingException(e);
			throw e;
			// handleProcessingException(e);
		} finally {
			if (client != null)
				client.close();
		}

	}
}
