package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.service.crud.ProductService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductImage.ImageInfoState;

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
		System.out.println("Load image! " +product.getImage().getCurrentState());
		FacesUtils.addHeaderForResponse("Image-State", product.getImage().getCurrentState());
		if (product.getImage().getCurrentState() == ImageInfoState.NOT_FOUND) {
			FacesUtils.error(null, "Imagem não encontrada para " + product.getCommercialCode(), null, "growl");
		}
	}
}
