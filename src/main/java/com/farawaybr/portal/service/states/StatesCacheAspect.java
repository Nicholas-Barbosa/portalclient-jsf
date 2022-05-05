package com.farawaybr.portal.service.states;

import java.util.List;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.farawaybr.portal.dto.BrazilianState;

@Interceptor
@StatesCache
public class StatesCacheAspect {

	@Inject
	private BrazilianStatesSourceTruth source;

	@SuppressWarnings("unchecked")
	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
		if (source.isEmpty()) {
			Object result = context.proceed();
			List<BrazilianState> collection = (List<BrazilianState>) result;
			source.loadCacheSource(collection);
			return result;
		}
		return source.getAll();

	}
}
