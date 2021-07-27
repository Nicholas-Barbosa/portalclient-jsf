package com.portal.client.service;

import java.math.BigDecimal;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.ItemBudget;
import com.portal.client.exception.CustomerNotAllowed;

public interface BudgetCommonBehaviorHelper {

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(BaseBudget budget);

	void removeItem(BaseBudget budget, ItemBudget itemToRemove);

	void addItem(BaseBudget budgetDTO, ItemBudget produc);

	default void setCustomer(BaseBudget budget, CustomerOnOrder customer) {
		if (budget.getItems().size() >= 1)
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
	void setDiscount(BaseBudget budget, BigDecimal discount) throws CustomerNotAllowed;

}
