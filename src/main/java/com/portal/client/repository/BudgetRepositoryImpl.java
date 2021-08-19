package com.portal.client.repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetEstimatedResultBuilder;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetSavedResponse;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.dto.FormToEstimateBudget;
import com.portal.client.dto.ItemBudgetToSaveJsonSerializable;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.jaxrs.client.RestClient;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.service.jsonb.JsonbService;
import com.portal.client.vo.Customer404Error;
import com.portal.client.vo.Deseriaized404JsonEstimateEndpoint;
import com.portal.client.vo.WrapperItem404Error;

@ApplicationScoped
public class BudgetRepositoryImpl implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final RestClient restClient;
	private final UserSessionAPIManager apiManager;
	private final JsonbService jsonbService;
	private final String orcamentoKey = "ORCAMENTO_API";

	public BudgetRepositoryImpl() {
		this(null, null, null);
	}

	@Inject
	public BudgetRepositoryImpl(RestClient restClient, UserSessionAPIManager apiManager, JsonbService jsonbService) {
		super();
		this.restClient = restClient;
		this.apiManager = apiManager;
		this.jsonbService = jsonbService;
	}

	@Override
	public BudgetPage findAll(int page, int pageSize) {
		ServerAPI api = apiManager.getAPI(orcamentoKey);
		StringBuilder endpointURL = new StringBuilder(api.getBasePath());
		endpointURL.append("/budgets");
		return restClient.get(endpointURL.toString(), api.getToken(), api.getTokenPrefix(), BudgetPage.class,
				Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC"), null, MediaType.APPLICATION_JSON);
	}

	@Override
	public void save(BaseBudget budget) {
		ServerAPI api = apiManager.getAPI(orcamentoKey);
		budget.replaceItems(budget.getItems().parallelStream().map(ItemBudgetToSaveJsonSerializable::new)
				.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll));
		BudgetSavedResponse response = restClient.post(apiManager.buildEndpoint(api, "budgets"), api.getToken(),
				api.getTokenPrefix(), BudgetSavedResponse.class, null, null, BudgetToSaveJsonSerializable.of(budget),
				MediaType.APPLICATION_JSON);
		budget.setIdCode(response.getCode());
	}

	@Override
	public Optional<BaseBudget> findByCode(String code) {
		ServerAPI api = apiManager.getAPI(orcamentoKey);
		try {
			return Optional.of(restClient.get(apiManager.buildEndpoint(api, "budgets/{code}"), api.getToken(),
					api.getTokenPrefix(), BudgetFullProjection.class, null, Map.of("code", code),
					MediaType.APPLICATION_JSON));
		} catch (javax.ws.rs.NotFoundException e) {
			return Optional.empty();
		}
	}

	@Override
	public BaseBudget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> items)
			throws CustomerNotFoundException, ItemsNotFoundException {
		ServerAPI server = apiManager.getAPI(orcamentoKey);

		FormToEstimateBudget toEstimate = new FormToEstimateBudget(customerCode, customerStore, items);
		try {
			BudgetEstimatedResultBuilder budgetBuilder = restClient.post(apiManager.buildEndpoint(server, "estimate"),
					server.getToken(), server.getTokenPrefix(), BudgetEstimatedResultBuilder.class, null, null,
					toEstimate, "application/json");
			return budgetBuilder.build();
		} catch (NotFoundException e) {
			Deseriaized404JsonEstimateEndpoint notFoundCause = deserializeJson404FromEstimateEndpoint(
					e.getResponse().getEntity() + "");
			if (notFoundCause.isOkWithCustomer())
				throw new CustomerNotFoundException(notFoundCause.getCustomerError());
			throw new ItemsNotFoundException(notFoundCause.getItemErrors());

		}
	}

	private Deseriaized404JsonEstimateEndpoint deserializeJson404FromEstimateEndpoint(String json) {
		ExecutorService executor = null;
		try {
			executor = Executors.newFixedThreadPool(2);
			Future<WrapperItem404Error> f = executor
					.submit(() -> jsonbService.fromJson(json, WrapperItem404Error.class));
			Future<Customer404Error> fCustomer = executor
					.submit(() -> jsonbService.fromJson(json, Customer404Error.class));
			Deseriaized404JsonEstimateEndpoint deserialized = new Deseriaized404JsonEstimateEndpoint(
					f.get().getErrors(), fCustomer.get());
			return deserialized;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return null;
		} finally {
			executor.shutdown();
		}
	}
}
