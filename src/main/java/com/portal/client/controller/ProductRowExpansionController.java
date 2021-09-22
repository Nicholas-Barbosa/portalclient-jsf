package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named
public class ProductRowExpansionController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8019017391911659693L;
	private String productStockMessage;

	public void loadProductStock() {
		
	}
	public String getProductStockMessage() {
		return productStockMessage;
	}
	
	
}
