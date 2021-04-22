package com.portal.client.rest.auth;

import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import com.portal.cdi.qualifier.OAuth2RestAuth;
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
			Class<ERROR> errorType, Map<String, Object> queryParams) {
		ServiceApi parentType = userPropertyHolder.findServiceApi(serviceApiKey);
		if (parentType instanceof OAuth2ServiceApi) {
			OAuth2ServiceApi oAuthApi = (OAuth2ServiceApi) parentType;

			Feature feature = OAuth2ClientSupport.feature(oAuthApi.getToken());

			Client client = ClientBuilder.newBuilder().connectTimeout(8, TimeUnit.SECONDS).register(feature).build();
			
			UriBuilder uriBuilder = UriBuilder.fromPath(oAuthApi.getBasePath()).path(endpoint);
			queryParams.entrySet().parallelStream().forEach(entry -> {
				uriBuilder.queryParam(entry.getKey(), entry.getValue());
			});
			
			Response rawResponse = client.target(uriBuilder.build()).request().get();
			if (rawResponse.getStatus() == 201 || rawResponse.getStatus() == 200)
				return JsonbBuilder.create().fromJson(((InputStream) rawResponse.getEntity()), responseType);
			return JsonbBuilder.create().fromJson(((InputStream) rawResponse.getEntity()), errorType);
		} else
			throw new IllegalArgumentException(
					"the ServiceApi object retrieved from the key,its not type of OAuth2AuthenticatedRestClient or covariant!");

	}

	@Override
	public <RQ, RP> RP login(String path, RQ requestBody, MediaType mediaRequestBody, Class<RP> responseType,
			Object... queryParams) {
		Client client = ClientBuilder.newBuilder().readTimeout(5, TimeUnit.SECONDS).connectTimeout(8, TimeUnit.SECONDS)
				.build();

		return client.target(MessageFormat.format(path, queryParams)).request()
				.post(requestBody != null ? Entity.entity(requestBody, mediaRequestBody) : null, responseType);
	}

}
