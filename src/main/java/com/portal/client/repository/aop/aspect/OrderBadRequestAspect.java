package com.portal.client.repository.aop.aspect;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ProcessingException;

import com.portal.client.dto.ErrorOrcamentoAPI;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.repository.aop.OrderBadRequestJoinPointCut;

@Interceptor
@OrderBadRequestJoinPointCut
public class OrderBadRequestAspect {

	@AroundInvoke
	public Object checkException(InvocationContext joinpoint) throws Exception {
		try {
			return joinpoint.proceed();
		} catch (ProcessingException e) {
			throw e.getCause() instanceof BadRequestException
					? new OrderBadRequestExcpetion(
							((BadRequestException) e.getCause()).getResponse().readEntity(ErrorOrcamentoAPI.class))
					: e;
		}
	}
}
