package com.portal.client.vo;

public class BudgetEstimatedResultSet {

	private boolean ok;
	private Budget budget;
	private BudgetEstimatedResult404Error error;
	
	public BudgetEstimatedResultSet(boolean ok, Budget budget, BudgetEstimatedResult404Error error) {
		super();
		this.ok = ok;
		this.budget = budget;
		this.error = error;
	}

	public boolean isOk() {
		return ok;
	}

	public Budget getBudget() {
		return budget;
	}

	public BudgetEstimatedResult404Error getError() {
		return error;
	}

}
