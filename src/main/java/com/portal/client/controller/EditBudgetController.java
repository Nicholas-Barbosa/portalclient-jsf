package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class EditBudgetController {

	private String budgetID;

	public String getBudgetID() {
		return budgetID;
	}

	public void setBudgetID(String budgetID) {
		this.budgetID = budgetID;
	}
	
	
}
