package com.farawaybr.portal.repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.BatchProductSearchJsonPostForm;
import com.farawaybr.portal.dto.ProductPageDTO;
import com.farawaybr.portal.dto.ProductStock;
import com.farawaybr.portal.dto.ProductStockWrapper;
import com.farawaybr.portal.dto.ProductTechDetailJson;
import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.dto.ProductToFindStock;
import com.farawaybr.portal.dto.ProductWrapper;
import com.farawaybr.portal.exception.ProductsNotFoundException;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.service.jsonb.JsonbService;
import com.farawaybr.portal.vo.Product;
import com.farawaybr.portal.vo.WrapperProduct404Error;

@ApplicationScoped
public class ProductRepositoryImpl extends RepositoryInterceptors implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;
	@Inject
	private RestClient restClient;
	@Inject
	private APIHelper protheusApiHelper;
	@Inject
	private JsonbService jsonb;

	public ProductRepositoryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String store) {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);
		pathParmas.put("customerCode", customerCode);
		pathParmas.put("store", store);
		return Optional.of(restClient
				.get(protheusApiHelper.buildEndpoint("products/{code}/client/{customerCode}/store/{store}"),
						ProductWrapper.class, null, pathParmas, MediaType.APPLICATION_JSON,
						Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()))
				.getProducts().get(0).getProduct());

	}

	@Override
	public Future<ProductWrapper> findByCodeAsync(String code, String customerCode, String store)
			throws ExecutionException {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("code", code);
		pathParams.put("customerCode", customerCode);
		pathParams.put("store", store);
		return restClient.getAsync(
				protheusApiHelper.buildEndpoint("products/{code}/client/{customerCode}/store/{store}"),
				ProductWrapper.class, null, pathParams, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));
	}

	@Override
	public ProductPageDTO find(int page, int pageSize) {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageDTO productPageDto = (ProductPageDTO) restClient.get(protheusApiHelper.buildEndpoint("products"),
				ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", description);
		ProductPageDTO productPageDto = (ProductPageDTO) restClient.get(protheusApiHelper.buildEndpoint("products"),
				ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));
		return Optional.of(productPageDto);

	}

	@Override
	public Future<ProductWrapper> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws ExecutionException {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("state", state);
		queryParams.put("type", sellerType);
		return restClient.getAsync(protheusApiHelper.buildEndpoint("products/{code}"), ProductWrapper.class,
				queryParams, pathParmas, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));

	}

	@Override
	public ProductTechDetailJson findTechDetails(String commercialCode) {
		return restClient.get("https://gauss.com.br/produtos/wp-json/wp/v2/gauss_products",
				ProductTechDetailJson[].class, Map.of("slug", commercialCode, "lang", "pt"), null, "application/json",
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + System.getProperty("site.api.token")))[0];
	}

	@Override
	public void findStock(Product... products) {
		ProductToFindStock[] productsToFind = Arrays.stream(products).map(ProductToFindStock::of)
				.collect(Collectors.toSet()).toArray(new ProductToFindStock[0]);
		List<ProductStock> stocks = restClient.post(protheusApiHelper.buildEndpoint("stock/search"),
				ProductStockWrapper.class, null, null, productsToFind, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken())).getStock();
		Arrays.stream(products).parallel().forEach(p -> {
			stocks.stream().filter(pStock -> p.getCommercialCode().equals(pStock.getCommercialCode())).findAny()
					.ifPresent(pStock -> {
						p.setStock(pStock.getStock());
					});

		});

	}

	@Override
	public BatchProductSearchDataWrapper batchProductSearch(String customerCode, String customerStore,
			Set<ProductToFind> products) {
		BatchProductSearchJsonPostForm postForm = new BatchProductSearchJsonPostForm(customerCode, customerStore,
				products);
		try {
			BatchProductSearchDataWrapper dataWrapper = restClient.post(protheusApiHelper.buildEndpoint("estimate"),

					BatchProductSearchDataWrapper.class, null, null, postForm, "application/json",
					Map.of(HttpHeaders.AUTHORIZATION, "Bearer " + protheusApiHelper.getToken()));
			return dataWrapper;
		} catch (NotFoundException e) {
			WrapperProduct404Error wrapper = jsonb.fromJson((String) e.getResponse().getEntity(),
					WrapperProduct404Error.class);
			throw new ProductsNotFoundException(wrapper.getErrors());
		}
	}

}
