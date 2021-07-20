package com.portal.client.service;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.BudgetListPage;
import com.portal.client.dto.BudgetXlsxPreviewForm;
import com.portal.client.dto.BudgetXlsxPreviewedDTO;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.Item;
import com.portal.client.dto.Order;
import com.portal.client.exception.CustomerNotAllowed;

public interface BudgetService extends ServiceSerializable {

	BudgetListPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException;

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(Order budget);

	void removeItem(Order budget, Item itemToRemove);

	BudgetXlsxPreviewedDTO previewXlsxContent(BudgetXlsxPreviewForm form);

	void addItem(Order budgetDTO, Item produc);

	default void setCustomer(Order budget, CustomerOnOrder customer) {
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
	void setDiscount(Order budget, BigDecimal discount) throws CustomerNotAllowed;

}
