package com.portal.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import org.primefaces.component.api.UIData;
import org.primefaces.component.blockui.BlockUI;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.CustomerGaussDTO;
import com.portal.dto.CustomerPageGaussDTO;
import com.portal.dto.ErrorGaussDTO;
import com.portal.dto.PageResponse;
import com.portal.dto.ProductGaussDTO;
import com.portal.dto.ProductsPageGaussDTO;
import com.portal.exception.ProcessingExceptionHandler;
import com.portal.service.faces.FacesService;
import com.portal.service.view.HoldMessageView;
import com.portal.ui.lazy.datamodel.CustomerGaussDTOLazyDataModel;
import com.portal.ui.lazy.datamodel.LazyOperations;
import com.portal.ui.lazy.datamodel.ProductGaussDTOLazyDataModel;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient UIData uiCustomerDataTable;

	private transient BlockUI uiBlockForm;

	private boolean render = true;

	private CustomerGaussDTO selectedCustomer;

	private LazyDataModel<CustomerGaussDTO> lazyCustomers;

	private LazyDataModel<ProductGaussDTO> lazyProducts;

	private final HoldMessageView holderMessage;

	private final AuthenticatedRestClient authRestClient;

	private final FacesService facesService;

	private final ProcessingExceptionHandler processingExceptionHandler;
	private ProductsPageGaussDTO productResponseDTO;

	private String customerCode, customerStore, h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	public BudgetController() {
		this(null, null, null, null);
	}

	@Inject
	public BudgetController(@OAuth2RestAuth AuthenticatedRestClient authRestClient, HoldMessageView holderMessage,
			FacesService facesService, ProcessingExceptionHandler processingExceptionHandler) {
		super();
		this.authRestClient = authRestClient;
		this.lazyProducts = new ProductGaussDTOLazyDataModel();
		this.lazyCustomers = new CustomerGaussDTOLazyDataModel();
		this.holderMessage = holderMessage;
		this.facesService = facesService;
		this.processingExceptionHandler = processingExceptionHandler;

	}

	@PostConstruct
	public void init() {
		this.h5DivLoadCustomers = holderMessage.label("carregando_clientes");
		this.h5DivLoadProducts = holderMessage.label("carregano_produtos");
	}

	public void globalLoadCustomers(int page) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSizeForCustomers);
		h5DivLoadCustomers = this.populateLazyCollection(queryParams, "clients", this.lazyCustomers,
				CustomerPageGaussDTO.class, null, holderMessage.label("impossivel_procurar_clientes"),
				"formClientTb:dtCustomer") ? holderMessage.label("selecione_cliente")
						: holderMessage.label("impossivel_carregar_clientes");

	}

	/**
	 * This method will be called when view page is render by async ajax request.
	 */
	public void initTableCustomers() {
		this.globalLoadCustomers(0);
	}

	public void onPageCustomerListener(PageEvent pageEvent) {
		this.globalLoadCustomers(pageEvent.getPage() + 1);

	}

	public void globalLoadProducts(int page) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSizeForProducts);
		this.h5DivLoadProducts = this.populateLazyCollection(queryParams, "products", this.lazyProducts,
				ProductsPageGaussDTO.class, null, holderMessage.label("impossivel_procurar_produtos"),
				"formProdutos:dtProducts") ? holderMessage.label("selecione_produtos")
						: holderMessage.label("impossivel_carregar_produtos");

	}

	public void initTableProducts() {
		this.globalLoadProducts(0);
	}

	public void findCustomer() {

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("code", customerCode);
		pathParams.put("store", customerStore);
		this.populateCollectionWithSingleRow(null, "clients/{code}/loja/{store}", this.lazyCustomers,
				CustomerPageGaussDTO.class, pathParams, holderMessage.label("nao_encontrado"),
				"formClientTb:dtCustomer");

	}

	public void refreshDtCustomers() {
		this.globalLoadCustomers(0);

	}

	public void onPageProducts(PageEvent pageEvent) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", pageEvent.getPage() + 1);
		queryParams.put("pageSize", pageSizeForCustomers);
	}

	@SuppressWarnings("unchecked")
	public <T extends PageResponse<?>, U extends LazyDataModel<?>> boolean populateLazyCollection(
			Map<String, Object> queryParams, String enpoint, U collection, Class<T> responseType,
			Map<String, Object> pathParams, String summaryForErrorResponse, String clientIdMessage) {
		try {

			Object response = authRestClient.getForEntity("GAUSS_ORCAMENTO", enpoint, responseType, ErrorGaussDTO.class,
					queryParams, pathParams);

			try {
				T pageResponse = (T) response;
				collection.setPageSize(pageResponse.getPageSize());
				collection.setRowCount(pageResponse.totalItems());

				/*
				 * Here will occur override,so T or U not make difference.
				 */
				((LazyOperations<T>) collection).addCollection((List<T>) pageResponse.getContent());
				return true;
			} catch (ClassCastException e) {
				ErrorGaussDTO error = (ErrorGaussDTO) response;
				facesService.error(clientIdMessage, summaryForErrorResponse, error.getErrorMessage())
						.addHeaderForResponse("Backbone-Status", "Error");

			}
		} catch (ProcessingException e) {
			processingExceptionHandler.checkProcessingExceptionCauseAndAddMessage(e, clientIdMessage);
			facesService.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		} catch (Exception e) {
			facesService.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends PageResponse<?>, U extends LazyDataModel<?>> void populateCollectionWithSingleRow(
			Map<String, Object> queryParams, String enpoint, U collection, Class<T> responseType,
			Map<String, Object> pathParams, String summaryForErrorResponse, String clientIdMessage) {
		try {

			Object response = authRestClient.getForEntity("GAUSS_ORCAMENTO", enpoint, responseType, ErrorGaussDTO.class,
					queryParams, pathParams);

			try {
				T pageResponse = (T) response;
				collection.setPageSize(1);
				collection.setRowCount(1);
				((LazyOperations<T>) collection).addCollection((List<T>) pageResponse.getContent());

			} catch (ClassCastException e) {
				ErrorGaussDTO error = (ErrorGaussDTO) response;
				facesService.error(clientIdMessage, summaryForErrorResponse, error.getErrorMessage())
						.addHeaderForResponse("Backbone-Status", "Error");

			}
		} catch (ProcessingException e) {
			processingExceptionHandler.checkProcessingExceptionCauseAndAddMessage(e, clientIdMessage);
			facesService.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		} catch (Exception e) {
			facesService.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		}
	}

	/**
	 * Method triggered by rowSelect ajax event
	 */
	public void onCustomerSelected() {
		// this.uiCustomerDataTable.setRendered(false);
	}

	public boolean getRender() {
		return this.render;
	}

	public UIData getUiCustomerDataTable() {
		return uiCustomerDataTable;
	}

	public void setUiCustomerDataTable(UIData uiCustomerDataTable) {
		this.uiCustomerDataTable = uiCustomerDataTable;
	}

	public BlockUI getUiBlockForm() {
		return uiBlockForm;
	}

	public void setUiBlockForm(BlockUI uiBlockForm) {
		this.uiBlockForm = uiBlockForm;

	}

	public CustomerGaussDTO getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(CustomerGaussDTO selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public LazyDataModel<CustomerGaussDTO> getLazyCustomers() {
		return lazyCustomers;
	}

	public void setLazyCustomers(LazyDataModel<CustomerGaussDTO> lazyCustomers) {
		this.lazyCustomers = lazyCustomers;
	}

	public ProductsPageGaussDTO getProductResponseDTO() {
		return productResponseDTO;
	}

	public LazyDataModel<ProductGaussDTO> getLazyProducts() {
		return lazyProducts;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public void setCustomerStore(String customerStore) {
		this.customerStore = customerStore;
	}

	public Integer getPageSizeForCustomers() {
		return pageSizeForCustomers;
	}

	public Integer getPageSizeForProducts() {
		return pageSizeForProducts;
	}

	public String getH5DivLoadCustomers() {
		return h5DivLoadCustomers;
	}

	public String getH5DivLoadProducts() {
		return h5DivLoadProducts;
	}
}
