package com.portal.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimateDTO.EstimatedItem;
import com.portal.repository.BudgetRepository;

@Stateless
public class BudgetServiceImpl implements BudgetService {

	@Inject
	private BudgetRepository budgetRepository;

	public BudgetEstimateDTO estimateValues(BudgetEstimateForm form) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, SocketException, TimeoutException {
		return budgetRepository.estimate(form);
	}

	public BudgetEstimateDTO calculatePreEstiamte() {
		return null;
		// return budgetRepository.recalculateEstimate(budgetEstimated);
	}

	@Override
	public void findAll(int page, int pageSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public BudgetEstimateDTO estimate(BudgetEstimateForm budgetEstimateForm) throws SocketTimeoutException,
			ConnectException, ProcessingException, IllegalArgumentException, SocketException, TimeoutException {
		return budgetRepository.estimate(budgetEstimateForm);
	}

	@Override
	public BudgetEstimateDTO updateQuantity(BudgetEstimateDTO budget) {
		budget.getEstimatedItemValues().parallelStream().forEach(EstimatedItem::recalculateTotales);
		budget.reCalculateTotales();
		return null;
	}
	@Override
	public BudgetEstimateDTO updateQuantity(BudgetEstimateDTO budget, EstimatedItem estimatedItemValue) {
		budget.getEstimatedItemValues().parallelStream().filter(es -> es.equals(estimatedItemValue))
				.forEach(EstimatedItem::recalculateTotales);
		budget.reCalculateTotales();
		return null;
	}
}
