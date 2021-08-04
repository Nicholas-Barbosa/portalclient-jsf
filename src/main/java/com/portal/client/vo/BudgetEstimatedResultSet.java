package com.portal.client.vo;

import com.portal.client.dto.BaseBudget;

public class BudgetEstimatedResultSet {

	private boolean ok;
	private BaseBudget budget;
	private BudgetEstimatedResult404Error error;
	
	public BudgetEstimatedResultSet(boolean ok, BaseBudget budget, BudgetEstimatedResult404Error error) {
		super();
		this.ok = ok;
		this.budget = budget;
		this.error = error;
	}

	public boolean isOk() {
		return ok;
	}

	public BaseBudget getBudget() {
		return budget;
	}

	public BudgetEstimatedResult404Error getError() {
		return error;
	}

}
