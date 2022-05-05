package com.farawaybr.portal.jsf.controller.show;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.OrderBehaviorHelper;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Order;

@Named
@RequestScoped
public class CustomerDynamicDialog implements ShowController<String> {

	@Inject
	private OrderBehaviorHelper orderHelper;

	@Override
	public void show(String p) {
		Map<String, Object> options = Map.of("modal", true, "responsive", true, "contentWidth", "50vw", "contentHeight",
				"55vh");
		Map<String, List<String>> queryParams = Map.of("keyword", List.of(p));
		FacesUtils.openViewOnDialog(options, "/faces/customerSearch", queryParams);

	}
	
	public void handleReturn(Order order) {
		orderHelper.setCustomer(order, null);
	}
}
