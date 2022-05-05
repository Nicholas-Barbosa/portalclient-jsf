package com.farawaybr.portal.service;

import com.farawaybr.portal.exception.ItemQuantityNotAllowed;
import com.farawaybr.portal.vo.Item;

public interface ItemService {

	/**
	 * Calculate totals(unit value * quantity).
	 * 
	 * @param item
	 */
	void calculateDueQuantity(Item item, int quantity) throws ItemQuantityNotAllowed;

	/**
	 * Calculate unit values based on new discount.
	 * 
	 * @param item
	 */
//	void applyGlobalDiscount(@NotNull Collection<? extends Item> items, BigDecimal discount);
//
//	void applyGlobalDiscount(Item items, BigDecimal discount);
//
//	void applyLineDiscount(@NotNull Collection<? extends Item> items, ItemLineDiscountForm itemLineDiscount);

	boolean checkQuantityPolicies(Item item, int quantity);
}
