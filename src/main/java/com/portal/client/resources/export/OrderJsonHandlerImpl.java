package com.portal.client.resources.export;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.service.jsonb.JsonbService;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderJsonHandlerImpl implements OrderJsonHandler {

	@Inject
	private JsonbService jsonB;

	@Override
	public byte[] toJson(Order order) {
		// TODO Auto-generated method stub
		return jsonB.toJson(order).getBytes();
	}

	@Override
	public Order fromJson(byte[] json) {
		return jsonB.fromJson(new String(json), Order.class);
	}

}
