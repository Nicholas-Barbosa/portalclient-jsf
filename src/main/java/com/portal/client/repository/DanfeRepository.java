package com.portal.client.repository;

import java.util.Optional;

import com.portal.client.vo.Danfe;

public interface DanfeRepository {

	Optional<Danfe> findByInvoiceNumber(String invoiceNumber, String invoiceSerie);
}
