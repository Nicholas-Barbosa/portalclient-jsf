package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.service.crud.OpenPaymentService;

@RequestScoped
@Named
public class OpenPaymentsComponentController {

	@Inject
	private OpenPaymentService service;

	private int pageSize = 10;

	public void test() {
		System.out.println("Test");
	}
	public void findPayments(int page, String code, String store) {
		service.findByCustomerCodeStore(page, pageSize, code, store).ifPresent(paymentPage -> {

		});
	}
}
