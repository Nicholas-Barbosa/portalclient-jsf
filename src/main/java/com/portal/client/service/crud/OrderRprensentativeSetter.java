package com.portal.client.service.crud;

import javax.enterprise.context.ApplicationScoped;

import com.portal.client.security.api.helper.APIHelper;
import com.portal.client.security.user.RepresentativeUser;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderRprensentativeSetter {

	private APIHelper protheusApi;

	public void setAutor(Order order) {
//		order.setRepresentative((RepresentativeUser) protheusApi.getSourceAPI().getUserData());

	}
}
