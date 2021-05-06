package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;

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
	public Optional<ProductDTO> getByCode(String code) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException,SocketException {
		try {
			Map<String, Object> pathParmas = new HashMap<>();
			pathParmas.put("code", code);
			ProductPageDTO productPage = authRestClient.getForEntity("ORCAMENTO_API", "products/{code}",
					ProductPageDTO.class, null, pathParmas, MediaType.APPLICATION_JSON_TYPE);
			return Optional.of(((List<ProductDTO>) productPage.getContent()).get(0));
		} catch (NotFoundException e) {
			return Optional.empty();
		}

	}

	@Override
	public ProductPageDTO getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException,SocketException {
		Map<String, Object> queryParams = Stream.of(page, pageSize)
				.collect(Collectors.toMap(k -> k.toString(), v -> v));
		ProductPageDTO productPageDto = (ProductPageDTO) authRestClient.getForEntity("ORCAMENTO_API", "products",
				ProductPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON_TYPE);

		return productPageDto;
	}

	@Override
	public Optional<ProductPageDTO> getByDescription(int page, int pageSize, String description)
			throws SocketTimeoutException, ConnectException, ProcessingException, IllegalArgumentException,
			TimeoutException,SocketException {
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
