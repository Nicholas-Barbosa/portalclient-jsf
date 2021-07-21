package com.portal.client.service;

import java.math.BigDecimal;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import com.portal.client.dto.ItemBudgetRequest;
import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.dto.ItemBuRequestValues;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.util.MathUtils;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {

	@Override
	public void calculateDueQuantity(ItemBudgetRequest item, int quantity) throws ItemQuantityNotAllowed {
		if (checkQuantityPolicies(item, quantity)) {
			calculateTotals(item, quantity);
			item.getValues().setQuantity(quantity);
			return;
		}
		throw new ItemQuantityNotAllowed(
				"quantity " + quantity + " must be multiple of " + item.getProduct().getMultiple());
	}

	@Override
	public void applyGlobalDiscount(@NotNull Collection<? extends ItemBudgetRequest> items, BigDecimal discount) {
		items.parallelStream().forEach(i -> {
			ItemBuRequestValues price = i.getValues();

			BigDecimal[] unitValues = this.applyDiscount(i, discount);
			price.setUnitGrossValue(unitValues[0]);
			price.setUnitStValue(unitValues[1]);
			price.setUnitValue(unitValues[2]);
			price.setBudgetGlobalDiscount(discount);
			calculateTotals(i);
			price.setUnitGrossValueFromGBDiscount(unitValues[0]);
			price.setUnitStValueFromGBDiscount(unitValues[1]);
			price.setUnitValueFromGBDiscount(unitValues[2]);

			if (price.getLineDiscount() != null || !price.getLineDiscount().equals(BigDecimal.ZERO))
				this.applyLineDiscount(items,
						new ItemLineDiscountForm(i.getProduct().getLine(), price.getLineDiscount()));
		});
	}

	@Override
	public void applyLineDiscount(@NotNull Collection<? extends ItemBudgetRequest> items, ItemLineDiscountForm itemLineDiscount) {
		BigDecimal discount = itemLineDiscount.getDiscount();
		String line = itemLineDiscount.getLine();
		items.parallelStream().filter(i -> i.line().equals(line)).forEach(i -> {
			ItemBuRequestValues price = i.getValues();

			price.setUnitGrossValue(
					MathUtils.subtractValueByPercentage(discount, price.getUnitGrossValueFromGBDiscount()));
			price.setUnitStValue(MathUtils.subtractValueByPercentage(discount, price.getUnitStValueFromGBDiscount()));
			price.setUnitValue(MathUtils.subtractValueByPercentage(discount, price.getUnitValueFromGBDiscount()));
			price.setLineDiscount(discount);
			calculateTotals(i);

		});

	}

	private void calculateTotals(ItemBudgetRequest item) {
		ItemBuRequestValues values = item.getValues();
		values.setTotalGrossValue(
				MathUtils.calculateTotalValueOverQuantity(values.getQuantity(), values.getUnitGrossValue()));
		values.setTotalStValue(
				MathUtils.calculateTotalValueOverQuantity(values.getQuantity(), values.getUnitStValue()));
		values.setTotalValue(MathUtils.calculateTotalValueOverQuantity(values.getQuantity(), values.getUnitValue()));
	}

	private void calculateTotals(ItemBudgetRequest item, int quantity) {
		ItemBuRequestValues prices = item.getValues();
		prices.setTotalGrossValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitGrossValue()));
		prices.setTotalStValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitStValue()));
		prices.setTotalValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitValue()));
	}

	private BigDecimal[] applyDiscount(ItemBudgetRequest item, BigDecimal discount) {
		ItemBuRequestValues price = item.getValues();
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
	public boolean checkQuantityPolicies(ItemBudgetRequest item, int quantity) {
		// TODO Auto-generated method stub
		return quantity % item.getProduct().getMultiple() == 0 ? true : false;
	}

}
