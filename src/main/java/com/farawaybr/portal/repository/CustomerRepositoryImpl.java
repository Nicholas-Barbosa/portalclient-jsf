package com.farawaybr.portal.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.farawaybr.portal.dto.CustomerPageDTO;
import com.farawaybr.portal.dto.CustomerWrapper;
import com.farawaybr.portal.dto.ProductPriceTableWrapper;
import com.farawaybr.portal.dto.ProductPriceTableWrapper.ProductPriceTableJsonData;
import com.farawaybr.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.security.api.helper.APIHelper;
import com.farawaybr.portal.vo.Customer;

@ApplicationScoped
public class CustomerRepositoryImpl extends RepositoryInterceptors implements CustomerRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042300828676622038L;
	private final RestClient restClient;
	private final APIHelper protheusApiHelper;

	public CustomerRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public CustomerRepositoryImpl(RestClient restClient, APIHelper protheusApiHelper) {
		super();
		this.restClient = restClient;
		this.protheusApiHelper = protheusApiHelper;
	}

	@Override
	public CustomerPageDTO find(int page, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> queryParms = new HashMap<>();
		queryParms.put("page", page);
		queryParms.put("pageSize", pageSize);
		CustomerPageDTO customerPage = restClient.get(protheusApiHelper.buildEndpoint("client"), CustomerPageDTO.class,
				queryParms, null, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken()));
		return customerPage;

	}

	@Override
	public Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO) {
		Map<String, Object> pathParams = getMapInstance();
		pathParams.put("code", searchCustomerByCodeAndStoreDTO.getCode());
		pathParams.put("codeStore", searchCustomerByCodeAndStoreDTO.getStore());
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("clients/{code}/loja/{codeStore}"),
				CustomerWrapper.class, null, pathParams, MediaType.APPLICATION_JSON,
				Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())).getCustomer());

	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize) {
		Map<String, Object> queryParams = getMapInstance();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", name);
		Optional<CustomerPageDTO> cPage = Optional
				.of(restClient.get(protheusApiHelper.buildEndpoint("clients"), CustomerPageDTO.class, queryParams, null,
						MediaType.APPLICATION_JSON, Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())));

		return cPage.get().getContent().size() > 0 ? cPage : Optional.empty();

	}

	private Map<String, Object> getMapInstance() {
		return new HashMap<>();
	}

	@Override
	public Optional<List<ProductPriceTableJsonData>> findPriceTable(String customerCode, String customerStore) {
		// TODO Auto-generated method stub
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("tables/{customer}/loja/{store}"),
				ProductPriceTableWrapper.class, null, Map.of("customer", customerCode, "store", customerStore),
				"application/json", Map.of(HttpHeaders.AUTHORIZATION,"Bearer "+  protheusApiHelper.getToken())).getList());
	}

}
