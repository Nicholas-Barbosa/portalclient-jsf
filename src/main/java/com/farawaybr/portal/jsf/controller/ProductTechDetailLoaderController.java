package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.crud.ProductService;
import com.farawaybr.portal.vo.Product;

@RequestScoped
@Named
public class ProductTechDetailLoaderController {

	@Inject
	private ProductService productService;

	public void load(Product product) {
		productService.loadTechDetails(product);
	}
}
