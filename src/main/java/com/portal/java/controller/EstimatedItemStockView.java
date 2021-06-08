package com.portal.java.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.java.dto.EstimatedItemDTO;
import com.portal.java.service.ResourceBundleServiceImpl;

@RequestScoped
@Named
public class EstimatedItemStockView {

	private String msgAvaliableStock;

	@Inject
	private ResourceBundleServiceImpl resourceBundleServiceImpl;

	public String getMsgAvaliableStock() {
		return msgAvaliableStock;
	}

	public void setMsgAvaliableStock(EstimatedItemDTO estimatedItem) {
		this.msgAvaliableStock = resourceBundleServiceImpl.getMessage("quantiade_em_estoque",
				estimatedItem.getAvaliableStock(), estimatedItem.getCommercialCode());
	}

}