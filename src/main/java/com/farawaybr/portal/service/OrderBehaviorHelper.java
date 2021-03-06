package com.farawaybr.portal.service;

import java.util.Collection;
import java.util.List;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.vo.CustomerOnOrder;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Order;
import com.farawaybr.portal.vo.Product;

public interface OrderBehaviorHelper {

	/**
	 * Calculate the totals for that object based on the item totals.
	 * 
	 * @param budget
	 */
	void calculateTotals(Order budget);

	void removeItem(Order budget, Item itemToRemove);

	void addItem(Order order, Item produc);

	void addItem(Order order, Collection<Item> items);

	void addProduct(Order order, Product product);

	void addProducts(Order order, BatchProductSearchDataWrapper wrapper);

	void addProducts(Order order, Collection<Product> products);

	default void setCustomer(Order order, CustomerOnOrder customer) {
		if (order.getItems() != null && order.getItems().size() >= 1)
			throw new UnsupportedOperationException(
					"You can't set a client at this moment. Because this budget has many items.");
		order.setCustomerOnOrder(customer);
	}

	/**
	 * Set global discount.Only for PROSPECT customers!
	 * 
	 * @param budget
	 * @param discount
	 */
//	void setDiscount(Order budget, BigDecimal discount) throws CustomerNotAllowed;

	void merge(Order originalObj, Order newObj);

//	void lineDiscount(Order order, ItemLineDiscountForm form);

	void removeItems(Order order, List<Item> itemsToCompareAndRemove);

	void sumStValue(Order order);

}
