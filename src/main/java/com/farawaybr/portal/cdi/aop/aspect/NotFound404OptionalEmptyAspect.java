package com.farawaybr.portal.cdi.aop.aspect;

import java.util.Optional;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;

import com.farawaybr.portal.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;

@Interceptor
@NotFoundOptionalEmptyJoinPointCut
@Priority(3)
public class NotFound404OptionalEmptyAspect {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext joinpoint) throws Throwable {
		try {
			return joinpoint.proceed();
		} catch (ProcessingException e) {
			if (e.getCause() instanceof NotFoundException && isOptional(joinpoint))
				return Optional.empty();
			throw e.getCause();
		}
	}

	private boolean isOptional(InvocationContext joinpoint) {
		return joinpoint.getMethod().getReturnType() == Optional.class;
	}
}
