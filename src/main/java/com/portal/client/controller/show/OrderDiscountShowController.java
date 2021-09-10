package com.portal.client.controller.show;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Order;

@RequestScoped
@Named
public class OrderDiscountShowController implements ShowController<Order> {

	@Inject
	private HttpSession session;
	private final Map<String, List<String>> queryParams;
	private static final Map<String, Object> options;

	static {
		options = new HashMap<>();
		initOptions();
	}

	public OrderDiscountShowController() {
		queryParams = new HashMap<>();
	}

	private static void initOptions() {
		options.put("responsive", true);
		options.put("modal", true);
	}

	public void show(Order p, int tab) {
		session.setAttribute("order-todisc", p);
		queryParams.put("activeTab", List.of(tab + ""));
		FacesUtils.openViewOnDialog(options, "orderDiscounts", queryParams);
	}

	@Override
	public void show(Order p) {
		// TODO Auto-generated method stub

	}

}
