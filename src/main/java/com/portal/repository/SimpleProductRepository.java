package com.portal.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
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
	public Optional<Product> getByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductPage getAllByPage(int page, int pageSize) {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageGaussDTO productPageDto = (ProductPageGaussDTO) authRestClient.getForEntity("GAUSS_ORCAMENTO",
				"products", ProductPageGaussDTO.class, queryParams, null);

		return new ProductPage(productPageDto.totalItems(), productPageDto.totalPages(), productPageDto.getPageSize(),
				productPageDto.getPage(),
				productPageDto
						.getContent().parallelStream().map(p -> new Product(p.getCode(), p.getDescriptionType(),
								p.getCommercialCode(), p.getType(), p.getDescription()))
						.collect(HashSet::new, Set::add, Set::addAll));
	}

	@Override
	public ProductPage getByDescription(int page, int pageSize, String description) {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageGaussDTO productPageDto = (ProductPageGaussDTO) authRestClient.getForEntity("GAUSS_ORCAMENTO",
				"products", ProductPageGaussDTO.class, queryParams, null);
		return null;
	}

}
