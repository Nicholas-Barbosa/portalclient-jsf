package com.portal.client.jaxrs.client.aop.aspect;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.portal.client.jaxrs.client.aop.IllegalResponsePointCutJoinPoint;

@Interceptor
@IllegalResponsePointCutJoinPoint
public class IllegalResponseAspect {

	@AroundInvoke
	public Object checkResponse(InvocationContext joinPoint) throws Exception {
		try {
			return joinPoint.proceed();
		} catch (Exception e) {
			if (e instanceof IllegalStateException) {
				return joinPoint.proceed();
			}
			throw e;
		}
	}
}
