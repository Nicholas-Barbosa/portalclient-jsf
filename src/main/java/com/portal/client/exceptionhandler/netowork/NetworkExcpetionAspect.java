package com.portal.client.exceptionhandler.netowork;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.ProcessingException;

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
		} catch (ProcessingException | SocketTimeoutException | TimeoutException e) {
			if (e instanceof ProcessingException) {
				ProcessingException p = (ProcessingException) e;
				if (p.getCause() instanceof SocketTimeoutException || p.getCause() instanceof TimeoutException)
					subject.notifyObservers((Exception) p.getCause());
				throw p.getCause();
			}
			subject.notifyObservers(e);
			throw e;
		}
	}

}
