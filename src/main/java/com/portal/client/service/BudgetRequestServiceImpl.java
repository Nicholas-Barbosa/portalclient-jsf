package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetToSave;
import com.portal.client.dto.ItemBudgetToSave;
import com.portal.client.exception.CustomerNotAllowed;

@ApplicationScoped
public class BudgetRequestServiceImpl implements BudgetRequestService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508352396468403737L;
	private ItemService itemService;

	@Inject
	public BudgetRequestServiceImpl(ItemService itemService) {
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
	public void removeItem(BudgetToSave budget, ItemBudgetToSave itemToRemove) {
		if (budget.getItems().remove(itemToRemove)) {
			calculateTotals(budget);
		}
	}

	@Override
	public void addItem(BudgetToSave budgetDTO, ItemBudgetToSave produc) {
		if (produc != null) {
			budgetDTO.getItems().add(produc);
			this.calculateTotals(budgetDTO);
		}
	}

	@Override
	public void setDiscount(BudgetToSave budget, BigDecimal discount) throws CustomerNotAllowed {
//		if (budget.getCustomerOnOrder().getType() == CustomerType.PROSPECT) {
		budget.setGlobalDiscount(discount);
		itemService.applyGlobalDiscount(budget.getItems(), discount);
		this.calculateTotals(budget);
		return;
//		}
//		throw new CustomerNotAllowed("You can't set global discount for a normal client!");

	}

}
