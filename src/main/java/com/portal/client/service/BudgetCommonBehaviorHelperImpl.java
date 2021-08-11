package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BaseBudget;
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
	public void calculateTotals(BaseBudget budget) {
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
	public void removeItem(BaseBudget budget, ItemBudget itemToRemove) {
		if (budget.removeItem(itemToRemove)) {
			calculateTotals(budget);
		}
	}

	@Override
	public void addItem(BaseBudget budget, ItemBudget item) {
		if (item != null && budget.addItem(item)) {
			if (budget.getGlobalDiscount() != null && !budget.getGlobalDiscount().equals(BigDecimal.ZERO)) {
				itemService.applyGlobalDiscount(item, budget.getGlobalDiscount());
			}
			this.calculateTotals(budget);
		}
	}

	@Override
	public void setDiscount(BaseBudget budget, BigDecimal discount) throws CustomerNotAllowed {
		budget.setGlobalDiscount(discount);
		if (budget.getItems().size() > 0) {
			itemService.applyGlobalDiscount(budget.getItems(), discount);
			this.calculateTotals(budget);
		}

	}

	@Override
	public void merge(BaseBudget mixedBudget, BaseBudget budgetToMix) {
		if (mixedBudget.getIdCode() == null & budgetToMix.getIdCode() != null)
			mixedBudget.setIdCode(budgetToMix.getIdCode());
		mixedBudget.setCreatedAt(budgetToMix.getCreatedAt());
		mixedBudget.setGlobalDiscount(mixedBudget.getGlobalDiscount().add(budgetToMix.getGlobalDiscount()));
		budgetToMix.getItems().stream().forEach(i -> {
			this.addItem(mixedBudget, i);
		});
	}

}
