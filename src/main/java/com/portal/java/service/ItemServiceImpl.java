package com.portal.java.service;

import javax.enterprise.context.ApplicationScoped;

import com.portal.java.dto.Item;
import com.portal.java.dto.ItemPrice;
import com.portal.java.util.MathUtils;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {

	@Override
	public void calculateDueQuantity(Item item, boolean calculateUnitValues) {
		if (calculateUnitValues)
			this.calculateDueDiscount(item);
		ItemPrice prices = item.getItemPrice();
		prices.setTotalGrossValue(
				MathUtils.calculateTotalValueOverQuantity(item.getQuantity(), prices.getUnitGrossValue()));
		prices.setTotalStValue(MathUtils.calculateTotalValueOverQuantity(item.getQuantity(), prices.getUnitStValue()));
		prices.setTotalValue(MathUtils.calculateTotalValueOverQuantity(item.getQuantity(), prices.getUnitValue()));
	}

	@Override
	public void calculateDueDiscount(Item item) {
		ItemPrice prices = item.getItemPrice();
		prices.setUnitGrossValue(MathUtils.subtractValueByPercentage(item.getTotalDiscount(),
				prices.getUnitGrossValueWithoutDiscount()));
		prices.setUnitStValue(
				MathUtils.subtractValueByPercentage(item.getTotalDiscount(), prices.getUnitStValueWithoutDiscount()));
		prices.setUnitValue(
				MathUtils.subtractValueByPercentage(item.getTotalDiscount(), prices.getUnitValueWithoutDiscount()));
		this.calculateDueQuantity(item, false);
	}

}
