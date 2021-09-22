package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage;
import com.portal.client.vo.ProductTechDetail;

public interface ContractProductBuilder {
	public ContractProductBuilder withCode(String code);

	public ContractProductBuilder withCommercialCode(String commercialCode);

	public ContractProductBuilder withApplicability(String applicability);

	public ContractProductBuilder withLine(String line);

	public ContractProductBuilder withAcronymLine(String acronymLine);

	public ContractProductBuilder withMultiple(int multiple);

	public ContractProductBuilder withCommercialBlock(boolean commercialBlock);

	public ContractProductBuilder withImage(ProductImage image);

	public ContractProductBuilder withUnitStValue(BigDecimal unitStValue);

	public ContractProductBuilder withUnitValue(BigDecimal unitValue);

	public ContractProductBuilder withUnitGrossValue(BigDecimal unitGrossValue);

	public ContractProductBuilder withDescription(String description);

	public ContractProductBuilder withTechDetail(ProductTechDetail t);

	public ContractProductBuilder withQuantity(int quantity);

	public ContractProductBuilder withStock(int stock);

	Product build();
}
