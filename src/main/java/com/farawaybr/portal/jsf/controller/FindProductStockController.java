package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.crud.ProductService;
import com.farawaybr.portal.vo.Product;

@RequestScoped
@Named
public class FindProductStockController {

	@Inject
	private ProductService productService;

	public void find(Product product) {
		productService.findStock(product);
	}
}
