package com.portal.client.resources.export;

import com.portal.client.vo.Budget;

public interface BudgetExporter {

	byte[] export(Budget budget);

}
