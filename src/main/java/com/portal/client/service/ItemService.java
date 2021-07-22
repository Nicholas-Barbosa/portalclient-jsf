package com.portal.client.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.portal.client.dto.ItemBudgetToSave;
import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.exception.ItemQuantityNotAllowed;

public interface ItemService {

	/**
	 * Calculate totals(unit value * quantity).
	 * 
	 * @param item
	 */
	void calculateDueQuantity(ItemBudgetToSave item, int quantity)throws ItemQuantityNotAllowed;

	/**
	 * Calculate unit values based on new discount.
	 * 
	 * @param item
	 */
	void applyGlobalDiscount(@NotNull Collection<? extends ItemBudgetToSave> items, BigDecimal discount);

	void applyLineDiscount(@NotNull Collection<? extends ItemBudgetToSave> items, ItemLineDiscountForm itemLineDiscount);

	boolean checkQuantityPolicies(ItemBudgetToSave item, int quantity);
}
