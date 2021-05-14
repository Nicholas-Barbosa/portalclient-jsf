package com.portal.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.CustomerDTO;
import com.portal.dto.ItemEstimateBudgetForm;
import com.portal.dto.ProductDTO;
import com.portal.repository.BudgetRepository;

@Stateful
public class BudgetService {

	private CustomerDTO customer;

	@Inject
	private BudgetRepository budgetRepository;

	private final Set<ItemEstimateBudgetForm> items;
	private BudgetEstimateDTO budgetEstimated;
	
	public BudgetService() {
		items = new HashSet<>();
	}
	public BudgetEstimateDTO estimateValues() throws SocketTimeoutException, ConnectException, ProcessingException,
			IllegalArgumentException, SocketException, TimeoutException {
		budgetEstimated = budgetRepository
				.estimate(new BudgetEstimateForm(customer.getCode(), customer.getStore(), new HashSet<>(items)));
		return budgetEstimated;
	}

	public BudgetEstimateDTO calculatePreEstiamte() {
		return budgetRepository.recalculateEstimate(budgetEstimated);
	}

	public void addItem(ProductDTO e) {
		items.add(ItemEstimateBudgetForm.getInstanceFromProduct(e));
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public BudgetEstimateDTO getBudgetEstimated() {
		return budgetEstimated==null ? new BudgetEstimateDTO() : new BudgetEstimateDTO(budgetEstimated);
	}
	
	
}
