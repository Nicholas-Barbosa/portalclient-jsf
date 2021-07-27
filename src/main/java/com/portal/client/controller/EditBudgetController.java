package com.portal.client.controller;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.service.BudgetService;
import com.portal.client.util.jsf.FacesUtils;

@Named
@RequestScoped
public class EditBudgetController {

	private BudgetService budgetService;

	private BudgetFullProjection budget;

	private String budgetID;

	@Inject
	public EditBudgetController(BudgetService budgetService) {
		super();
		this.budgetService = budgetService;
	}

	public void getById() {
		try {
			budgetService.findByCode(budgetID).ifPresentOrElse(b -> {
				FacesUtils.info(null, "Orçamento encontrado", null, "growl");
				budget = b;
			}, () -> {
				FacesUtils.error(null, "Orçamento não encontrado", "Código inexistente!", "growl");
			});
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			FacesUtils.fatal(null, "Error", e.getMessage(), "growl");
		}
	}

	public String getBudgetID() {
		return budgetID;
	}

	public void setBudgetID(String budgetID) {
		this.budgetID = budgetID;
	}

	public BudgetFullProjection getBudget() {
		return budget;
	}
}
