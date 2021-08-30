package com.portal.client.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.util.MathUtils;
import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {

	@Override
	public void calculateDueQuantity(Item item, int quantity) throws ItemQuantityNotAllowed {
		if (checkQuantityPolicies(item, quantity)) {
			calculateTotals(item, quantity);
			item.getValue().setQuantity(quantity);
			return;
		}
		throw new ItemQuantityNotAllowed(
				"quantity " + quantity + " must be multiple of " + item.getProduct().getValue().getMultiple());
	}

	@Override
	public void applyGlobalDiscount(@NotNull Collection<? extends Item> items, BigDecimal discount) {
		items.parallelStream().forEach(i -> {
			ItemValue value = i.getValue();

			BigDecimal[] unitValues = this.applyDiscount(i, discount);
			value.setUnitGrossValue(unitValues[0]);
			value.setUnitStValue(unitValues[1]);
			value.setUnitValue(unitValues[2]);
			value.setBudgetGlobalDiscount(discount);
			calculateTotals(i);
			value.setUnitGrossValueFromGBDiscount(unitValues[0]);
			value.setUnitStValueFromGBDiscount(unitValues[1]);
			value.setUnitValueFromGBDiscount(unitValues[2]);
			if (value.getLineDiscount() != null && !value.getLineDiscount().equals(BigDecimal.ZERO))
				this.applyLineDiscount(items,
						new ItemLineDiscountForm(i.getProduct().getLine(), value.getLineDiscount()));
		});
	}

	@Override
	public void applyGlobalDiscount(Item items, BigDecimal discount) {
		this.applyGlobalDiscount(List.of(items), discount);

	}

	@Override
	public void applyLineDiscount(@NotNull Collection<? extends Item> items, ItemLineDiscountForm itemLineDiscount) {
		BigDecimal discount = itemLineDiscount.getDiscount();
		String line = itemLineDiscount.getLine();
		items.parallelStream().filter(i -> i.getLine().equals(line)).forEach(i -> {
			ItemValue price = i.getValue();

			price.setUnitGrossValue(
					MathUtils.subtractValueByPercentage(discount, price.getUnitGrossValueFromGBDiscount()));
			price.setUnitStValue(MathUtils.subtractValueByPercentage(discount, price.getUnitStValueFromGBDiscount()));
			price.setUnitValue(MathUtils.subtractValueByPercentage(discount, price.getUnitValueFromGBDiscount()));
			price.setLineDiscount(discount);
			calculateTotals(i);

		});

	}

	private void calculateTotals(Item item) {
		ItemValue values = item.getValue();
		values.setTotalGrossValue(
				MathUtils.calculateTotalValueOverQuantity(values.getQuantity(), values.getUnitGrossValue()));
		values.setTotalStValue(
				MathUtils.calculateTotalValueOverQuantity(values.getQuantity(), values.getUnitStValue()));
		values.setTotalValue(MathUtils.calculateTotalValueOverQuantity(values.getQuantity(), values.getUnitValue()));
	}

	private void calculateTotals(Item item, int quantity) {
		ItemValue prices = item.getValue();
		prices.setTotalGrossValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitGrossValue()));
		prices.setTotalStValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitStValue()));
		prices.setTotalValue(MathUtils.calculateTotalValueOverQuantity(quantity, prices.getUnitValue()));
	}

	private BigDecimal[] applyDiscount(Item item, BigDecimal discount) {
		ItemValue price = item.getValue();
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
		if (item.getProduct().getValue().getMultiple() == 0)
			return true;
		return quantity % item.getProduct().getValue().getMultiple() == 0 ? true : false;
	}

}
