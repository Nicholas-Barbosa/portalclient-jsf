package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.service.crud.ProductService;
import com.portal.client.vo.Product;

@RequestScoped
@Named
public class ProductTechDetailLoaderController {

	@Inject
	private ProductService productService;

	public void load(Product product) {
		productService.loadTechDetails(product);
	}
}
