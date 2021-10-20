package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Order;

@ApplicationScoped
@Named
public class OrderCommonBehaviorHelperImpl implements OrderCommonBehaviorHelper, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508352396468403737L;
	private ItemService itemService;

	@Inject
	public OrderCommonBehaviorHelperImpl(ItemService itemService) {
		super();
		this.itemService = itemService;
	}

	@Override
	public void calculateTotals(Order budget) {
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
	public void removeItem(Order budget, Item item) {
		if (budget.removeItem(item)) {
			sumStValue(budget);
			ItemValue itemValue = item.getValue();
			BigDecimal itemGrossValue = itemValue.getTotalGrossValue();
			BigDecimal itemStValue = itemValue.getTotalStValue();
			BigDecimal itemTValue = itemValue.getTotalValue();
			BigDecimal orderGrossValue = budget.getGrossValue().subtract(itemGrossValue);
			BigDecimal orderStValue = budget.getStValue().subtract(itemStValue);
			BigDecimal orderValue = budget.getLiquidValue().subtract(itemTValue);
			budget.setGrossValue(orderGrossValue);
			budget.setStValue(orderStValue);
			budget.setLiquidValue(orderValue);
			item = null;
		}
	}

	@Override
	public void addItem(Order order, Item item) {
		if (item != null && order.addItem(item)) {
			if (order.getGlobalDiscount() != null && !order.getGlobalDiscount().equals(BigDecimal.ZERO)) {
				itemService.applyGlobalDiscount(item, order.getGlobalDiscount());
			}
			ItemValue itemValue = item.getValue();
			if (order.getGrossValue() == null)
				order.setGrossValue(itemValue.getTotalGrossValue());
			else
				order.setGrossValue(order.getGrossValue().add(itemValue.getTotalGrossValue()));
			if (order.getLiquidValue() == null)
				order.setLiquidValue(itemValue.getTotalValue());
			else
				order.setLiquidValue(order.getLiquidValue().add(itemValue.getTotalValue()));
			if (order.getStValue() == null)
				order.setStValue(itemValue.getTotalStValue());
			else
				order.setStValue(order.getStValue().add(itemValue.getTotalStValue()));
		}
	}

	@Override
	public void setDiscount(Order budget, BigDecimal discount) throws CustomerNotAllowed {
		budget.setGlobalDiscount(discount);
		if (budget.getItems().size() > 0) {
			itemService.applyGlobalDiscount(budget.getItems(), discount);
			this.calculateTotals(budget);
		}

	}

	@Override
	public void merge(Order mixedBudget, Order budgetToMix) {
		if (mixedBudget.getCode() == null & budgetToMix.getCode() != null)
			mixedBudget.setCode(budgetToMix.getCode());
		mixedBudget.setCreatedAt(budgetToMix.getCreatedAt());
		mixedBudget.setGlobalDiscount(mixedBudget.getGlobalDiscount().add(budgetToMix.getGlobalDiscount()));
		budgetToMix.getItems().stream().forEach(i -> {
			this.addItem(mixedBudget, i);
		});
	}

	@Override
	public void lineDiscount(Order order, ItemLineDiscountForm form) {
		this.itemService.applyLineDiscount(order.getItems(), form);
		this.calculateTotals(order);
	}

	@Override
	public void removeItems(Order order, List<Item> itemsToCompareAndRemove) {
		itemsToCompareAndRemove.forEach(i -> this.removeItem(order, i));

	}

	@Override
	public void addItem(Order order, Collection<Item> items) {
		items.stream().forEach(item -> this.addItem(order, item));
	}

	@Override
	public void sumStValue(Order order) {
		if (order.getStValue() == null) {
			BigDecimal stValue = order.getItems().stream().map(i -> i.getValue().getTotalStValue())
					.reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
			order.setStValue(stValue);
		}

	}

}
