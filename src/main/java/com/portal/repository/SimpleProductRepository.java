package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.ProductGaussDTO;
import com.portal.dto.ProductPageGaussDTO;
import com.portal.pojo.Product;
import com.portal.pojo.ProductPage;

@SessionScoped
public class SimpleProductRepository implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;

	private final AuthenticatedRestClient authRestClient;

	public SimpleProductRepository() {
		this(null);
	}

	@Inject
	public SimpleProductRepository(@OAuth2RestAuth AuthenticatedRestClient authRestClient) {
		super();
		this.authRestClient = authRestClient;

	}

	@Override
	public Optional<Product> getByCode(String code) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException {
		try {
			Map<String, Object> pathParmas = new HashMap<>();
			pathParmas.put("code", code);
			ProductPageGaussDTO productPage = authRestClient.getForEntity("ORCAMENTO_API", "products/{code}",
					ProductPageGaussDTO.class, null, pathParmas, MediaType.APPLICATION_JSON_TYPE);
			return Optional.of(((List<ProductGaussDTO>) productPage.getContent()).get(0).toProduct());
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

	@Override
	public ProductPage getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageGaussDTO productPageDto = (ProductPageGaussDTO) authRestClient.getForEntity("ORCAMENTO_API",
				"products", ProductPageGaussDTO.class, queryParams, null, MediaType.APPLICATION_JSON_TYPE);

		return new ProductPage(productPageDto.totalItems(), productPageDto.totalPages(), productPageDto.getPageSize(),
				productPageDto.getPage(),
				productPageDto
						.getContent().parallelStream().map(p -> new Product(p.getCode(), p.getDescriptionType(),
								p.getCommercialCode(), p.getType(), p.getDescription()))
						.collect(HashSet::new, Set::add, Set::addAll));
	}

	@Override
	public ProductPage getByDescription(int page, int pageSize, String description) throws SocketTimeoutException,
			ConnectException, ProcessingException, IllegalArgumentException, TimeoutException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", description);

		ProductPageGaussDTO productPageDto = (ProductPageGaussDTO) authRestClient.getForEntity("ORCAMENTO_API",
				"products", ProductPageGaussDTO.class, queryParams, null, MediaType.APPLICATION_JSON_TYPE);
		ProductPage product = productPageDto.toProduct();

		return product;
	}

}
