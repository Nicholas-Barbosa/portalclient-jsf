package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class ProductStockMessageController {

	public String getMessage(Integer stock) {
		return stock == null || stock == 0 ? "Sem estoque"
				: stock == 1 ? " Há 1 disponível" : "Há " + stock + " disponíveis";
	}
}
