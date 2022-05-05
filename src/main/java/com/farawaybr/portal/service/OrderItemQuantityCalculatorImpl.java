package com.farawaybr.portal.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.exception.ItemQuantityNotAllowed;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Order;
import com.farawaybr.portal.vo.ProductPriceData;

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
		ProductPriceData productPriceData = item.getPriceData();

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
