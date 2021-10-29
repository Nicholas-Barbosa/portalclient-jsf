package com.portal.client.cdi.aop.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.portal.client.cdi.aop.annotations.OrderRepresentativeSetterJoinPointCut;
import com.portal.client.service.crud.OrderRprensentativeSetter;
import com.portal.client.vo.Order;

@Interceptor
@OrderRepresentativeSetterJoinPointCut
public class OrderRepresentativeSetterAspect {

	@Inject
	private OrderRprensentativeSetter orderSetter;

	@AroundInvoke
	public Object setAuthor(InvocationContext joinPoint) throws Exception {
		try {
			return joinPoint.proceed();
		} finally {
			Method method = joinPoint.getMethod();
			Parameter[] parameters = method.getParameters();
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i].getType() == Order.class) {
					Order order = null;
					parameters[i].getClass().cast(order);
					orderSetter.setAutor(order);
					break;
				}
			}
		}
	}
}
