package com.portal.client.controller.show;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Order;

@Named
@RequestScoped
public class ItemImportShowController implements ShowController<CustomerOnOrder> {

	@Override
	public void show(CustomerOnOrder p) {
		if (p != null) {
			if (p instanceof ProspectCustomerOnOrder) {
				FacesUtils.warn(null, "Operação indisponível",
						"Esta operação não está disponivel para clientes de tipo prospect.", "growl");
			}
			FacesUtils.openViewOnDialog(
					Map.of("modal", true, "responsive", true, "contentWidth", "98vw", "contentHeight", "80vh"),
					"/faces/itemImport", Map.of("customerCode", List.of(p.getCode()), "customerStore", List.of(p.getStore()),
							"onDialog", List.of("true")));
			return;
		}
		FacesUtils.error(null, "Cliente não selecionado", null, "growl");

	}

	public void show(Order order) {
		if (order !=null && order.getCustomerOnOrder() != null) {
			if (order.getCustomerOnOrder() instanceof ProspectCustomerOnOrder) {
				FacesUtils.warn(null, "Operação indisponível",
						"Esta operação não está disponivel para clientes de tipo prospect.", "growl");
			}
			FacesUtils.openViewOnDialog(
					Map.of("modal", true, "responsive", true, "contentWidth", "98vw", "contentHeight", "80vh"),
					"/faces/itemImport", Map.of("customerCode", List.of(order.getCustomerOnOrder().getCode()), "customerStore", List.of(order.getCustomerOnOrder().getStore()),
							"onDialog", List.of("true")));
			return;
		}
		FacesUtils.error(null, "Cliente não selecionado", null, "growl");

	}
}
