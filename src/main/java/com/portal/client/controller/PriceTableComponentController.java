package com.portal.client.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTable;
import com.portal.client.resources.export.BudgetExportType;
import com.portal.client.resources.export.ProductPriceTableExporter;
import com.portal.client.service.ProductPriceTableService;
import com.portal.client.util.jsf.FacesUtils;

@ViewScoped
@Named
public class PriceTableComponentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6213941017088855634L;

	@Inject
	private ProductPriceTableService service;

	@Inject
	private ProductPriceTableExporter exporter;

	private List<ProductPriceTable> tables;

	private StreamedContent file;
	
	public void find(String customerCode, String customerStore) {
		service.find(customerCode, customerStore).ifPresentOrElse(collection -> {
			this.tables = collection;
			System.out.println("Achou!");
			FacesUtils.addHeaderForResponse("foundTable", true);
		}, () -> FacesUtils.addHeaderForResponse("foundTable", false));

	}

	public void export(String customerCode) {
		byte[] excel = exporter.toExcel(customerCode, tables);
		FacesUtils.prepareResponseForDownloadOfStreams("tabelaPreços", excel, BudgetExportType.EXCEL.getType());
	}

	public List<ProductPriceTable> getTables() {
		return tables;
	}
}
