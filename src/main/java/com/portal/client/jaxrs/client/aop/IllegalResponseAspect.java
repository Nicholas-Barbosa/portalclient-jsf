package com.portal.client.jaxrs.client.aop;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@IllegalResponsePointCutJoinPoint
public class IllegalResponseAspect {

	@AroundInvoke
	public Object checkResponse(InvocationContext joinPoint) throws Exception {
		try {
			return joinPoint.proceed();
		} catch (Exception e) {
			if (e instanceof IllegalStateException) {
				System.out.println("IllegalStateException");
				return joinPoint.proceed();
			}
			return null;
		}
	}
}
