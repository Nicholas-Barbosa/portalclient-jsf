package com.farawaybr.portal.jaxrs.client.aspect;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.farawaybr.portal.cdi.aop.annotations.IllegalResponsePointCutJoinPoint;
import com.farawaybr.portal.jaxrs.client.IllegalResponseStatusException;

@Interceptor
@IllegalResponsePointCutJoinPoint
@Priority(2)
public class IllegalResponseAspect {

	@AroundInvoke
	public Object checkResponse(InvocationContext joinPoint) throws Exception {
		try {
			return joinPoint.proceed();
		} catch (Exception e) {
			if (e instanceof IllegalResponseStatusException || e.getCause() instanceof IllegalResponseStatusException) {
				return joinPoint.proceed();
			}
			throw e;
		}
	}
}
