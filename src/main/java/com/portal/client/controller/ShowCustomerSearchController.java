package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ShowCustomerSearchController implements ShowController<String> {

	public void show(String keyword) {

	}
}
