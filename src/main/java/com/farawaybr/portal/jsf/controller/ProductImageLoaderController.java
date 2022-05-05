package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.crud.ProductService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProductImage.ImageInfoState;

@Named
@RequestScoped
public class ProductImageLoaderController {

	private ProductService productService;

	@Inject
	public ProductImageLoaderController(ProductService productService) {
		super();
		this.productService = productService;
	}

	public void loadImage(Product product) {
		productService.loadImage(product);
		FacesUtils.addHeaderForResponse("Image-State", product.getImage().getCurrentState());
		if (product.getImage().getCurrentState() == ImageInfoState.NOT_FOUND) {
			FacesUtils.error(null, "Imagem não encontrada para " + product.getCommercialCode(), null, "growl");
		}
	}
}
