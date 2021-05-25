package com.portal.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.NoPageProductResponseDTO;
import com.portal.dto.ProductPageDTO;

@Stateless
public class ProductRepositoryImpl implements ProductRepository, Serializable {

	private static final long serialVersionUID = 4463669170628763803L;

	@Inject
	@OAuth2RestAuth
	private AuthenticatedRestClient authRestClient;

	@Override
	public NoPageProductResponseDTO getByCode(String code) throws ProcessingException {
		try {
			Map<String, Object> pathParmas = new HashMap<>();
			pathParmas.put("code", code);
			NoPageProductResponseDTO product = authRestClient.getForEntity("ORCAMENTO_API", "products/{code}",
					NoPageProductResponseDTO.class, null, pathParmas, MediaType.APPLICATION_JSON_TYPE);
			return product;
		} catch (NotFoundException e) {
			return null;
		}

	}

	@Override
	public ProductPageDTO getAllByPage(int page, int pageSize) throws ProcessingException {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageDTO productPageDto = (ProductPageDTO) authRestClient.getForEntity("ORCAMENTO_API", "products",
				ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON_TYPE);

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> getByDescription(int page, int pageSize, String description)
			throws ProcessingException {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", description);

		try {
			ProductPageDTO productPageDto = (ProductPageDTO) authRestClient.getForEntity("ORCAMENTO_API", "products",
					ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON_TYPE);
			return Optional.of(productPageDto);
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

}
