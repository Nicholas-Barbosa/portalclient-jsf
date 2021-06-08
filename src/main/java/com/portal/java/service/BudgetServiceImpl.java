package com.portal.java.service;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;

import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;
import com.portal.java.dto.EstimatedItemDTO;
import com.portal.java.repository.BudgetRepository;

@ApplicationScoped
public class BudgetServiceImpl implements BudgetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268548772630741803L;
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
	public BudgetEstimatedDTO estimate(BudgetEstimateForm budgetEstimateForm)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		BudgetEstimatedDTO dto = budgetRepository.estimate(budgetEstimateForm);

		dto.getItems().parallelStream().forEach(e -> {
			budgetEstimateForm.getItemsForm().parallelStream()
					.filter(i -> i.getCommercialCode().equals(e.getCommercialCode())).findAny()
					.ifPresent((productForm) -> {
						e.setBaseProductAttr(productForm.getProductDTO());
					});

		});
		return dto;
	}

	@Override
	public void updateQuantity(BudgetEstimatedDTO budget, EstimatedItemDTO estimatedItemValue) {

		estimatedItemValue.changeTotalGrossValue(this.calculateTotalPerQuantity(estimatedItemValue.getQuantity(),
				estimatedItemValue.getUnitGrossValue()));
		estimatedItemValue.changeTotalPrice(
				this.calculateTotalPerQuantity(estimatedItemValue.getQuantity(), estimatedItemValue.getUnitPrice()));
		estimatedItemValue.changeTotalStValue(
				this.calculateTotalPerQuantity(estimatedItemValue.getQuantity(), estimatedItemValue.getUnitStValue()));
		this.bulkUpdateValues(budget);
	}

	@Override
	public void removeItem(BudgetEstimatedDTO budget, EstimatedItemDTO product) {
		budget.removeItem(product);
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

	private void bulkUpdateValues(BudgetEstimatedDTO oldBudget) {
		Set<EstimatedItemDTO> itemsToCalculate = oldBudget.getItems();
		BigDecimal liquidValue = new BigDecimal(itemsToCalculate.parallelStream().map(e -> e.getTotale())
				.collect(Collectors.summingDouble(v -> v.doubleValue())));
		BigDecimal grossValue = new BigDecimal(itemsToCalculate.parallelStream()
				.map(e -> e.getTotalGrossValue().doubleValue()).collect(Collectors.summingDouble(v -> v)));
		BigDecimal stTotal = new BigDecimal(itemsToCalculate.parallelStream().map(e -> e.getStValue().doubleValue())
				.collect(Collectors.summingDouble(v -> v)));
		BigDecimal totalDiscount = new BigDecimal(itemsToCalculate.parallelStream()
				.map(e -> e.getDiscount().doubleValue()).collect(Collectors.summingDouble(v -> v)));
		oldBudget.bulkUpdateTotales(liquidValue, grossValue, stTotal, totalDiscount);
	}

}
