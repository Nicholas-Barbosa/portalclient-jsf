package com.portal.client.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.BatchProductSearchDataWrapper.BatchProductSearchData;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductPriceData;

@ApplicationScoped
@Named
public class OrderBehaviorHelperImpl implements OrderBehaviorHelper, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508352396468403737L;

	@Override
	public void calculateTotals(Order budget) {
		BigDecimal newGrossValue = budget.getItems().parallelStream().map(Item::getProduct)
				.map(p -> p.getPriceData().getTotalGrossValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newLiquidValue = budget.getItems().parallelStream().map(Item::getProduct)
				.map(p -> p.getPriceData().getTotalValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		BigDecimal newStValue = budget.getItems().parallelStream().map(Item::getProduct)
				.map(p -> p.getPriceData().getTotalStValue())
				.reduce(BigDecimal.ZERO, (a, b) -> a.add(b), (a, b) -> a.add(b));
		budget.setGrossValue(newGrossValue);
		budget.setLiquidValue(newLiquidValue);
		budget.setStValue(newStValue);
	}

	@Override
	public void removeItem(Order budget, Item item) {
		if (budget.removeItem(item)) {
			sumStValue(budget);
			ProductPriceData priceData = item.getProduct().getPriceData();
			BigDecimal itemGrossValue = priceData.getTotalGrossValue();
			BigDecimal itemStValue = priceData.getTotalStValue();
			BigDecimal itemTValue = priceData.getTotalValue();
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
			ProductPriceData priceData = item.getProduct().getPriceData();
			if (order.getGrossValue() == null)
				order.setGrossValue(priceData.getTotalGrossValue());
			else
				order.setGrossValue(order.getGrossValue().add(priceData.getTotalGrossValue()));
			if (order.getLiquidValue() == null)
				order.setLiquidValue(priceData.getTotalValue());
			else
				order.setLiquidValue(order.getLiquidValue().add(priceData.getTotalValue()));
			if (order.getStValue() == null)
				order.setStValue(priceData.getTotalStValue());
			else
				order.setStValue(order.getStValue().add(priceData.getTotalStValue()));
		}
	}

//	@Override
//	public void setDiscount(Order budget, BigDecimal discount) throws CustomerNotAllowed {
//		budget.setGlobalDiscount(discount);
//		if (budget.getItems().size() > 0) {
//			itemService.applyGlobalDiscount(budget.getItems(), discount);
//			this.calculateTotals(budget);
//		}
//
//	}

	@Override
	public void merge(Order originObj, Order newObj) {
		if (originObj.getCode() == null & newObj.getCode() != null)
			originObj.setCode(newObj.getCode());
		if (originObj.getCreatedAt() == null && newObj.getCreatedAt() != null)
			originObj.setCreatedAt(newObj.getCreatedAt());
		this.addItem(originObj, newObj.getItems());

	}

//	@Override
//	public void lineDiscount(Order order, ItemLineDiscountForm form) {
//		this.itemService.applyLineDiscount(order.getItems(), form);
//		this.calculateTotals(order);
//	}

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
			BigDecimal stValue = order.getItems().stream().map(Item::getProduct)
					.map(i -> i.getPriceData().getTotalStValue()).reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
			order.setStValue(stValue);
		}

	}

	@Override
	public void addProduct(Order order, Product product) {
		this.addItem(order, new Item(product));

	}

	@Override
	public void addProducts(Order order, BatchProductSearchDataWrapper wrapper) {
		this.addProducts(order,
				wrapper.getProducts().stream().map(BatchProductSearchData::getProduct).collect(Collectors.toList()));
	}

	@Override
	public void addProducts(Order order, Collection<Product> products) {
		products.parallelStream().map(Item::new).peek(i -> order.addItem(i))
				.filter(i -> order.getItems().contains(i)).forEach(i -> {
					synchronized (order) {
						ProductPriceData priceData = i.getProduct().getPriceData();
						BigDecimal oldStValue = order.getStValue();
						BigDecimal oldValue = order.getLiquidValue();
						BigDecimal oldGross = order.getGrossValue();
						if (oldStValue != null) {
							order.setStValue(oldStValue.add(priceData.getTotalStValue()));
						} else
							order.setStValue(priceData.getTotalStValue());

						if (oldValue != null) {
							order.setLiquidValue(oldValue.add(priceData.getTotalValue()));
						} else {
							order.setLiquidValue(priceData.getTotalValue());
						}
						if (oldGross != null) {
							order.setGrossValue(oldGross.add(priceData.getTotalGrossValue()));
						} else
							order.setGrossValue(priceData.getTotalGrossValue());
					}
				});
		;

	}

}
