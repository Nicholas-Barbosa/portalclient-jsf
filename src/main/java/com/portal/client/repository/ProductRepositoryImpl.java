package com.portal.client.repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.ProductPage;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.dto.ProductStock;
import com.portal.client.dto.ProductStockWrapper;
import com.portal.client.dto.ProductTechDetailJson;
import com.portal.client.dto.ProductToFindStock;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.repository.aop.OptionalEmptyRepository;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.vo.Product;

@ApplicationScoped
public class ProductRepositoryImpl extends OptionalEmptyRepository implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;

	private TokenedRestClient restClient;
	private ProtheusAPIHelper orcamentoAPI;

	@Inject
	public ProductRepositoryImpl(TokenedRestClient restClient, ProtheusAPIHelper orcamentoAPI) {
		super();
		this.restClient = restClient;
		this.orcamentoAPI = orcamentoAPI;
	}

	@Override
	public Optional<Product> findByCode(String code, String customerCode, String store) {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);
		pathParmas.put("customerCode", customerCode);
		pathParmas.put("store", store);
		return Optional
				.of(restClient.get(orcamentoAPI.buildEndpoint("products/{code}/client/{customerCode}/store/{store}"),
						orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(), ProductPage.class, null, pathParmas,
						MediaType.APPLICATION_JSON).getProducts().get(0));

	}

	@Override
	public Future<ProductPage> findByCodeAsync(String code, String customerCode, String store)
			throws ExecutionException {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("code", code);
		pathParams.put("customerCode", customerCode);
		pathParams.put("store", store);
		return restClient.getAsync(orcamentoAPI.buildEndpoint("products/{code}/client/{customerCode}/store/{store}"),
				orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(), ProductPage.class, null, pathParams,
				MediaType.APPLICATION_JSON);
	}

	@Override
	public ProductPageDTO find(int page, int pageSize) {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageDTO productPageDto = (ProductPageDTO) restClient.get(orcamentoAPI.buildEndpoint("products"),
				orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(), ProductPageDTO.class, queryParams, null,
				MediaType.APPLICATION_JSON);

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> findByDescription(int page, int pageSize, String description) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", description);
		ProductPageDTO productPageDto = (ProductPageDTO) restClient.get(orcamentoAPI.buildEndpoint("products"),
				orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(), ProductPageDTO.class, queryParams, null,
				MediaType.APPLICATION_JSON);
		return Optional.of(productPageDto);

	}

	@Override
	public Future<ProductPage> findByCodeForProspectAsync(String code, String state, String sellerType)
			throws ExecutionException {
		Map<String, Object> pathParmas = new HashMap<>();
		pathParmas.put("code", code);

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("state", state);
		queryParams.put("type", sellerType);
		return restClient.getAsync(orcamentoAPI.buildEndpoint("products/{code}"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), ProductPage.class, queryParams, pathParmas, MediaType.APPLICATION_JSON);

	}

	@Override
	public ProductTechDetailJson findTechDetails(String commercialCode) {
		return restClient.get("https://gauss.com.br/produtos/wp-json/wp/v2/gauss_products",
				System.getProperty("site.api.token"), "Bearer", ProductTechDetailJson[].class,
				Map.of("slug", commercialCode, "lang", "pt"), null, "application/json")[0];
	}

	@Override
	public void findStock(Product... products) {
		ProductToFindStock[] productsToFind = Arrays.stream(products).map(ProductToFindStock::of)
				.collect(Collectors.toSet()).toArray(new ProductToFindStock[0]);
		List<ProductStock> stocks = restClient.post(orcamentoAPI.buildEndpoint("stock/search"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), ProductStockWrapper.class, null, null, productsToFind,
				MediaType.APPLICATION_JSON).getStock();
		Arrays.stream(products).parallel().forEach(p -> {
			stocks.stream().filter(pStock -> p.getCommercialCode().equals(pStock.getCommercialCode())).findAny()
					.ifPresent(pStock -> {
						p.setStock(pStock.getStock());
					});

		});

	}

}
