package com.portal.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimateDTO.EstimatedItem;

public interface BudgetService {

	void findAll(int page, int pageSize);

	BudgetEstimateDTO estimate(BudgetEstimateForm budgetEstimateForm) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, SocketException, TimeoutException;

	/**
	 * Update all items from budget
	 * 
	 * @param budget
	 * @return
	 */
	BudgetEstimateDTO updateQuantity(BudgetEstimateDTO budget);

	/**
	 * Update items from budget which items are equal in term of equals.
	 * 
	 * @param budget
	 * @param estimatedItemValue
	 * @return
	 */
	BudgetEstimateDTO updateQuantity(BudgetEstimateDTO budget, EstimatedItem estimatedItemValue);

	void removeItem(BudgetEstimateDTO budget, EstimatedItem item);
}
