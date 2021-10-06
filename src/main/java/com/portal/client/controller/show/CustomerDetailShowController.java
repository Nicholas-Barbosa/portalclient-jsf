package com.portal.client.controller.show;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.portal.client.dto.Customer;
import com.portal.client.util.jsf.FacesUtils;

@Named
@RequestScoped
public class CustomerDetailShowController implements ShowController<Customer> {

	public void show(Customer customer) {
		if (customer != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customer_to_detail", customer);
			Map<String, Object> options = new HashMap<>();
			options.put("modal", true);
			options.put("draggable", true);
			options.put("position", "center");
			options.put("contentWidth", "60vw");
			options.put("contentHeight", "45vh");
			options.put("responsive", true);
			PrimeFaces.current().dialog().openDynamic("/faces/customerDetail", options, null);
			return;
		}
		FacesUtils.error(null, "Cliente não selecionado", "Não foi selecionado nenhum cliente neste ínterim.", "growl");
	}

}
