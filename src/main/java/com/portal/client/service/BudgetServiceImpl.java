package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetResponse;
import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.repository.BudgetRepository;
import com.portal.client.vo.BudgetPage;

@ApplicationScoped
public class BudgetServiceImpl implements BudgetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;

	private BudgetRepository budgetRepository;

	@Inject
	public BudgetServiceImpl(BudgetRepository budgetRepository) {
		super();
		this.budgetRepository = budgetRepository;
	}

	@Override
	public BudgetPage findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		return budgetRepository.findAll(page, pageSize);

	}

	@Override
	public BudgetResponse save(BudgetToSave budgetRequest, CustomerRepresentativeOrderForm ordersForm)
			throws SocketTimeoutException, ConnectException, SocketException, TimeoutException {
		checkBudgetState(budgetRequest);
		BudgetToSaveJsonSerializable toSave = new BudgetToSaveJsonSerializable(budgetRequest, ordersForm);
		return budgetRepository.save(toSave);
	}

	@Override
	public void checkBudgetState(BudgetToSave budgetRequest) {
		if (budgetRequest == null)
			throw new IllegalArgumentException("parameter is null!");
		if (budgetRequest.getCustomerOnOrder() == null)
			throw new IllegalArgumentException("CustomerOnOrder is null!");
		if (budgetRequest.getItems().size() == 0)
			throw new IllegalArgumentException("No items on budget!");
	}
}
