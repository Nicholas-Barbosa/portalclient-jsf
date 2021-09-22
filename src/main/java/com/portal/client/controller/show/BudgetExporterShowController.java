package com.portal.client.controller.show;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;

@RequestScoped
@Named
public class BudgetExporterShowController implements ShowController<Budget> {

	@Inject
	private HttpSession session;

	@Override
	public void show(Budget p) {
		if (p == null || p.getItems().size() == 0) {
			FacesUtils.error(null, "Objeto de pedido não está pronto",
					"Entre com as informações necessárias para criar um pedido/orçamento.");
			return;
		}
		session.setAttribute("budget-toexport", p);
		Map<String, Object> options = Map.of("responsive", true, "closeable", false, "modal", true, "contentWidth",
				"20vw", "contentHeight", "25vh");
		FacesUtils.openViewOnDialog(options, "orderExport");
	}

}
