package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.ProductCalculator;
import com.farawaybr.portal.service.crud.ProductService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.CustomerOnOrder;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.ProspectCustomerOnOrder;

@ViewScoped
@Named
public class ProductSearchComponentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3733963320781310655L;

	private String codeToSearch;

	private ProductService productService;
	private ProductCalculator productCalculator;
	private Product product;
	private int newQuantity;
	private boolean duplicateProduct;

	@Inject
	public ProductSearchComponentController(ProductService productService, ProductCalculator productCalculator) {
		super();
		this.productService = productService;
		this.productCalculator = productCalculator;
	}

	public void search(CustomerOnOrder customer, List<Product> products) {
		try {
			productService.findByCode(codeToSearch, customer.getCode(), customer.getStore(), customer.getStore(),
					customer instanceof ProspectCustomerOnOrder
							? ((ProspectCustomerOnOrder) customer).getSellerType().getType()
							: null,
					customer.getType()).ifPresentOrElse(product -> {
						this.product = product;
						FacesUtils.ajaxUpdate("manage-product-content");
						FacesUtils.executeScript("$('#footer').show()");
						this.newQuantity = product.getPriceData().getQuantity();
						this.duplicateProduct = products == null ? false
								: products.parallelStream().anyMatch(
										p -> p.getCommercialCode().equalsIgnoreCase(product.getCommercialCode()));

					}, () -> {
						FacesUtils.error(null, "Produto não localizado", null, "growl");
						product = null;
					});
		} catch (ExecutionException | InterruptedException e) {
			// TODO Auto-generated catch block
			FacesUtils.fatal(null, "Error interno", "Tente novamente mais tarde", "growl");
			e.printStackTrace();
		}
	}

	public void notifyObserver(ProductSearchObserver observer) {
		if (product != null) {
			observer.onConfirm(product);
			product = null;
			codeToSearch = null;
			return;
		}
		FacesUtils.warn(null, "Produto não adicionado!", "Pesquise o produto antes de confirmar", "growl");
	}

	public void onQuantityChange() {
		if (product != null)
			productCalculator.quantity(newQuantity, product.getPriceData());
	}

	public void confirmCancel() {
		product = null;
	}

	public String getCodeToSearch() {
		return codeToSearch;
	}

	public void setCodeToSearch(String codeToSearch) {
		this.codeToSearch = codeToSearch;
	}

	public Product getProduct() {
		return product;
	}

	public int getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}

	public boolean isDuplicateProduct() {
		return duplicateProduct;
	}
}
