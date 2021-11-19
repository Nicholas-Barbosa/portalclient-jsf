package com.portal.client.resources.export;

import com.portal.client.vo.Order;

public interface OrderJsonHandler {

	byte[] toJson(Order order);

	Order fromJson(byte[] json);
}
