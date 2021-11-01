package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;

public class PriceTableJasperDto {

	private String customerCode;

	private Set<PriceJasperDto> prices;

	public PriceTableJasperDto(String customerCode, Set<PriceJasperDto> prices) {
		super();
		this.customerCode = customerCode;
		this.prices = prices;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public Set<PriceJasperDto> getPrices() {
		return prices;
	}

	public static PriceTableJasperDto of(String customerCode, List<ProductPriceTable> table) {
		
		return null;
		
	}
	public static class PriceJasperDto {

		private String productCode, productDescriptio, productLine;
		private BigDecimal productValue, productSt, productGross;

		public PriceJasperDto(String productCode, String productDescriptio, String productLine, BigDecimal productValue,
				BigDecimal productSt, BigDecimal productGross) {
			super();
			this.productCode = productCode;
			this.productDescriptio = productDescriptio;
			this.productLine = productLine;
			this.productValue = productValue;
			this.productSt = productSt;
			this.productGross = productGross;
		}

		public String getProductCode() {
			return productCode;
		}

		public String getProductDescriptio() {
			return productDescriptio;
		}

		public String getProductLine() {
			return productLine;
		}

		public BigDecimal getProductValue() {
			return productValue;
		}

		public BigDecimal getProductSt() {
			return productSt;
		}

		public BigDecimal getProductGross() {
			return productGross;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PriceJasperDto other = (PriceJasperDto) obj;
			if (productCode == null) {
				if (other.productCode != null)
					return false;
			} else if (!productCode.equals(other.productCode))
				return false;
			return true;
		}

	}
}
