package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.ItemBudget;
import com.portal.client.exception.CustomerNotAllowed;

@ApplicationScoped
public class BudgetCommonBehaviorHelperImpl implements BudgetCommonBehaviorHelper, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508352396468403737L;
	private ItemService itemService;

	@Inject
	public BudgetCommonBehaviorHelperImpl(ItemService itemService) {
		super();
		this.itemService = itemService;
	}

	@Override
	public void calculateTotals(BudgetToSave budget) {
		BigDecimal newGrossValue = budget.getItems().parallelStream().map(p -> p.getValues().getTotalGrossValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newLiquidValue = budget.getItems().parallelStream().map(p -> p.getValues().getTotalValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newStValue = budget.getItems().parallelStream().map(p -> p.getValues().getTotalStValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		budget.setGrossValue(newGrossValue);
		budget.setLiquidValue(newLiquidValue);
		budget.setStValue(newStValue);
	}

	@Override
	public void removeItem(BudgetToSave budget, ItemBudget itemToRemove) {
		if (budget.getItems().remove(itemToRemove)) {
			calculateTotals(budget);
		}
	}

	@Override
	public void addItem(BudgetToSave budgetDTO, ItemBudget produc) {
		if (produc != null) {
			budgetDTO.getItems().add(produc);
			this.calculateTotals(budgetDTO);
		}
	}

	@Override
	public void setDiscount(BudgetToSave budget, BigDecimal discount) throws CustomerNotAllowed {
		budget.setGlobalDiscount(discount);
		itemService.applyGlobalDiscount(budget.getItems(), discount);
		this.calculateTotals(budget);
		return;

	}

}
