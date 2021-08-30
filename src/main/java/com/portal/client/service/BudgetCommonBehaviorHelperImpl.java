package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

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
	public void calculateTotals(Budget budget) {
		BigDecimal newGrossValue = budget.getItems().parallelStream().map(p -> p.getValue().getTotalGrossValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newLiquidValue = budget.getItems().parallelStream().map(p -> p.getValue().getTotalValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newStValue = budget.getItems().parallelStream().map(p -> p.getValue().getTotalStValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		budget.setGrossValue(newGrossValue);
		budget.setLiquidValue(newLiquidValue);
		budget.setStValue(newStValue);
	}

	@Override
	public void removeItem(Budget budget, Item item) {
		if (budget.removeItem(item)) {
			calculateTotals(budget);
		}
	}

	@Override
	public void addItem(Budget budget, Item item) {
		if (item != null && budget.addItem(item)) {
			if (budget.getGlobalDiscount() != null && !budget.getGlobalDiscount().equals(BigDecimal.ZERO)) {
				itemService.applyGlobalDiscount(item, budget.getGlobalDiscount());
			}
			this.calculateTotals(budget);
		}
	}

	@Override
	public void setDiscount(Budget budget, BigDecimal discount) throws CustomerNotAllowed {
		budget.setGlobalDiscount(discount);
		if (budget.getItems().size() > 0) {
			itemService.applyGlobalDiscount(budget.getItems(), discount);
			this.calculateTotals(budget);
		}

	}

	@Override
	public void merge(Budget mixedBudget, Budget budgetToMix) {
		if (mixedBudget.getCode() == null & budgetToMix.getCode() != null)
			mixedBudget.setCode(budgetToMix.getCode());
		mixedBudget.setCreatedAt(budgetToMix.getCreatedAt());
		mixedBudget.setGlobalDiscount(mixedBudget.getGlobalDiscount().add(budgetToMix.getGlobalDiscount()));
		budgetToMix.getItems().stream().forEach(i -> {
			this.addItem(mixedBudget, i);
		});
	}

}
