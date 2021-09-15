package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.dto.ProductValue;
import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public class ItemBuilder implements ContractItemBuilder {

	private Product product;
	private ItemValue itemValue;

	public static ItemBuilder getInstance() {
		return new ItemBuilder();
	}

	public static Item product(Product product) {
		ItemBuilder builder = new ItemBuilder();
		ProductValue productValue = product.getValue();
		builder.withProduct(product);
		return builder.withProduct(product)
				.withValue(ItemValueBuilder.getInstance().withQuantity(productValue.getQuantity())
						.withUnitGrossValue(productValue.getUnitGrossValue())
						.withUnitStValue(productValue.getUnitStValue()).withUnitValue(productValue.getUnitValue())
						.withTotalGrossValue(productValue.getTotalGrossValue())
						.withTotalStValue(productValue.getTotalStValue()).withTotalValue(productValue.getTotalValue())
						.withLineDiscount(BigDecimal.ZERO).withGlobalDiscount(BigDecimal.ZERO).build())
				.build();
	}

	@Override
	public ItemBuilder withProduct(Product value) {
		this.product = value;
		return this;
	}

	@Override
	public ContractItemBuilder withValue(ItemValue value) {
		this.itemValue = value;
		return this;
	}

	@Override
	public Item build() {
		return new Item(product, itemValue);
	}

	public Product getProduct() {
		return product;
	}

	public ItemValue getItemValue() {
		return itemValue;
	}

}
