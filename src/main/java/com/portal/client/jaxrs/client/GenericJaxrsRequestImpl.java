package com.portal.client.jaxrs.client;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.portal.client.jaxrs.client.providers.filter.TokenHeaderSupport;
import com.portal.client.jaxrs.client.providers.filter.WebApplicationExceptionExceptionLauncherFilter;
import com.portal.client.jaxrs.client.providers.message.reader.JsonMessageReader;
import com.portal.client.jaxrs.client.providers.message.writer.JsonMessageWriter;

@ApplicationScoped
public class GenericJaxrsRequestImpl implements Serializable, GenericJaxrsRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2408524350800866807L;

	@Override
	public <RESP, RQS> RESP request(String uri, String token, String tokenPrefix, Class<RESP> responseType,
			Map<String, Object> queryParams, Map<String, Object> pathParams, RQS requestBody, String mediaType,
			HttpVerb httpVerb) {
		Client client = null;
		try {
			client = getClient(token, tokenPrefix, mediaType);
			WebTarget resource = getWebTarget(client, uri, queryParams, pathParams);
			Entity<RQS> entityRequest = requestBody != null ? Entity.entity(requestBody, mediaType)
					: Entity.entity(null, mediaType);

			return resource.request().accept(mediaType).put(entityRequest, responseType);
		} finally {
			client.close();
		}
	}

	@Override
	public Client getClient(String token, String tokenPrefix, String mediaType) {
		Client client = null;
		client = getClientFollowingMediaType(mediaType);
		if (token != null && tokenPrefix != null) {
			TokenHeaderSupport tokenHeaderSupport = new TokenHeaderSupport(token, tokenPrefix);
			client = client.register(tokenHeaderSupport);
		}
		return client;
	}

	@Override
	public Client getClientFollowingMediaType(String media) {
		Client client = media.equals("application/json")
				? ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.SECONDS).build()
						.register(JsonMessageReader.class).register(JsonMessageWriter.class)
				: null;
		client.register(WebApplicationExceptionExceptionLauncherFilter.class);
		
		return client;
	}

	@Override
	public WebTarget getWebTarget(Client client, String url, Map<String, Object> queryParams,
			Map<String, Object> pathParams) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public <T>void request(Function<Client, T>request){
		request.apply(null);
	}
}
