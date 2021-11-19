package com.portal.client.resources.export;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class SimpleBudgetExporterFactory {

	@Inject
	private Instance<BudgetExporter> instances;

	public BudgetExporter getExporter(BudgetExportType type) {
		switch (type) {
		case PDF:
			return null;
		default:
			return null;
		}
	}
}
