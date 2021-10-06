package com.portal.client.controller.show;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.util.jsf.FacesUtils;

@RequestScoped
@Named
public class ProductSearchShowController implements ShowController<CustomerOnOrder> {

	@Override
	public void show(CustomerOnOrder customer) {
		if (customer != null) {
			Map<String, List<String>> queryParams = new HashMap<>();
			queryParams.put("customerType", List.of(customer.getType().name()));
			if (customer instanceof ProspectCustomerOnOrder) {
				ProspectCustomerOnOrder prospCustomer = (ProspectCustomerOnOrder) customer;
				queryParams.put("customerPropState", List.of(prospCustomer.getState()));
				queryParams.put("customerPropSelType", List.of(prospCustomer.getSellerType().getType()));
			} else {
				queryParams.put("customerCode", List.of(customer.getCode()));
				queryParams.put("customerStore", List.of(customer.getStore()));
			}

			FacesUtils.openViewOnDialog(
					Map.of("modal", true, "responsive", true, "contentWidth", "50vw", "contentHeight", "65vh"),
					"/faces/productSearch", queryParams);
			return;
		}
		FacesUtils.error(null, "Cliente nullo", "Selecione um cliente para escolher um produto", "growl");
	}

}
