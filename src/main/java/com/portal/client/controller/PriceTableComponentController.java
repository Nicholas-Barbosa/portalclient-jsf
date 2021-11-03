package com.portal.client.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.ProductPriceTabletWrapper.ProductPriceTable;
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

	private List<ProductPriceTable> tables;

	public void find(String customerCode, String customerStore) {
		service.find(customerCode, customerStore).ifPresentOrElse(collection -> {
			this.tables = collection;
			System.out.println("Achou!");
			FacesUtils.addHeaderForResponse("foundTable", true);
//			FacesUtils.executeScript(
//					"$('");
		}, () -> FacesUtils.addHeaderForResponse("foundTable", false));

	}

	public void export() {

	}

	public List<ProductPriceTable> getTables() {
		return tables;
	}
}
