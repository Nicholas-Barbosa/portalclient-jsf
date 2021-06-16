package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.BudgetDTO;
import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;
import com.portal.java.dto.BudgetXlsxPreviewForm;
import com.portal.java.dto.BudgetXlsxPreviewedDTO;
import com.portal.java.dto.Product;

public interface BudgetService extends ServiceSerializable {

	void findAll(int page, int pageSize);

	BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	/**
	 * Recalculate newProductValues object and then recalculate totals for this
	 * mutable budget object.
	 * 
	 * @param budget
	 * @param newProductValues
	 * @return
	 */
	void recalculate(BudgetDTO budget, Product newProductValues);

	void removeItem(BudgetDTO budget, Product itemToRemove);

	void checkQuantityPolicies(BudgetEstimatedDTO budget);

	BudgetXlsxPreviewedDTO previewXlsxContent(BudgetXlsxPreviewForm form);

	void recalculateForGlobalDiscount(BudgetDTO budgetDTO);
}
