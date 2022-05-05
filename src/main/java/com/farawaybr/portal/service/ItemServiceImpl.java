package com.farawaybr.portal.service;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;

import com.farawaybr.portal.exception.ItemQuantityNotAllowed;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.ProductPriceData;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {

	@Override
	public void calculateDueQuantity(Item item, int quantity) throws ItemQuantityNotAllowed {
		if (checkQuantityPolicies(item, quantity)) {
			calculateTotals(item, quantity);
			item.getPriceData().setQuantity(quantity);
			return;
		}
		throw new ItemQuantityNotAllowed(
				"quantity " + quantity + " must be multiple of " + item.getPriceData().getMultiple());
	}

//	@Override
//	public void applyGlobalDiscount(@NotNull Collection<? extends Item> items, BigDecimal discount) {
//		items.parallelStream().forEach(i -> {
//			ItemValue value = i.getValue();
//
//			BigDecimal[] unitValues = this.applyDiscount(i, discount);
//			value.setUnitGrossValue(unitValues[0]);
//			value.setUnitStValue(unitValues[1]);
//			value.setUnitValue(unitValues[2]);
//			value.setBudgetGlobalDiscount(discount);
//			calculateTotals(i);
//			value.setUnitGrossValueFromGBDiscount(unitValues[0]);
//			value.setUnitStValueFromGBDiscount(unitValues[1]);
//			value.setUnitValueFromGBDiscount(unitValues[2]);
//			if (value.getLineDiscount() != null && !value.getLineDiscount().equals(BigDecimal.ZERO))
//				this.applyLineDiscount(items,
//						new ItemLineDiscountForm(i.getProduct().getLine(), value.getLineDiscount()));
//		});
//	}
//
//	@Override
//	public void applyGlobalDiscount(Item items, BigDecimal discount) {
//		this.applyGlobalDiscount(List.of(items), discount);
//
//	}
//
//	@Override
//	public void applyLineDiscount(@NotNull Collection<? extends Item> items, ItemLineDiscountForm itemLineDiscount) {
//		BigDecimal discount = itemLineDiscount.getDiscount();
//		String line = itemLineDiscount.getLine();
//		items.parallelStream().filter(i -> i.getLine().equals(line)).forEach(i -> {
//			ItemValue price = i.getValue();
//
//			price.setUnitGrossValue(
//					MathUtils.subtractValueByPercentage(discount, price.getUnitGrossValueFromGBDiscount()));
//			price.setUnitStValue(MathUtils.subtractValueByPercentage(discount, price.getUnitStValueFromGBDiscount()));
//			price.setUnitValue(MathUtils.subtractValueByPercentage(discount, price.getUnitValueFromGBDiscount()));
//			price.setLineDiscount(discount);
//			calculateTotals(i);
//
//		});
//
//	}

	private void calculateTotals(Item item) {
		ProductPriceData priceData = item.getPriceData();
		BigDecimal quantity = BigDecimal.valueOf(priceData.getQuantity());
		priceData.setTotalGrossValue(priceData.getUnitGrossValue().multiply(quantity));
		priceData.setTotalStValue(priceData.getUnitStValue().multiply(quantity));
		priceData.setTotalValue(priceData.getUnitValue().multiply(quantity));
	}

	private void calculateTotals(Item item, int quantity1) {
		ProductPriceData priceData = item.getPriceData();
		BigDecimal quantity = BigDecimal.valueOf(quantity1);
		priceData.setTotalGrossValue(priceData.getUnitGrossValue().multiply(quantity));
		priceData.setTotalStValue(priceData.getUnitStValue().multiply(quantity));
		priceData.setTotalValue(priceData.getUnitValue().multiply(quantity));
	}
//	private BigDecimal[] applyDiscount(Item item, BigDecimal discount) {
//		ItemValue price = item.getValue();
//		BigDecimal values[] = new BigDecimal[3];
//		BigDecimal unitGrossValue = MathUtils.subtractValueByPercentage(discount,
//				price.getUnitGrossValueWithoutDiscount());
//		BigDecimal unitStValue = MathUtils.subtractValueByPercentage(discount, price.getUnitStValueWithoutDiscount());
//		BigDecimal unitValue = MathUtils.subtractValueByPercentage(discount, price.getUnitValueWithoutDiscount());
//		values[0] = unitGrossValue;
//		values[1] = unitStValue;
//		values[2] = unitValue;
//		return values;
//	}

	@Override
	public boolean checkQuantityPolicies(Item item, int quantity) {
		// TODO Auto-generated method stub
		ProductPriceData priceData = item.getPriceData();
		if (priceData.getMultiple() == null || priceData.getMultiple() == 0)
			return true;
		return quantity % priceData.getMultiple() == 0 ? true : false;
	}

}
