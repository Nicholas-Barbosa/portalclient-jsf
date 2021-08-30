package com.portal.client.service;

import java.math.BigDecimal;

import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

public interface BudgetCommonBehaviorHelper {

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(Budget budget);

	void removeItem(Budget budget, Item itemToRemove);

	void addItem(Budget budgetDTO, Item produc);

	default void setCustomer(Budget budget, CustomerOnOrder customer) {
		if (budget.getItems() != null && budget.getItems().size() >= 1)
			throw new UnsupportedOperationException(
					"You can't set a client at this moment. Because this budget has many items.");
		budget.setCustomerOnOrder(customer);
	}

	/**
	 * Set global discount.Only for PROSPECT customers!
	 * 
	 * @param budget
	 * @param discount
	 */
	void setDiscount(Budget budget, BigDecimal discount) throws CustomerNotAllowed;

	void merge(Budget mixedBudget, Budget budgetToMix);
}
