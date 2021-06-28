package com.portal.java.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import com.portal.java.dto.Item;
import com.portal.java.dto.ItemLineDiscountForm;
import com.portal.java.dto.ItemPrice;
import com.portal.java.exception.ItemQuantityNotAllowed;
import com.portal.java.util.MathUtils;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {

	@Override
	public void calculateDueQuantity(Item item, int quantity) throws ItemQuantityNotAllowed {
		if (checkQuantityPolicies(item, quantity)) {
			calculateTotals(item, quantity);
			item.setQuantity(quantity);

			return;
		}
		throw new ItemQuantityNotAllowed(
				"quantity " + quantity + " must be multiple of " + item.getProduct().getMultiple());
	}

	@Override
	public void applyGlobalDiscount(@NotNull Collection<? extends Item> items, BigDecimal discount) {
		items.parallelStream().forEach(i -> {
			ItemPrice price = i.getItemPrice();

			BigDecimal[] unitValues = this.applyDiscount(i, discount);
			price.setUnitGrossValue(unitValues[0]);
			price.setUnitStValue(unitValues[1]);
			price.setUnitValue(unitValues[2]);
			i.setBudgetGlobalDiscount(discount);
			calculateTotals(i);
			price.setUnitGrossValueFromGBDiscount(unitValues[0]);
			price.setUnitStValueFromGBDiscount(unitValues[1]);
			price.setUnitValueFromGBDiscount(unitValues[2]);
		});
	}

	@Override
	public void applyLineDiscount(@NotNull Collection<? extends Item> items, ItemLineDiscountForm itemLineDiscount) {
		BigDecimal discount = itemLineDiscount.getDiscount();
		String line = itemLineDiscount.getLine();
		items.parallelStream().filter(i -> i.line().equals(line)).forEach(i -> {
			ItemPrice price = i.getItemPrice();

			price.setUnitGrossValue(
					MathUtils.subtractValueByPercentage(discount, price.getUnitGrossValueFromGBDiscount()));
			price.setUnitStValue(MathUtils.subtractValueByPercentage(discount, price.getUnitStValueFromGBDiscount()));
			price.setUnitValue(MathUtils.subtractValueByPercentage(discount, price.getUnitValueFromGBDiscount()));
			i.setLineDiscount(discount);
			calculateTotals(i);

		});

	}

	private void calculateTotals(Item item) {
		ItemPrice prices = item.getItemPrice();
		prices.setTotalGrossValue(
				MathUtils.calculateTotalValueOverQuantity(item.getQuantity(), prices.getUnitGrossValue()));
		prices.setTotalStValue(MathUtils.calculateTotalValueOverQuantity(item.getQuantity(), prices.getUnitStValue()));
		prices.setTotalValue(MathUtils.calculateTotalValueOverQuantity(item.getQuantity(), prices.getUnitValue()));
	}

	private void calculateTotals(Item item, int quantity) {
		ItemPrice prices = item.getItemPrice();
		prices.setTotalGrossValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitGrossValue()));
		prices.setTotalStValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitStValue()));
		prices.setTotalValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitValue()));
	}

	private BigDecimal[] applyDiscount(Item item, BigDecimal discount) {
		ItemPrice price = item.getItemPrice();
		BigDecimal values[] = new BigDecimal[3];
		BigDecimal unitGrossValue = MathUtils.subtractValueByPercentage(discount,
				price.getUnitGrossValueWithoutDiscount());
		BigDecimal unitStValue = MathUtils.subtractValueByPercentage(discount, price.getUnitStValueWithoutDiscount());
		BigDecimal unitValue = MathUtils.subtractValueByPercentage(discount, price.getUnitValueWithoutDiscount());
		values[0] = unitGrossValue;
		values[1] = unitStValue;
		values[2] = unitValue;
		return values;
	}

	@Override
	public boolean checkQuantityPolicies(Item item, int quantity) {
		// TODO Auto-generated method stub
		return quantity % item.getProduct().getMultiple() == 0 ? true : false;
	}

}
