package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;

import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.BudgetSemiProjection;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.ui.lazy.datamodel.BudgetLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.util.jsf.FacesUtils;

@ViewScoped
@Named
public class BudgetListController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9093802333014411573L;

	private BudgetCrudService buService;

	private LazyBehaviorDataModel<BudgetSemiProjection> budgets;

	@Inject
	public BudgetListController(BudgetCrudService buService) {
		super();
		this.buService = buService;
	}

	public void loadBudgets(int page) {
		if (budgets == null)
			this.budgets = new BudgetLazyDataModel();
		try {
			LazyPopulatorUtils.populate(budgets, buService.findAll(page, 15));
			FacesUtils.addHeaderForResponse("isEmpty", false);
		} catch (NotFoundException e) {
			FacesUtils.addHeaderForResponse("isEmpty", true);
			FacesUtils.executeScript("$('#contentNotFound').show();$('#contentFound').hide();");
		}
	}

	public void onPage(PageEvent page) {
		this.loadBudgets(page.getPage() + 1);
	}

	public LazyBehaviorDataModel<BudgetSemiProjection> getBudgets() {
		return budgets;
	}

}
