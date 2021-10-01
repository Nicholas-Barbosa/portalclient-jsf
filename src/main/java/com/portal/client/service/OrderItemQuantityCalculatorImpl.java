package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Order;

@ApplicationScoped
public class OrderItemQuantityCalculatorImpl implements OrderItemQuantityCalculator, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405223065684465910L;
	@Inject
	private ItemService itemService;

	@Override
	public void calc(Order order, Item item, int quantity) throws ItemQuantityNotAllowed {
		ItemValue itemValue = item.getValue();

		BigDecimal oldTotalValue = itemValue.getTotalValue();
		BigDecimal oldTotalGrossValue = itemValue.getTotalGrossValue();
		BigDecimal oldTotalStValue = itemValue.getTotalStValue();

		itemService.calculateDueQuantity(item, quantity);

		order.setGrossValue(order.getGrossValue().subtract(oldTotalGrossValue).add(itemValue.getTotalGrossValue()));
		order.setLiquidValue(order.getLiquidValue().subtract(oldTotalValue).add(itemValue.getTotalValue()));
		order.setStValue(order.getStValue().subtract(oldTotalStValue).add(itemValue.getTotalStValue()));

	}

}
