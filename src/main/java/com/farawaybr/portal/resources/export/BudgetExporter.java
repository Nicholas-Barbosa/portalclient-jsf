package com.farawaybr.portal.resources.export;

import com.farawaybr.portal.vo.Budget;

public interface BudgetExporter {

	byte[] export(Budget budget);

}
