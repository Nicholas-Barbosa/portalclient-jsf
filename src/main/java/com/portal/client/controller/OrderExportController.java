package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.dto.OrderExporterForm;
import com.portal.client.export.OrderExportType;
import com.portal.client.export.OrderExporter;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Order;

@RequestScoped
@Named
public class OrderExportController {

	private boolean order;
	private Order orderToExport;
	private HttpSession httpSession;
	private OrderExporter orderExporter;
	private OrderExporterForm exportForm;

	@Inject
	public OrderExportController(HttpSession httpSession, OrderExporter orderExporter) {
		super();
		this.httpSession = httpSession;
		this.orderExporter = orderExporter;
		this.exportForm = new OrderExporterForm();
	}

	public void export() {
		orderToExport = (Order) httpSession.getAttribute("order-toexport");
		byte[] streams = orderExporter.export(orderToExport, exportForm.getType());
		exportForm.checkFileExtension();
		FacesUtils.prepareResponseForDownloadOfStreams(getFileName(), streams, getFileType().getType());
	}

	public void close() {
		httpSession.removeAttribute("order-toexport");
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
