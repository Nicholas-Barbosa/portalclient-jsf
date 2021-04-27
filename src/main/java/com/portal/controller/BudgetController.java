package com.portal.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import org.primefaces.component.api.UIData;
import org.primefaces.component.blockui.BlockUI;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.QueryParam;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.controller.lazycollection.CustomerGaussDTOLazyDataModel;
import com.portal.controller.lazycollection.ProductGaussDTOLazyDataModel;
import com.portal.dto.CustomerGaussDTO;
import com.portal.dto.CustomerResponseGaussDTO;
import com.portal.dto.ErrorGaussDTO;
import com.portal.dto.ProductGaussDTO;
import com.portal.dto.ProductsResponseGaussDTO;
import com.portal.service.faces.FacesService;
import com.portal.service.reflection.MethodAttributes;
import com.portal.service.reflection.ReflectionService;
import com.portal.service.view.HoldMessageView;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomerResponseGaussDTO customerResponseGaussDTO;

	private transient UIData uiCustomerDataTable;

	private transient BlockUI uiBlockForm;

	private boolean render = true;

	private CustomerGaussDTO selectedCustomer;

	private LazyDataModel<CustomerGaussDTO> lazyCustomers;

	private LazyDataModel<ProductGaussDTO> lazyProducts;

	private final HoldMessageView holderMessage;

	private final AuthenticatedRestClient authRestClient;

	private final FacesService facesService;

	private ProductsResponseGaussDTO productResponseDTO;

	private String customerCode, customerStore;

	private final Map<String, Object[]> methodsThatThrewException = new ConcurrentHashMap<>();

	public BudgetController() {
		this(null, null, null);
	}

	@Inject
	public BudgetController(@OAuth2RestAuth AuthenticatedRestClient authRestClient, HoldMessageView holderMessage,
			FacesService facesService) {
		super();
		this.authRestClient = authRestClient;
		this.lazyProducts = new ProductGaussDTOLazyDataModel();
		this.lazyCustomers = new CustomerGaussDTOLazyDataModel();
		this.holderMessage = holderMessage;
		this.facesService = facesService;

	}

	public void initTableCustomers() {
		try {
			if (((CustomerGaussDTOLazyDataModel) this.lazyCustomers).getCustomers().isEmpty()) {
				this.getCustomers(List.of(new QueryParam("page", 0), new QueryParam("pageSize", 12)));
			}
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onPageCustomerListener(PageEvent pageEvent) {
	
		this.getCustomers(List.of(new QueryParam("page", pageEvent.getPage() + 1), new QueryParam("pageSize", 12)));
	}

	public void getCustomers(List<QueryParam> queryParams, Object... pathParams) {
		try {
			Object response = authRestClient.getForEntity("GAUSS_ORCAMENTO", "clients", CustomerResponseGaussDTO.class,
					ErrorGaussDTO.class, queryParams, pathParams);
			if (response instanceof CustomerResponseGaussDTO) {
				this.customerResponseGaussDTO = (CustomerResponseGaussDTO) response;
				this.lazyCustomers.setPageSize(this.customerResponseGaussDTO.getPageSize());
				this.lazyCustomers.setRowCount(this.customerResponseGaussDTO.getTotalItems());

				((CustomerGaussDTOLazyDataModel) this.lazyCustomers)
						.addCollectionToLazyCustomers(this.customerResponseGaussDTO.getClients());
			} else {
				ErrorGaussDTO error = (ErrorGaussDTO) response;
				facesService.error(null, holderMessage.label("resposta_servidor"), error.getErrorMessage())
						.addHeaderForResponse("Backbone-Status", "Error");
			}
		} catch (ProcessingException e) {
			this.methodsThatThrewException.put("getCustomers", new Object[] { queryParams, pathParams });
			facesService.checkProcessingExceptionCauseAndAddMessage(e).addHeaderForResponse("Backbone-Status", "Error");
			((CustomerGaussDTOLazyDataModel) this.lazyCustomers).clearCustomers();

		} catch (Exception e) {
			this.methodsThatThrewException.put("getCustomers", new Object[] { queryParams, pathParams });
			if (FacesContext.getCurrentInstance() != null)
				facesService.addHeaderForResponse("Backbone-Status", "Error");
			((CustomerGaussDTOLazyDataModel) this.lazyCustomers).clearCustomers();
			e.printStackTrace();
		}
	}

	/**
	 * Method triggered by rowSelect ajax event
	 */
	public void onCustomerSelected() {
		//this.uiCustomerDataTable.setRendered(false);
	}

	/**
	 * called by <f:event listener="#{budgetController.preRenderUIBlockCustomerForm}
	 * event="preRenderComponenet"/>
	 */
	public void preRenderUIBlockCustomerForm() {
		this.uiBlockForm.setBlock("formClientTb");
		this.uiBlockForm.setBlocked(true);

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

	public ProductsResponseGaussDTO getProductResponseDTO() {
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

}
