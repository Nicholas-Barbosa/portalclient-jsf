package com.portal.client.repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.BudgetEstimatedResultBuilder;
import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.BudgetSavedResponse;
import com.portal.client.dto.BudgetToSaveJsonSerializable;
import com.portal.client.dto.BudgetToUpdateDTO;
import com.portal.client.dto.FormToEstimateBudget;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.dto.Page;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.repository.aop.OptionalEmptyRepository;
import com.portal.client.security.api.helper.OrcamentoAPIHelper;
import com.portal.client.service.jsonb.JsonbService;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Customer404Error;
import com.portal.client.vo.Deseriaized404JsonEstimateEndpoint;
import com.portal.client.vo.WrapperItem404Error;

@ApplicationScoped
public class BudgetRepositoryImpl extends OptionalEmptyRepository implements BudgetRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758905240244736233L;
	private final TokenedRestClient restClient;
	private final JsonbService jsonbService;
	private OrcamentoAPIHelper orcamentoAPI;

	public BudgetRepositoryImpl() {
		this(null, null, null);
	}

	@Inject
	public BudgetRepositoryImpl(TokenedRestClient restClient, JsonbService jsonbService,
			OrcamentoAPIHelper orcamentoAPI) {
		super();
		this.restClient = restClient;
		this.jsonbService = jsonbService;
		this.orcamentoAPI = orcamentoAPI;
	}

	@Override
	public Page<Budget> findAll(int page, int pageSize) {
		StringBuilder endpointURL = new StringBuilder(orcamentoAPI.getBasePath());
		endpointURL.append("/budgets");
		return restClient.get(endpointURL.toString(), orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(),
				BudgetPage.class, Map.of("page", page, "pageSize", pageSize, "searchOrder", "DESC"), null,
				MediaType.APPLICATION_JSON);
	}

	@Override
	public void save(Budget budget) {
		BudgetSavedResponse response = restClient.post(orcamentoAPI.buildEndpoint("budgets"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), BudgetSavedResponse.class, null, null,
				BudgetToSaveJsonSerializable.of(budget), MediaType.APPLICATION_JSON);
		budget.setCode(response.getCode());
	}

	@Override
	public Optional<Page<Budget>> findByCode(String code, int page, int pageSize) {
		return Optional.of(restClient.get(orcamentoAPI.buildEndpoint("budgets/{code}"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), BudgetFullProjection.class, Map.of("page", page, "pageSize", pageSize),
				Map.of("code", code), MediaType.APPLICATION_JSON));

	}

	@Override
	public Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> items)
			throws CustomerNotFoundException, ItemsNotFoundException {

		FormToEstimateBudget toEstimate = new FormToEstimateBudget(customerCode, customerStore, items);
		try {
			BudgetEstimatedResultBuilder budgetBuilder = restClient.post(orcamentoAPI.buildEndpoint("estimate"),
					orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(), BudgetEstimatedResultBuilder.class, null,
					null, toEstimate, "application/json");
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

	@Override
	public void update(Budget budget) {
		BudgetToUpdateDTO toUpdate = new BudgetToUpdateDTO(budget);
		restClient.put(orcamentoAPI.buildEndpoint("budgets/{id}"), orcamentoAPI.getToken(),
				orcamentoAPI.getPrefixToken(), String.class, null, Map.of("id", budget.getCode()), toUpdate,
				"application/json");
	}
}
