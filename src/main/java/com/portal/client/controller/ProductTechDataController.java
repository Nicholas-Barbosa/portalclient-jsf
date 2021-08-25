package com.portal.client.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;

import com.portal.client.service.crud.ProductService;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Product;
import com.portal.client.vo.ProductTechDetail;

@Named
@RequestScoped
public class ProductTechDataController {

	private ProductService productService;

	private Product product;

	private HttpSession httpSession;

	@Inject
	public ProductTechDataController(ProductService productService, HttpSession httpSession) {
		super();
		this.productService = productService;
		this.httpSession = httpSession;
	}

	@PostConstruct
	public void init() {
		product = (Product) httpSession.getAttribute("product-techDetails");
	}

	public void loadDetails() {
		try {
			productService.findTechDetails(getCode(), product);
			System.out.println("link " + product.getLink());
		} catch (ProcessingException e) {
			if (e.getCause() instanceof NotAuthorizedException) {
				FacesUtils.fatal(null, "Token inválido", "Contate o suporte!", "growl");
				return;
			}
			FacesUtils.error(null, "Erro de rede", null, "growl");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	public void closeDialog() {
		PrimeFaces.current().dialog().closeDynamic(null);
		httpSession.removeAttribute("product-techDetails");
	}

	public String getCode() {
		return product != null ? product.getCommercialCode() : null;
	}

	public Product getProduct() {
		return product;
	}

	public ProductTechDetail getTechDetail() {
		return product == null ? null : product.getProductTechDetail();
	}
}
