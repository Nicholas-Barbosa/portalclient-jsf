package com.farawaybr.portal.service;

import com.farawaybr.portal.exception.ItemQuantityNotAllowed;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Order;

public interface OrderItemQuantityCalculator {

	void calc(Order order,Item item,int quantity)throws ItemQuantityNotAllowed;
}
