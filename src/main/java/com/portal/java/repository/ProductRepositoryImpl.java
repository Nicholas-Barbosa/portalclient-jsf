package com.portal.java.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.java.cdi.qualifier.OAuth2RestAuth;
import com.portal.java.client.rest.auth.AuthenticatedRestClient;
import com.portal.java.dto.NoPageProductResponseDTO;
import com.portal.java.dto.ProductPageDTO;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;

	@Inject
	@OAuth2RestAuth
	private AuthenticatedRestClient authRestClient;

	@Override
	public NoPageProductResponseDTO findByCode(String code)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		try {
			Map<String, Object> pathParmas = new HashMap<>();
			pathParmas.put("code", code);
			return authRestClient.get("ORCAMENTO_API", "products/{code}", NoPageProductResponseDTO.class, null,
					pathParmas, MediaType.APPLICATION_JSON);
		} catch (NotFoundException e) {
			return null;
		}

	}

	@Override
	public Future<NoPageProductResponseDTO> findByCodeAsync(String code)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		try {
			Map<String, Object> pathParmas = new HashMap<>();
			pathParmas.put("code", code);
			return authRestClient.getAsync("ORCAMENTO_API", "products/{code}", NoPageProductResponseDTO.class, null,
					pathParmas, MediaType.APPLICATION_JSON);
		} catch (NotFoundException e) {
			return null;
		}
	}

	@Override
	public ProductPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageDTO productPageDto = (ProductPageDTO) authRestClient.get("ORCAMENTO_API", "products",
				ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON);

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", description);
		try {
			ProductPageDTO productPageDto = (ProductPageDTO) authRestClient.get("ORCAMENTO_API", "products",
					ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON);
			return Optional.of(productPageDto);
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

}
