package com.portal.client.jaxrs.client;

import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.portal.client.jaxrs.client.aop.IllegalResponsePointCutJoinPoint;
import com.portal.client.jaxrs.client.providers.filter.TokenHeaderSupport;

@ApplicationScoped
@IllegalResponsePointCutJoinPoint
public class DeferredClientRequestImpl implements Serializable, DeferredClientRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8420422881382830155L;

	@Inject
	private JaxrsClientSource clientDS;

	@Override
	public <T> T request(BiFunction<Client, WebTarget, T> lambda, WebTargetData wtd) {
		Client client = clientDS.getClient();
		return lambda.apply(client, getWebTarget(client, wtd));
	}

	@Override
	public <T> Future<T> requestAsync(BiFunction<Client, WebTarget, Future<T>> lambda, WebTargetData wtd) {
		// TODO Auto-generated method stub
		Client client = clientDS.getClient();
		return lambda.apply(client, getWebTarget(client, wtd));
	}

	private WebTarget getWebTarget(Client client, WebTargetData wtd) {
		WebTarget resource = client.target(wtd.getUrl());
		if (wtd.getPathParams() != null && !wtd.getPathParams().isEmpty()) {
			resource = resource.resolveTemplatesFromEncoded(wtd.getPathParams());
		}
		if (wtd.getQueryParams() != null && !wtd.getQueryParams().isEmpty()) {
			for (String key : wtd.getQueryParams().keySet()) {
				resource = resource.queryParam(key, wtd.getQueryParams().get(key));
			}
		}
		return resource.register(new TokenHeaderSupport(wtd.getToken(), wtd.getPrefixToken()))
				;
	}
}
