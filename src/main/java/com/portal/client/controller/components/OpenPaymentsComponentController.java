package com.portal.client.controller.components;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.OpenPaymentsPage.OpenPaymentDto;
import com.portal.client.service.crud.OpenPaymentService;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.ui.lazy.datamodel.OpenPaymentsLazyDataModel;
import com.portal.client.util.jsf.FacesUtils;

@RequestScoped
@Named
public class OpenPaymentsComponentController {

	@Inject
	private OpenPaymentService service;
	@Inject
	private OpenPaymentsComponentData data;
	private LazyBehaviorDataModel<OpenPaymentDto> payments = new OpenPaymentsLazyDataModel();
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

	public LazyBehaviorDataModel<OpenPaymentDto> getPayments() {
		return payments;
	}
}
