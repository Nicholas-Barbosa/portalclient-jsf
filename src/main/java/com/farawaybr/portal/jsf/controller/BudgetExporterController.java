package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.dto.BudgetExporterForm;
import com.farawaybr.portal.resources.export.BudgetExportType;
import com.farawaybr.portal.resources.export.SimpleBudgetExporterFactory;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Budget;

@RequestScoped
@Named
public class BudgetExporterController {

	private boolean order;
	private SimpleBudgetExporterFactory budgetExporterFactory;
	private BudgetExporterForm exportForm;

	@Inject
	public BudgetExporterController(SimpleBudgetExporterFactory budgetExporterFactory) {
		super();
		this.budgetExporterFactory = budgetExporterFactory;
		this.exportForm = new BudgetExporterForm();
	}

	public void checkOrderToExport() {

	}

	public void export(Budget budget) {
		if (budget.getItems() != null && !budget.getItems().isEmpty()) {
			byte[] streams = budgetExporterFactory.getExporter(exportForm.getType()).export(budget);
			exportForm.appendExtension();
			FacesUtils.prepareResponseForDownloadOfStreams(getFileName(), streams, getFileType().getType());
			return;
		}
		FacesUtils.error(null, "Orçamento sem itens!", null, "growl");
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public String getFileName() {
		return exportForm.getFileName();
	}

	public void setFileName(String fileName) {
		this.exportForm.setFileName(fileName);
	}

	public BudgetExportType getFileType() {
		return exportForm.getType();
	}

	public void setFileType(BudgetExportType type) {
		this.exportForm.setType(type);
	}

	public BudgetExporterForm getExportForm() {
		return exportForm;
	}
}
