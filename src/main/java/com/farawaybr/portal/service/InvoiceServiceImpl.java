package com.farawaybr.portal.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.repository.DanfeRepository;
import com.farawaybr.portal.vo.Danfe;
import com.farawaybr.portal.vo.Invoice;

@ApplicationScoped
public class InvoiceServiceImpl implements InvoiceService {

	@Inject
	private DanfeRepository repository;

	@Override
	public  Optional<Danfe> findByInvoice(String invoiceNumber, String invoiceKey) {
		return repository.findByInvoiceNumber(invoiceNumber, invoiceKey);
	}

	@Override
	public Optional<Danfe> findByInvoice(Invoice invoice) {
		// TODO Auto-generated method stub
		return repository.findByInvoiceNumber(invoice.getNumber(), invoice.getSerie());
	}

}
