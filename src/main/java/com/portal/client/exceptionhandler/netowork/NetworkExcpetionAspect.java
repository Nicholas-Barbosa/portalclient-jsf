package com.portal.client.exceptionhandler.netowork;

import java.io.Serializable;
import java.net.SocketTimeoutException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@NetworkExceptionJoinPointCut
public class NetworkExcpetionAspect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9103297624295760292L;
	@Inject
	private NetworkExceptionSubject subject;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext joinpoint) throws Throwable {
		try {
			return joinpoint.proceed();
		} catch (SocketTimeoutException e) {
			System.out.println(e);
			throw e;
		}
	}

}
