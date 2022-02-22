package com.portal.client.cdi.aop.aspect;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import com.portal.client.cdi.aop.annotations.NotAuthorizedJoinPointCut;
import com.portal.client.security.auth.AuthenticationService;

@Interceptor
@NotAuthorizedJoinPointCut
@Priority(0)
public class NotAuthorizedAspect {

	@Inject
	private AuthenticationService authService;

	@AroundInvoke
	public Object authorize(InvocationContext joinpoint) throws Throwable {
		try {
			System.out.println("not authorized interceptor");
			return joinpoint.proceed();
		} catch (NotAuthorizedException | ProcessingException e) {
			System.out.println("not authorized " +e);
			if (e instanceof ProcessingException)
				if (e.getCause() instanceof NotAuthorizedException)
					authService.refreshToken();
			return joinpoint.proceed();
		}
	}
}
