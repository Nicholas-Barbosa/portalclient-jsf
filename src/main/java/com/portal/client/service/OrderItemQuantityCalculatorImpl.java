package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
import com.portal.client.vo.ProductPriceData;

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
		ProductPriceData productPriceData = item.getProduct().getPriceData();

		BigDecimal oldTotalValue = productPriceData.getTotalValue();
		BigDecimal oldTotalGrossValue = productPriceData.getTotalGrossValue();
		BigDecimal oldTotalStValue = productPriceData.getTotalStValue();

		itemService.calculateDueQuantity(item, quantity);

		order.setGrossValue(
				order.getGrossValue().subtract(oldTotalGrossValue).add(productPriceData.getTotalGrossValue()));
		order.setLiquidValue(order.getLiquidValue().subtract(oldTotalValue).add(productPriceData.getTotalValue()));
		order.setStValue(order.getStValue().subtract(oldTotalStValue).add(productPriceData.getTotalStValue()));

	}

}
