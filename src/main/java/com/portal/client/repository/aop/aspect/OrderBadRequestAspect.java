package com.portal.client.repository.aop.aspect;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.BadRequestException;

import com.portal.client.dto.ErrorOrcamentoAPI;
import com.portal.client.repository.OrderBadRequestExcpetion;
import com.portal.client.repository.aop.OrderBadRequestJoinPointCut;
import com.portal.client.service.jsonb.JsonbService;

@Interceptor
@OrderBadRequestJoinPointCut
public class OrderBadRequestAspect {

	@Inject
	private JsonbService jsonb;

	@AroundInvoke
	public Object checkException(InvocationContext joinpoint) throws Exception {
		try {
			return joinpoint.proceed();
		} catch (BadRequestException e) {
			String json = (String) e.getResponse().getEntity();
			throw new OrderBadRequestExcpetion(jsonb.fromJson(json, ErrorOrcamentoAPI.class));

		}
	}
}
