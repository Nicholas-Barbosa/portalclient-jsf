package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.BudgetSemiProjection;
import com.portal.client.dto.BudgetSemiProjectionPage;
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
	private String keyFilter;
	private BudgetSemiProjectionPage page;
	private boolean filterOn;

	@Inject
	public BudgetListController(BudgetCrudService buService) {
		super();
		this.buService = buService;
	}

	public void clearFilter() {
		if (this.filterOn) {
			this.keyFilter = null;
			this.loadBudgets(1);
			this.filterOn = false;
		}
	}

	public void loadBudgets(int page) {
		this.filterOn = this.keyFilter != null && !keyFilter.isBlank() ? true : false;
		if (budgets == null)
			this.budgets = new BudgetLazyDataModel();
		buService.findAll(page, 15, keyFilter).ifPresentOrElse(p -> {
			LazyPopulatorUtils.populate(budgets, p);
			FacesUtils.addHeaderForResponse("isEmpty", false);
			this.page = (BudgetSemiProjectionPage) p;
		}, () -> {
			if (!this.filterOn) {
				FacesUtils.addHeaderForResponse("isEmpty", true);
				FacesUtils.executeScript("$('#contentNotFound').show();$('#contentFound').hide();");
			}
			this.budgets.clearCollection();
			this.page = null;
		});

	}

	public void onPage(PageEvent page) {
		this.loadBudgets(page.getPage() + 1);
	}

	public LazyBehaviorDataModel<BudgetSemiProjection> getBudgets() {
		return budgets;
	}

	public String getKeyFilter() {
		return keyFilter;
	}

	public void setKeyFilter(String keyFilter) {
		this.keyFilter = keyFilter;
	}

	public BudgetSemiProjectionPage getPage() {
		return page;
	}

	public boolean isFilterOn() {
		return filterOn;
	}
}
