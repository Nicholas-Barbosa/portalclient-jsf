package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.dto.OrderExporterForm;
import com.portal.client.export.BudgetExporter;
import com.portal.client.export.OrderExportType;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Budget;

@RequestScoped
@Named
public class BudgetExporterController {

	private boolean order;
	private Budget budgetToExport;
	private HttpSession httpSession;
	private BudgetExporter orderExporter;
	private OrderExporterForm exportForm;

	@Inject
	public BudgetExporterController(HttpSession httpSession, BudgetExporter orderExporter) {
		super();
		this.httpSession = httpSession;
		this.orderExporter = orderExporter;
		this.exportForm = new OrderExporterForm();
	}

	public void checkOrderToExport() {

	}

	public void export() {
		budgetToExport = (Budget) httpSession.getAttribute("budget-toexport");
		byte[] streams = orderExporter.export(budgetToExport, exportForm.getType());
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

	public OrderExportType getFileType() {
		return exportForm.getType();
	}

	public void setFileType(OrderExportType type) {
		this.exportForm.setType(type);
	}

	public OrderExporterForm getExportForm() {
		return exportForm;
	}
}
