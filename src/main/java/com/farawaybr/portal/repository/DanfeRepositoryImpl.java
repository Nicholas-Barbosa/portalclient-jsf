package com.farawaybr.portal.repository;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import com.farawaybr.portal.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;
import com.farawaybr.portal.dto.DanfeDataDto;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.ProtheusAPIHelper;
import com.farawaybr.portal.vo.Danfe;

@ApplicationScoped
public class DanfeRepositoryImpl extends RepositoryInterceptors implements DanfeRepository {

	@Inject
	private RestClient restClient;

	@Inject
	private ProtheusAPIHelper protheusApi;

	@NotFoundOptionalEmptyJoinPointCut
	@Override
	public Optional<Danfe> findByInvoiceNumber(String invoiceNumber, String invoiceSerie) {
		// TODO Auto-generated method stub
		DanfeDataDto data = restClient.get(
				protheusApi.buildEndpoint("danfe/{invoiceNumber}/series/{invoiceSerie}"), DanfeDataDto.class, null,
				Map.of("invoiceNumber", invoiceNumber, "invoiceSerie", invoiceSerie), "application/json",
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApi.getToken()));
		StringBuilder base64 = new StringBuilder(data.getStreamBase64());
//		base64.delete(0, 28);
		return Optional.of(new Danfe(base64.toString()));
	}

}
