package com.portal.client.jaxrs.client;

import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;

import com.portal.client.jaxrs.client.aop.IllegalResponsePointCutJoinPoint;

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
	public <T> T request(Function<Client, T> lambda) {
		ClientWrapper wrapper = clientDS.getClient();
		T result = lambda.apply(wrapper.getClient());
		wrapper.setInUse(false);
		return result;
	}

	@Override
	public <T> Future<T> requestAsync(Function<Client, Future<T>> lambda) {
		// TODO Auto-generated method stub
		ClientWrapper wrapper = clientDS.getClient();
		Future<T> result = lambda.apply(wrapper.getClient());
		wrapper.setInUse(false);
		return result;
	}

}
