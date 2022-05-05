package com.farawaybr.portal.repository;

import java.util.Optional;

import com.farawaybr.portal.vo.Danfe;

public interface DanfeRepository {

	Optional<Danfe> findByInvoiceNumber(String invoiceNumber, String invoiceSerie);
}
