package com.portal.client.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.ProductPage;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.vo.Product;

@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;

	private RestClient restClient;
	private UserSessionAPIManager apiManager;

	@Inject
	public ProductRepositoryImpl(RestClient restClient, UserSessionAPIManager userSessionAPIManager) {
		super();
		this.restClient = restClient;
		this.apiManager = userSessionAPIManager;
	}

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);
		pathParmas.put("customerCode", customerCode);
		pathParmas.put("store", store);
		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		try {
			return Optional.of(restClient
					.get(apiManager.buildEndpoint(server, "products/{code}/client/{customerCode}/store/{store}"),
							server.getToken(), server.getTokenPrefix(), ProductPage.class, null, pathParmas,
							MediaType.APPLICATION_JSON)
					.getProducts().get(0));
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

	@Override
	public Future<ProductPage> findByCodeAsync(String code, String customerCode, String store)
			throws ExecutionException {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("code", code);
		pathParams.put("customerCode", customerCode);
		pathParams.put("store", store);
		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		return restClient.getAsync(
				apiManager.buildEndpoint(server, "products/{code}/client/{customerCode}/store/{store}"),
				server.getToken(), server.getTokenPrefix(), ProductPage.class, null, pathParams,
				MediaType.APPLICATION_JSON);
	}

	@Override
	public ProductPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");

		ProductPageDTO productPageDto = (ProductPageDTO) restClient.get(apiManager.buildEndpoint(server, "products"),
				server.getToken(), server.getTokenPrefix(), ProductPageDTO.class, queryParams, null,
				MediaType.APPLICATION_JSON);

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", description);
		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		try {
			ProductPageDTO productPageDto = (ProductPageDTO) restClient.get(
					apiManager.buildEndpoint(server, "products"), server.getToken(), server.getTokenPrefix(),
					ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON);
			return Optional.of(productPageDto);
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

	@Override
	public Future<ProductPage> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws ExecutionException {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("state", state);
		queryParams.put("type", sellerType);
		ServerAPI server = apiManager.getAPI("ORCAMENTO_API");
		return restClient.getAsync(apiManager.buildEndpoint(server, "products/{code}"), server.getToken(),
				server.getTokenPrefix(), ProductPage.class, queryParams, pathParmas, MediaType.APPLICATION_JSON);

	}

}
