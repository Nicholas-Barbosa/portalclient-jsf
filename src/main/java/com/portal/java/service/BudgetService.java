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
import com.portal.java.dto.Item;

public interface BudgetService extends ServiceSerializable {

	void findAll(int page, int pageSize);

	BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(BudgetDTO budget);

	/**
	 * Calculate totals for item-args object and then calculate totals for
	 * this mutable budget object.
	 * 
	 * @param budget
	 * @param item
	 * @return
	 */
	void calculateTotals(BudgetDTO budget, Item item,boolean calculateDueChangesOnDiscount,boolean calculateDueChangesOnQuantity);

	void removeItem(BudgetDTO budget, Item itemToRemove);

	BudgetXlsxPreviewedDTO previewXlsxContent(BudgetXlsxPreviewForm form);

	/**
	 * Adds globalDiscout field for all budgetDTO items, then calculates all unit
	 * and total values for budgetDTO items.Finally calculates the totals for that
	 * budget object based on the item totals
	 * 
	 * @param budgetDTO
	 */
	void calculateForGlobalDiscount(BudgetDTO budgetDTO);

	void addItem(BudgetDTO budgetDTO, Item produc);
}
