package com.portal.service;

import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimatedDTO;
import com.portal.dto.EstimatedItem;

public interface BudgetService extends ServiceSerializable{

	void findAll(int page, int pageSize);

	BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm) throws ProcessingException;

	/**
	 * Update items from budget which items are equal in term of equals.
	 * 
	 * @param budget
	 * @param estimatedItemValue
	 * @return
	 */
	void updateQuantity(BudgetEstimatedDTO budget, EstimatedItem estimatedItemValue);

	void removeItem(BudgetEstimatedDTO budget, EstimatedItem item);

	void checkQuantityPolicies(BudgetEstimatedDTO budget);

}
