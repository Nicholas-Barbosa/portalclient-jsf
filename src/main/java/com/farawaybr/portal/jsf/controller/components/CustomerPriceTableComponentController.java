package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.resources.export.BudgetExportType;
import com.farawaybr.portal.resources.export.CustomerPriceTableExporter;
import com.farawaybr.portal.service.CustomerService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Customer;

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
	private CustomerPriceTableExporter exporter;

	public void find(Customer customer) {
		if (service.findPriceTable(customer)) {
			FacesUtils.addHeaderForResponse("foundTable", true);
			return;
		}
		FacesUtils.addHeaderForResponse("foundTable", false);

	}

	public void export(Customer customer) {
		byte[] excel = exporter.toExcel(customer);
		FacesUtils.prepareResponseForDownloadOfStreams("tabelaPreços.xlsx", excel, BudgetExportType.EXCEL.getType());
	}

}
