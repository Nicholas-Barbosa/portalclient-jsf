package com.portal.client.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.nicholas.jaxrsclient.TokenedRestClient;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.CustomerWrapper;
import com.portal.client.dto.ProductPriceTableWrapper;
import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTableJsonData;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.security.api.helper.APIHelper;

@ApplicationScoped
public class CustomerRepositoryImpl extends OptionalEmptyRepository implements CustomerRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042300828676622038L;
	private final TokenedRestClient restClient;
	private final APIHelper protheusApiHelper;

	public CustomerRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public CustomerRepositoryImpl(TokenedRestClient restClient, APIHelper protheusApiHelper) {
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
		CustomerPageDTO customerPage = restClient.get(protheusApiHelper.buildEndpoint("client"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), CustomerPageDTO.class, queryParms,
				null, MediaType.APPLICATION_JSON);
		return customerPage;

	}

	@Override
	public Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO) {
		Map<String, Object> pathParams = getMapInstance();
		pathParams.put("code", searchCustomerByCodeAndStoreDTO.getCode());
		pathParams.put("codeStore", searchCustomerByCodeAndStoreDTO.getStore());
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("clients/{code}/loja/{codeStore}"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), CustomerWrapper.class, null,
				pathParams, MediaType.APPLICATION_JSON).getClients().get(0));

	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize) {
		Map<String, Object> queryParams = getMapInstance();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", name);
		Optional<CustomerPageDTO> cPage = Optional.of(restClient.get(protheusApiHelper.buildEndpoint("clients"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), CustomerPageDTO.class, queryParams,
				null, MediaType.APPLICATION_JSON));

		return cPage.get().getContent().size() > 0 ? cPage : Optional.empty();

	}

	private Map<String, Object> getMapInstance() {
		return new HashMap<>();
	}

	@Override
	public Optional<List<ProductPriceTableJsonData>> findPriceTable(String customerCode, String customerStore) {
		// TODO Auto-generated method stub
		return Optional.of(restClient.get(protheusApiHelper.buildEndpoint("tables/{customer}/loja/{store}"),
				protheusApiHelper.getToken(), protheusApiHelper.getTokenPrefix(), ProductPriceTableWrapper.class, null,
				Map.of("customer", customerCode, "store", customerStore), "application/json").getList());
	}

}
