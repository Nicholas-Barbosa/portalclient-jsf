package com.portal.client.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;
import com.portal.client.dto.DanfeDataDto;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.vo.Danfe;

@ApplicationScoped
public class DanfeRepositoryImpl implements DanfeRepository {

	@Inject
	private TokenedRestClient tokenedRestClient;

	@Inject
	private ProtheusAPIHelper protheusApi;

	@NotFoundOptionalEmptyJoinPointCut
	@Override
	public Optional<Danfe> findByInvoiceNumber(String invoiceNumber, String invoiceSerie) {
		// TODO Auto-generated method stub
		DanfeDataDto data = tokenedRestClient.get(
				protheusApi.buildEndpoint("/danfe/{invoiceNumber}/series/{invoiceSerie}"), protheusApi.getToken(),
				protheusApi.getTokenPrefix(), DanfeDataDto.class, null,
				Map.of("invoiceNumber", invoiceNumber, "invoiceSerie", invoiceSerie), "application/json");
		StringBuilder base64 = new StringBuilder(data.getStreamBase64());
//		base64.delete(0, 28);
		return Optional.of(new Danfe(base64.toString()));
	}

}
