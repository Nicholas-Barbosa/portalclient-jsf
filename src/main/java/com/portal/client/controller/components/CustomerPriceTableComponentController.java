package com.portal.client.controller.components;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.Customer;
import com.portal.client.resources.export.BudgetExportType;
import com.portal.client.resources.export.ProductPriceTableExporter;
import com.portal.client.service.CustomerService;
import com.portal.client.util.jsf.FacesUtils;

@RequestScoped
@Named
public class CustomerPriceTableComponentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6213941017088855634L;

	@Inject
	private CustomerService service;

	@Inject
	private ProductPriceTableExporter exporter;

	public void find(Customer customer) {
		if (service.findPriceTable(customer)) {
			FacesUtils.addHeaderForResponse("foundTable", true);
			return;
		}
		FacesUtils.addHeaderForResponse("foundTable", false);

	}

	public void export(Customer customer) {
		byte[] excel = exporter.toExcel(customer);
		FacesUtils.prepareResponseForDownloadOfStreams("tabelaPreços", excel, BudgetExportType.EXCEL.getType());
	}

}
