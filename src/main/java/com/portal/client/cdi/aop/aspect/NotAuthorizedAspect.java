package com.portal.client.cdi.aop.aspect;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;

import com.portal.client.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.portal.client.security.auth.AuthenticationService;

@Interceptor
@NotAuthorizedJoinPointCut
public class NotAuthorizedAspect {

	@Inject
	private AuthenticationService authService;

	@AroundInvoke
	public Object authorize(InvocationContext joinpoint) throws Throwable {

		try {
			return joinpoint.proceed();
		} catch (NotAuthorizedException e) {
			authService.refreshToken();
			return joinpoint.proceed();
		}
	}
}
