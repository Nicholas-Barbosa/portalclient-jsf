package com.portal.client.service;

import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;

public interface OrderItemQuantityCalculator {

	void calc(Order order,Item item,int quantity)throws ItemQuantityNotAllowed;
}
