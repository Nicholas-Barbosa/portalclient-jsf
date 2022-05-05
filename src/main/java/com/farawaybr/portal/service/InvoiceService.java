package com.farawaybr.portal.service;

import java.util.Optional;

import com.farawaybr.portal.vo.Danfe;
import com.farawaybr.portal.vo.Invoice;

public interface InvoiceService {

	Optional<Danfe> findByInvoice(String invoiceNumber,String invoiceKey);
	
	Optional<Danfe> findByInvoice(Invoice invoice);
}
