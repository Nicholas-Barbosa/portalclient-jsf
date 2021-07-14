package com.portal.java.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
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
import com.portal.java.dto.Product;
import com.portal.java.dto.ProductJsonWrapper;
import com.portal.java.dto.ProductPageDTO;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;

	@Inject
	@OAuth2RestAuth
	private AuthenticatedRestClient authRestClient;

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);
		pathParmas.put("customerCode", customerCode);
		pathParmas.put("store", store);
		try {
			return Optional
					.of(authRestClient.get("ORCAMENTO_API", "products/{code}/client/{customerCode}/store/{store}",
							ProductJsonWrapper.class, null, pathParmas, MediaType.APPLICATION_JSON).getProducts().get(0));
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

	@Override
	public Future<ProductJsonWrapper> findByCodeAsync(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("code", code);
		pathParams.put("customerCode", customerCode);
		pathParams.put("store", store);
		try {
			return authRestClient.getAsync("ORCAMENTO_API", "products/{code}/client/{customerCode}/store/{store}",
					ProductJsonWrapper.class, null, pathParams, MediaType.APPLICATION_JSON);
		} catch (NotFoundException e) {
			return null;
		}
	}

	@Override
	public ProductPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageDTO productPageDto = (ProductPageDTO) authRestClient.get("ORCAMENTO_API", "products",
				ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON);

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
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

	@Override
	public Future<ProductJsonWrapper> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("state", state);
		queryParams.put("type", sellerType);
		try {
			return authRestClient.getAsync("ORCAMENTO_API", "products/{code}", ProductJsonWrapper.class,
					queryParams, pathParmas, MediaType.APPLICATION_JSON);
		} catch (NotFoundException e) {
			return null;
		}
	}

}
