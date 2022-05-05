package com.farawaybr.portal.jsf.controller.components;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.farawaybr.portal.dto.OpenPaymentsPage.OpenPaymentDto;
import com.farawaybr.portal.jsf.lazydata.AbstractLazyDataModel;
import com.farawaybr.portal.jsf.lazydata.LazyPopulatorUtils;
import com.farawaybr.portal.jsf.lazydata.OpenPaymentsLazyDataModel;
import com.farawaybr.portal.service.crud.OpenPaymentService;
import com.farawaybr.portal.util.jsf.FacesUtils;

@RequestScoped
@Named
public class OpenPaymentsComponentController {

	@Inject
	private OpenPaymentService service;
	@Inject
	private OpenPaymentsComponentData data;
	private AbstractLazyDataModel<OpenPaymentDto> payments = new OpenPaymentsLazyDataModel();
	private int pageSize = 10;

	public void findPayments(int page, String code, String store) {
		service.findByCustomerCodeStore(page, pageSize, code, store).ifPresentOrElse(paymentPage -> {
			LazyPopulatorUtils.populate(payments, paymentPage);
			data.setCode(code);
			data.setStore(store);
		}, () -> {
			FacesUtils.error(null, "Não há títulos para este cliente", null, "growl");
		});
	}

	public void onPage(PageEvent pageEvent) {
		this.findPayments(pageEvent.getPage() + 1, data.getCode(), data.getStore());

	}

	public AbstractLazyDataModel<OpenPaymentDto> getPayments() {
		return payments;
	}
}
