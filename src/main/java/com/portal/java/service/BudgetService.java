package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;
import com.portal.java.dto.BudgetXlsxPreviewForm;
import com.portal.java.dto.BudgetXlsxPreviewedDTO;
import com.portal.java.dto.EstimatedItemDTO;

public interface BudgetService extends ServiceSerializable {

	void findAll(int page, int pageSize);

	BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	/**
	 * Update items from budget which items are equal in term of equals.
	 * 
	 * @param budget
	 * @param estimatedItemValue
	 * @return
	 */
	void reCalculate(BudgetEstimatedDTO budget, EstimatedItemDTO estimatedItemValue);

	void removeItem(BudgetEstimatedDTO budget, EstimatedItemDTO item);

	void checkQuantityPolicies(BudgetEstimatedDTO budget);

	BudgetXlsxPreviewedDTO previewXlsxContent(BudgetXlsxPreviewForm form);
}
