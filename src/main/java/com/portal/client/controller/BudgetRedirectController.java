package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class BudgetRedirectController {

	private String budgetId;

	public String redirect() {
		return "editBudget?faces-redirect=true&budgetID=" + budgetId;
	}

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}
}
