package com.portal.client.controller.show;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Product;

@RequestScoped
@Named
public class ProductTechDataShowController {

	private HttpSession httpSession;

	@Inject
	public ProductTechDataShowController(HttpSession httpSession) {
		super();
		this.httpSession = httpSession;
	}

	public void show(Product product) {
		if (product != null) {
			httpSession.setAttribute("product-techDetails", product);
			Map<String, Object> dialogOptions = Map.of("modal", true, "responsive", true, "onHide", "alert('close')",
					"contentWidth", "50vw", "contentHeight", "45vh", "closable", false, "showEffect", "fold",
					"hideEffect", "blind");
			FacesUtils.openViewOnDialog(dialogOptions, "/faces/productTechData");
			return;
		}
		throw new IllegalArgumentException("Product arg cannot be null!");
	}
}
