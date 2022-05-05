package com.farawaybr.portal.service.crud;

import javax.enterprise.context.ApplicationScoped;

import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.vo.Order;

@ApplicationScoped
public class OrderRprensentativeSetter {

	private APIHelper protheusApi;

	public void setAutor(Order order) {
//		order.setRepresentative((RepresentativeUser) protheusApi.getSourceAPI().getUserData());

	}
}
