package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.dto.ProductValue;
import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public class ItemBuilder implements ContractItemBuilder {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;
	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;
	private int quantity;
	private Integer multiple;
	private Product product;

	public static ItemBuilder getInstance() {
		return new ItemBuilder();
	}

	public static Item product(Product product) {
		ItemBuilder builder = new ItemBuilder();
		ProductValue productValue = product.getValue();
		return builder.withProduct(product).withQuantity(productValue.getQuantity())
				.withUnitGrossValue(productValue.getUnitGrossValue()).withUnitStValue(productValue.getUnitStValue())
				.withUnitValue(productValue.getUnitValue()).withTotalGrossValue(productValue.getTotalGrossValue())
				.withTotalStValue(productValue.getTotalStValue()).withTotalValue(productValue.getTotalValue())
				.withLineDiscount(BigDecimal.ZERO).withGlobalDiscount(BigDecimal.ZERO).build();
	}

	@Override
	public ItemBuilder withGlobalDiscount(BigDecimal value) {
		this.budgetGlobalDiscount = value;
		return this;
	}

	@Override
	public ItemBuilder withLineDiscount(BigDecimal value) {
		this.lineDiscount = value;
		return this;
	}

	@Override
	public ItemBuilder withUnitStValue(BigDecimal value) {
		this.unitStValue = value;
		return this;
	}

	@Override
	public ItemBuilder withUnitGrossValue(BigDecimal value) {
		this.unitGrossValue = value;
		return this;
	}

	@Override
	public ItemBuilder withTotalStValue(BigDecimal value) {
		this.totalStValue = value;
		return this;
	}

	@Override
	public ItemBuilder withTotalValue(BigDecimal value) {
		this.totalValue = value;
		return this;
	}

	@Override
	public ItemBuilder withTotalGrossValue(BigDecimal value) {
		this.totalGrossValue = value;
		return this;
	}

	@Override
	public ItemBuilder withQuantity(int value) {
		this.quantity = value;
		return this;
	}

	@Override
	public ItemBuilder withUnitValue(BigDecimal value) {
		this.unitValue = value;
		return this;
	}

	@Override
	public ItemBuilder withProduct(Product value) {
		this.product = value;
		return this;
	}

	@Override
	public ItemBuilder withMultiple(Product value) {
		this.product = value;
		return this;
	}

	@Override
	public BigDecimal getBudgetGlobalDiscount() {
		return budgetGlobalDiscount;
	}

	@Override
	public BigDecimal getLineDiscount() {
		return lineDiscount;
	}

	@Override
	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	@Override
	public BigDecimal getUnitValue() {
		return unitValue;
	}

	@Override
	public BigDecimal getUnitGrossValue() {
		return unitGrossValue;
	}

	@Override
	public BigDecimal getTotalStValue() {
		return totalStValue;
	}

	@Override
	public BigDecimal getTotalValue() {
		return totalValue;
	}

	@Override
	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public Integer getMultiple() {
		return multiple;
	}

	public Product getProduct() {
		return product;
	}

	public Item build() {
		return new Item(product, new ItemValue(quantity, multiple, budgetGlobalDiscount, lineDiscount, unitStValue,
				unitValue, unitGrossValue, totalStValue, totalValue, totalGrossValue));
	}

}
