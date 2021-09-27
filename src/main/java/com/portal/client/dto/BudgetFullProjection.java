package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.helper.StringToDateParser;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

public class BudgetFullProjection extends Budget implements Page<Budget> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8855667707827734380L;

	private int page, pageSize, totalPages, totalItems;

	@JsonbCreator
	public static BudgetFullProjection ofJsonb(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget") String code,
			@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("client_order") String customerNumOrder,
			@JsonbProperty("representative_order") String representativeOrder, @JsonbProperty("message") String message,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("items") Set<ItemBudgetProjection> items,
			@JsonbProperty("discount") BigDecimal globalDiscount, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("page") int page, @JsonbProperty("page_size") int pageSize,
			@JsonbProperty("total_page") int totalPages, @JsonbProperty("total_items") int totalItems) {

		CustomerOnOrder customer = new CustomerOnOrder(customerCode, customerStore, null, null, null, null, null, null,
				null);
		return new BudgetFullProjection(customerNumOrder, representativeOrder, customer, code, null, liquidValue,
				grossValue, items.stream().map(BudgetFullProjection::castItem).collect(Collectors.toList()), message,
				globalDiscount, StringToDateParser.convert(createdAt), page, pageSize, totalPages, totalItems);

	}

	public BudgetFullProjection(String customerNumOrder, String repNumOrder, CustomerOnOrder customer, String code,
			BigDecimal stValue, BigDecimal liquidValue, BigDecimal grossValue, List<Item> items, String message,
			BigDecimal globalDiscount, LocalDate createdAt, int page, int pageSize, int totalPages, int totalItems) {
		super(code, customerNumOrder, repNumOrder, customer, grossValue, liquidValue, stValue, globalDiscount, items,
				message, createdAt);
		this.page = page;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.totalItems = totalItems;
	}

	private static Item castItem(ItemBudgetProjection item) {
		return (Item) item;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public int totalPages() {
		// TODO Auto-generated method stub
		return totalPages;
	}

	@Override
	public int totalItems() {
		// TODO Auto-generated method stub
		return totalItems;
	}

	@Override
	public Collection<Budget> getContent() {
		return List.of(this);
	}
}
