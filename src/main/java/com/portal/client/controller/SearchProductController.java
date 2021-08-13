package com.portal.client.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.portal.client.dto.CustomerOnOrder.CustomerType;
import com.portal.client.dto.Product;
import com.portal.client.service.crud.ProductService;
import com.portal.client.util.jsf.FacesUtils;

@ViewScoped
@Named
public class SearchProductController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3733963320781310655L;

	private String state, productCodeToSearch, customerCode, customerStore, customerSellerType;

	private CustomerType customerType;
	private ProductService productService;

	private Product product;

	@Inject
	public SearchProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	public void search() {
		productService
				.findByCode(productCodeToSearch, customerCode, customerStore, state, customerSellerType, customerType)
				.ifPresentOrElse(product -> {
					this.product = product;
					FacesUtils.ajaxUpdate("manage-product-content");
					FacesUtils.executeScript("$('#footer').show()");
				}, () -> FacesUtils.error(null, "Produto não localizado", null, "growl"));
	}

	public void confirm() {
		PrimeFaces.current().dialog().closeDynamic(java.util.Optional.ofNullable(product));
		product = null;
	}

	public void cancel() {
		product = null;
		PrimeFaces.current().dialog().closeDynamic(java.util.Optional.empty());
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public void setCustomerStore(String customerStore) {
		this.customerStore = customerStore;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCustomerSellerType() {
		return customerSellerType;
	}

	public void setCustomerSellerType(String customerSellerType) {
		this.customerSellerType = customerSellerType;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getProductCodeToSearch() {
		return productCodeToSearch;
	}

	public void setProductCodeToSearch(String productCodeToSearch) {
		this.productCodeToSearch = productCodeToSearch;
	}

	public Product getProduct() {
		return product;
	}
}
