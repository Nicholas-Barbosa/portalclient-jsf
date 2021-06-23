package com.portal.java.service;

import java.util.Collection;

import com.portal.java.dto.Item;

public interface ItemService {

	/**
	 * Calculate totals(unit values * quantity). If calculateUnitValues is true,
	 * first calculates unit values based on discounts and then calculate totals
	 * 
	 * @param item
	 */
	void calculateDueQuantity(Item item, boolean calculateUnitValues);

	/**
	 * Calculate unit values based on new discounts.
	 * 
	 * @param item
	 */
	void calculateDueDiscount(Item item);
	/**
	 * Calculate unit values based on new discounts.
	 * 
	 * @param items
	 */
	void calculateDueDiscount(Collection<Item> items);
}
