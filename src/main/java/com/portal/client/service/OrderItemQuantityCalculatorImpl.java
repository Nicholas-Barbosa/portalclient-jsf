package com.portal.client.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderItemQuantityCalculatorImpl implements OrderItemQuantityCalculator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405223065684465910L;
	@Inject
	private OrderCommonBehaviorHelper orderBehavior;
	@Inject
	private ItemService itemService;

	@Override
	public void calc(Order order, Item item, int quantity) throws ItemQuantityNotAllowed {
		itemService.calculateDueQuantity(item, quantity);
		orderBehavior.calculateTotals(order);
	}

}
