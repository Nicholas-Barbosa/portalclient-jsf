package com.portal.client.service;

import java.util.Optional;

import com.portal.client.vo.Danfe;
import com.portal.client.vo.Invoice;

public interface InvoiceService {

	Optional<Danfe> findByInvoice(String invoiceNumber,String invoiceKey);
	
	Optional<Danfe> findByInvoice(Invoice invoice);
}
