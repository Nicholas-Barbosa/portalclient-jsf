package com.farawaybr.portal.jsf.controller.components;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.StreamedContent;

import com.farawaybr.portal.exceptionhandler.netowork.NetworkExceptionJoinPointCut;
import com.farawaybr.portal.service.InvoiceService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Danfe;
import com.farawaybr.portal.vo.Invoice;

@ViewScoped
@Named
@NetworkExceptionJoinPointCut
public class DanfeComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6701542337862777110L;

	@Inject
	private InvoiceService service;

	private StreamedContent danfe;

	public void load(Invoice invoice, String invoiceNumber, String invoiceSerie) {
		if (invoice != null) {
			this.danfeOptional(service.findByInvoice(invoice));
			return;
		}
		this.danfeOptional(service.findByInvoice(invoiceNumber, invoiceSerie));

	}

	private void danfeOptional(Optional<Danfe> danfeOptional) {
		danfeOptional.ifPresentOrElse(d -> {
			PrimeFaces.current().ajax().addCallbackParam("danfe", d.getBase64Streams());
			d = null;
		}, () -> {
			FacesUtils.error(null, "Danfe não encontrada", null, "growl");

		});
	}

	public StreamedContent getDanfe() {
		return danfe;
	}
}
