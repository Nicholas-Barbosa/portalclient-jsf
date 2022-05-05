package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.ResourceBundleServiceImpl;
import com.farawaybr.portal.vo.Product;

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
		this.msgAvaliableStock = resourceBundleServiceImpl.getMessage("quantiade_em_estoque",
				product.getStock(), product.getCommercialCode());
	}

}
