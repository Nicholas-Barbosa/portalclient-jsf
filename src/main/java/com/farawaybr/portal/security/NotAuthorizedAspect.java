package com.farawaybr.portal.security;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import com.farawaybr.portal.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.farawaybr.portal.security.auth.AuthenticationService;

@Interceptor
@NotAuthorizedJoinPointCut
@Priority(1)
public class NotAuthorizedAspect {

	@Inject
	private AuthenticationService authService;

	@Inject
	private Event<NotAuthorizedExceptionEvent> event;

	@AroundInvoke
	public Object authorize(InvocationContext joinpoint) throws Throwable {
		try {
			return joinpoint.proceed();
		} catch (NotAuthorizedException | ProcessingException e) {
			System.out.println("not authorized exception!");
			event.fire(new NotAuthorizedExceptionEvent("ydydyahemwbueewknenbwelewiwinveksk"));
			return null;
		}
//			if (e instanceof NotAuthorizedException) {
//				authService.refreshToken();
//				return joinpoint.proceed();
//			} else if (e instanceof ProcessingException) {
//				ProcessingException psExcpetion = (ProcessingException) e;
//				if (psExcpetion.getCause() instanceof NotAuthorizedException) {
//					authService.refreshToken();
//					return joinpoint.proceed();
//				}
//
//			}
//
//			throw e;

	}
}
