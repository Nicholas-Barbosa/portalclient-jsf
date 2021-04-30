package com.portal.repository;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.ErrorGaussDTO;
import com.portal.dto.ProductPageGaussDTO;
import com.portal.pojo.Product;
import com.portal.pojo.ProductPage;

@SessionScoped
public class SimpleProductRepository implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;
	@OAuth2RestAuth
	private final AuthenticatedRestClient restClient;

	public SimpleProductRepository() {
		this(null);
	}

	@Inject
	public SimpleProductRepository(AuthenticatedRestClient restClient) {
		super();
		this.restClient = restClient;
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
		ProductPageGaussDTO productPageDto = (ProductPageGaussDTO) restClient.getForEntity("GAUSS_ORCAMENTO",
				"products", ProductPageGaussDTO.class, ErrorGaussDTO.class, queryParams, null);
		return null;
	}

	@Override
	public ProductPage getByDescription(String description) {
		// TODO Auto-generated method stub
		return null;
	}

}
