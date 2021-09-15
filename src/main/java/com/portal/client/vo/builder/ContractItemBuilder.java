package com.portal.client.vo.builder;

import com.portal.client.vo.Item;
import com.portal.client.vo.ItemValue;
import com.portal.client.vo.Product;

public interface ContractItemBuilder {

	ContractItemBuilder withProduct(Product value);

	ContractItemBuilder withValue(ItemValue value);

	Item build();
}
