package com.portal.java.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class EstimatedItemDTO extends BaseProductDTO {

	private BigDecimal unitGrossValue;

	private BigDecimal totalGrossValue;

	private BigDecimal unitPrice;

	private BigDecimal totale;

	private BigDecimal stValue;

	private BigDecimal unitStValue;

	private BigDecimal discount;

	private int quantity;

	private Integer avaliableStock;

	@JsonbCreator
	public EstimatedItemDTO(@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue,
			@JsonbProperty("total_gross_value") BigDecimal totalGrossValue, @JsonbProperty("product_code") String code,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("unit_price") BigDecimal unitPrice,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totale,
			@JsonbProperty("st_value") BigDecimal stValue, @JsonbProperty("line_discount") BigDecimal discount,
			@JsonbProperty("available_stock") Integer avaliableStock) {
		super(code, null, commercialCode, null, null, null);
		this.avaliableStock = avaliableStock;
		this.unitGrossValue = unitGrossValue;
		this.totalGrossValue = totalGrossValue;
		this.unitPrice = unitPrice;
		this.totale = totale;
		this.stValue = stValue;
		this.discount = discount;
		this.quantity = quantity;
		this.avaliableStock = avaliableStock;
		this.unitStValue = stValue.divide(new BigDecimal(quantity), 2, RoundingMode.HALF_UP);
	}

	public EstimatedItemDTO(BigDecimal unitGrossValue, BigDecimal totalGrossValue, BigDecimal unitPrice,
			BigDecimal totale, BigDecimal stValue, BigDecimal unitStValue, BigDecimal discount, int quantity,
			Integer avaliableStock) {
		super();
		this.unitGrossValue = unitGrossValue;
		this.totalGrossValue = totalGrossValue;
		this.unitPrice = unitPrice;
		this.totale = totale;
		this.stValue = stValue;
		this.unitStValue = unitStValue;
		this.discount = discount;
		this.quantity = quantity;
		this.avaliableStock = avaliableStock;
	}

	public void setBaseProductAttr(BaseProductDTO base) {
		super.setDescription(base.getDescription());
		super.setMultiple(base.getMultiple());
		super.setInfo(base.getInfo());
	}

	public void setDescription(String description) {
		super.setDescription(description);
	}

	public BigDecimal getUnitGrossValue() {
		return unitGrossValue;
	}

	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getAvaliableStock() {
		return avaliableStock;
	}

	public final void bulkChangeTotales(BigDecimal totalGross, BigDecimal totalPrice, BigDecimal totalSt) {

	}

	public final void changeTotalGrossValue(BigDecimal newTotal) {
		this.totalGrossValue = newTotal;
	}

	public final void changeTotalPrice(BigDecimal newTotal) {
		this.totale = newTotal;
	}

	public final void changeTotalStValue(BigDecimal newTotal) {
		this.stValue = newTotal;
	}
}
