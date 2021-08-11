package com.portal.client.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.portal.client.dto.ItemBudget;
import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.exception.ItemQuantityNotAllowed;

public interface ItemService {

	/**
	 * Calculate totals(unit value * quantity).
	 * 
	 * @param item
	 */
	void calculateDueQuantity(ItemBudget item, int quantity) throws ItemQuantityNotAllowed;

	/**
	 * Calculate unit values based on new discount.
	 * 
	 * @param item
	 */
	void applyGlobalDiscount(@NotNull Collection<? extends ItemBudget> items, BigDecimal discount);

	void applyGlobalDiscount(ItemBudget items, BigDecimal discount);

	void applyLineDiscount(@NotNull Collection<? extends ItemBudget> items, ItemLineDiscountForm itemLineDiscount);

	boolean checkQuantityPolicies(ItemBudget item, int quantity);
}
