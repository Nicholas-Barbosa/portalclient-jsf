package com.portal.java.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.java.dto.Product;
import com.portal.java.service.ResourceBundleServiceImpl;

@RequestScoped
@Named
public class ProductStockView {

	private String msgAvaliableStock;

	@Inject
	private ResourceBundleServiceImpl resourceBundleServiceImpl;

	public String getMsgAvaliableStock() {
		return msgAvaliableStock;
	}

	public void setMsgAvaliableStock(Product product) {
		System.out.println("product stock " +product.getAvaliableStock());
		this.msgAvaliableStock = resourceBundleServiceImpl.getMessage("quantiade_em_estoque",
				product.getAvaliableStock(), product.getCommercialCode());
	}

}
