package com.portal.client.rest.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.providers.OAuth2Support;
import com.portal.client.rest.providers.SimpleClientRequestFilter;
import com.portal.client.rest.providers.message.reader.JsonObjectMessageReader;
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
	public <T, R> T postFoEntity(String uri, R requestBody, MediaType mediaRequestBody, Class<T> responseType,
			Object... queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, ERROR> Object getForEntity(String serviceApiKey, String endpoint, Class<T> responseType,
			Class<ERROR> errorType, Map<String, Object> queryParams, Map<String, Object> pathParams) {
		ServiceApi parentType = userPropertyHolder.findServiceApi(serviceApiKey);
		if (parentType instanceof OAuth2ServiceApi) {
			OAuth2ServiceApi oAuthApi = (OAuth2ServiceApi) parentType;

			OAuth2Support oAuth2Provider = new OAuth2Support(oAuthApi.getToken());
			Client client = ClientBuilder.newBuilder().connectTimeout(8, TimeUnit.SECONDS).build()
					.register(JsonObjectMessageReader.class).register(SimpleClientRequestFilter.class);

			WebTarget resource = client.target(oAuthApi.getBasePath()).path(endpoint).register(oAuth2Provider);
			if (pathParams != null) {
				resource = resource.resolveTemplatesFromEncoded(pathParams);
			}
			if (queryParams != null) {
				// I didnt use lambdas cause only final local variables can be
				// referenced,however target.queryParam return new WebTarget object.

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

			Response rawResponse = resource.request().accept(MediaType.APPLICATION_JSON).get();

			if (rawResponse.getStatus() == 201 || rawResponse.getStatus() == 200) {
				T t = rawResponse.readEntity(responseType);
				client.close();
				return t;
			}
			rawResponse.readEntity(errorType);
			client.close();

			return rawResponse.getEntity();
		} else
			throw new IllegalArgumentException(
					"the ServiceApi object retrieved from the key,its not type of OAuth2AuthenticatedRestClient or covariant!");

	}

	@Override
	public <RQ, RP> RP login(String path, RQ requestBody, MediaType mediaRequestBody, Class<RP> responseType,
			Map<String, Object> queryParams) {
		Client client = ClientBuilder.newBuilder().connectTimeout(8, TimeUnit.SECONDS).build();
		WebTarget resource = client.target(path);

		if (queryParams != null) {
			// I didnt use lambdas cause only final local variables can be
			// referenced,however target.queryParam return new WebTarget object.
			Set<String> paramsInSet = queryParams.keySet();
			for (String st : paramsInSet) {
				resource = resource.queryParam(st, queryParams.get(st));
			}
		}

		return resource.request().post(requestBody != null ? Entity.entity(requestBody, mediaRequestBody) : null,
				responseType);
	}

}
