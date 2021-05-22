package com.portal.service;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;

import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimatedDTO;
import com.portal.dto.EstimatedItem;
import com.portal.repository.BudgetRepository;

@Stateless
public class BudgetServiceImpl implements BudgetService {

	@Inject
	private BudgetRepository budgetRepository;

	public BudgetEstimatedDTO estimateValues(BudgetEstimateForm form) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, SocketException, TimeoutException {
		return budgetRepository.estimate(form);
	}

	public BudgetEstimatedDTO calculatePreEstiamte() {
		return null;
		// return budgetRepository.recalculateEstimate(budgetEstimated);
	}

	@Override
	public void findAll(int page, int pageSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm) throws ProcessingException {
		return budgetRepository.estimate(budgetEstimateForm);
	}

	@Override
	public void updateQuantity(BudgetEstimatedDTO budget, EstimatedItem estimatedItemValue) {

		estimatedItemValue.changeTotalGrossValue(this.calculateTotalPerQuantity(estimatedItemValue.getQuantity(),
				estimatedItemValue.getUnitGrossValue()));
		estimatedItemValue.changeTotalPrice(
				this.calculateTotalPerQuantity(estimatedItemValue.getQuantity(), estimatedItemValue.getUnitPrice()));
		estimatedItemValue.changeTotalStValue(
				this.calculateTotalPerQuantity(estimatedItemValue.getQuantity(), estimatedItemValue.getUnitStValue()));
		this.bulkUpdateValues(budget);
	}

	@Override
	public void removeItem(BudgetEstimatedDTO budget, EstimatedItem item) {
		budget.removeItem(item);
		bulkUpdateValues(budget);
	}

	@Override
	public void checkQuantityPolicies(BudgetEstimatedDTO budget) {
		// String[]itemsOutOfStock =
		// budget.getEstimatedItemValues().parallelStream().filter(i -> i.get)

	}

	private BigDecimal calculateTotalPerQuantity(int quantity, BigDecimal unitValue) {
		return unitValue.multiply(new BigDecimal(quantity));
	}

	private BigDecimal calculateUnitValue(int quantity, BigDecimal total) {
		return total.divide(new BigDecimal(quantity));
	}

	private void bulkUpdateValues(BudgetEstimatedDTO oldBudget) {
		Set<EstimatedItem> itemsToCalculate = oldBudget.getItems();
		BigDecimal liquidValue = new BigDecimal(oldBudget.getItems().parallelStream().map(e -> e.getTotale())
				.collect(Collectors.summingDouble(v -> v.doubleValue())));
		BigDecimal grossValue = new BigDecimal(oldBudget.getItems().parallelStream()
				.map(e -> e.getTotalGrossValue().doubleValue()).collect(Collectors.summingDouble(v -> v)));
		BigDecimal stTotal = new BigDecimal(oldBudget.getItems().parallelStream().map(e -> e.getStValue().doubleValue())
				.collect(Collectors.summingDouble(v -> v)));
		BigDecimal totalDiscount = new BigDecimal(itemsToCalculate.parallelStream()
				.map(e -> e.getDiscount().doubleValue()).collect(Collectors.summingDouble(v -> v)));
		oldBudget.bulkUpdateTotales(liquidValue, grossValue, stTotal, totalDiscount);
	}
}
