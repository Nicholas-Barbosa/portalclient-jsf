package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.service.crud.ProductService;
import com.portal.client.vo.Product;

@RequestScoped
@Named
public class FindProductStockController {

	@Inject
	private ProductService productService;

	public void find(Product product) {
		productService.findStock(product);
	}
}
