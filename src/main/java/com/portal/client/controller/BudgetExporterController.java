package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.dto.OrderExporterForm;
import com.portal.client.service.export.BudgetExportType;
import com.portal.client.service.export.SimpleBudgetExporterFactory;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;

@RequestScoped
@Named
public class BudgetExporterController {

	private boolean order;
	private Budget budgetToExport;
	private HttpSession httpSession;
	private SimpleBudgetExporterFactory budgetExporterFactory;
	private OrderExporterForm exportForm;

	@Inject
	public BudgetExporterController(HttpSession httpSession, SimpleBudgetExporterFactory budgetExporterFactory) {
		super();
		this.httpSession = httpSession;
		this.budgetExporterFactory = budgetExporterFactory;
		this.exportForm = new OrderExporterForm();
	}

	public void checkOrderToExport() {

	}

	public void export() {
		budgetToExport = (Budget) httpSession.getAttribute("budget-toexport");
		byte[] streams = budgetExporterFactory.getExporter(exportForm.getType()).export(budgetToExport);
		exportForm.checkFileExtension();
		FacesUtils.prepareResponseForDownloadOfStreams(getFileName(), streams, getFileType().getType());
	}

	public void close() {
		httpSession.removeAttribute("budget-toexport");
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

	public OrderExporterForm getExportForm() {
		return exportForm;
	}
}
