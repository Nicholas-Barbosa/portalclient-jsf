package com.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimatedDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3699045042053812861L;

	private BigDecimal liquidValue;

	private BigDecimal grossValue;

	private BigDecimal stTotal;

	private BigDecimal totalDiscount;

	private CustomerDTO customer;

	private Set<EstimatedItemDTO> items;

	public BudgetEstimatedDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public BudgetEstimatedDTO(@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("estimate") Set<EstimatedItemDTO> items) {
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.items = new HashSet<>(items);
		this.stTotal = calculateSt();
	}

	public BudgetEstimatedDTO(BigDecimal liquidValue, BigDecimal grossValue, BigDecimal stTotal,
			BigDecimal totalDiscount, Set<EstimatedItemDTO> items) {
		super();
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.stTotal = stTotal;
		this.totalDiscount = totalDiscount;
		this.items = items;
	}

	public final void bulkUpdateTotales(BigDecimal liquidValue, BigDecimal grossValue, BigDecimal stTotal,
			BigDecimal totalDiscount) {
		this.liquidValue = liquidValue;
		this.grossValue = grossValue;
		this.stTotal = stTotal;
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public BigDecimal getStTotal() {
		return stTotal;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public Set<EstimatedItemDTO> getItems() {
		return new HashSet<>(items);
	}
	

	public void removeItem(EstimatedItemDTO item) {
		this.items.remove(item);
	}
//	public void calcultaTotals() {
//		ExecutorService executor = null;
//		try {
//			executor = Executors.newFixedThreadPool(4);
//			executor.execute(() -> this.liquidValue = new BigDecimal(items.parallelStream()
//					.map(e -> e.getPrice().getTotale()).collect(Collectors.summingDouble(v -> v.doubleValue()))));
//			executor.execute(() -> this.grossValue = new BigDecimal(
//					items.parallelStream().map(e -> e.getPrice().getTotalGrossValue())
//							.collect(Collectors.summingDouble(v -> v.doubleValue()))));
//			executor.execute(() -> calculateSt());
//			executor.execute(() -> calculateTotalDiscount());
//		} finally {
//			executor.shutdown();
//			try {
//				executor.awaitTermination(10, TimeUnit.MICROSECONDS);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	public final void calculateTotalDiscount() {
//		this.totalDiscount = new BigDecimal(items.parallelStream().map(e -> e.getPrice().getDiscount())
//				.collect(Collectors.summingDouble(v -> v.doubleValue())));
//	}
//
	public final BigDecimal calculateSt() {
		return new BigDecimal(items.stream().mapToDouble(e -> e.getStValue().doubleValue()).sum());
	}

}
