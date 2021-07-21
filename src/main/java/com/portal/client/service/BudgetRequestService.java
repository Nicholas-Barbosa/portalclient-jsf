package com.portal.client.service;

import java.math.BigDecimal;

import com.portal.client.dto.BudgetRequest;
import com.portal.client.dto.ItemBudgetRequest;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.vo.CustomerOnOrder;

public interface BudgetRequestService {

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(BudgetRequest budget);

	void removeItem(BudgetRequest budget, ItemBudgetRequest itemToRemove);

	void addItem(BudgetRequest budgetDTO, ItemBudgetRequest produc);

	default void setCustomer(BudgetRequest budget, CustomerOnOrder customer) {
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
	void setDiscount(BudgetRequest budget, BigDecimal discount) throws CustomerNotAllowed;

}
