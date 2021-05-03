package com.portal.client.rest.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.providers.OAuth2Support;
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
	public <T> T getForEntity(String uri, Class<T> responseType, Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, ERROR> T getForEntity(String serviceApiKey, String endpoint, Class<T> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, MediaType media)
			throws ProcessingException, SocketTimeoutException, ConnectException, IllegalArgumentException,
			TimeoutException {
		ServiceApi parentType = userPropertyHolder.findServiceApi(serviceApiKey);

		if (parentType instanceof OAuth2ServiceApi) {
			try {
				OAuth2ServiceApi oAuthApi = (OAuth2ServiceApi) parentType;

				OAuth2Support oAuth2Provider = new OAuth2Support(oAuthApi.getToken());
				Client client = getClientFollowingMediaType(media);
				WebTarget resource = client.target(oAuthApi.getBasePath()).path(endpoint).register(oAuth2Provider);
				if (pathParams != null) {
					resource = resource.resolveTemplatesFromEncoded(pathParams);
				}
				if (queryParams != null) {
					Set<String> paramsInSet = queryParams.keySet();
					for (String st : paramsInSet) {
						try {
							resource = resource.queryParam(st,
									URLEncoder.encode(queryParams.get(st).toString(), "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				Response rawResponse = resource.request().accept(MediaType.APPLICATION_JSON).get();
				
				T t = rawResponse.readEntity(responseType);
				client.close();
				return t;
			} catch (ProcessingException e) {
				handleProcessingException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} else
			throw new IllegalArgumentException(
					"the ServiceApi object retrieved from the key,its not type of OAuth2AuthenticatedRestClient or covariant!");

	}

	@Override
	public <T, E> T doPost(String uri, Class<T> responseType, Map<String, Object> queryParams,
			Map<String, Object> pathParams, E requestBody, MediaType typeForRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
