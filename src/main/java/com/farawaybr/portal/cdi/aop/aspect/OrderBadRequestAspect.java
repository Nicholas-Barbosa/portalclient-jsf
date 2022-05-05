package com.farawaybr.portal.cdi.aop.aspect;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.BadRequestException;

import com.farawaybr.portal.cdi.aop.annotations.OrderBadRequestJoinPointCut;
import com.farawaybr.portal.dto.OrderBadRequestData;
import com.farawaybr.portal.repository.OrderBadRequestExcpetion;
import com.farawaybr.portal.service.jsonb.JsonbService;

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
			throw new OrderBadRequestExcpetion(jsonb.fromJson(json, OrderBadRequestData.class));

		}
	}
}
