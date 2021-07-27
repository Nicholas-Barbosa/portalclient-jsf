package com.portal.client.service;

import java.math.BigDecimal;

import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.ItemBudgetToSave;
import com.portal.client.exception.CustomerNotAllowed;

public interface BudgetRequestService {

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(BudgetToSave budget);

	void removeItem(BudgetToSave budget, ItemBudgetToSave itemToRemove);

	void addItem(BudgetToSave budgetDTO, ItemBudgetToSave produc);

	default void setCustomer(BudgetToSave budget, CustomerOnOrder customer) {
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
	void setDiscount(BudgetToSave budget, BigDecimal discount) throws CustomerNotAllowed;

}
