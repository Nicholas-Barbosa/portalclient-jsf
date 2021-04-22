package com.portal.controller;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import org.primefaces.component.blockui.BlockUI;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.controller.lazycollection.CustomerGaussDTOLazyDataModel;
import com.portal.controller.lazycollection.ProductGaussDTOLazyDataModel;
import com.portal.dto.CustomerGaussDTO;
import com.portal.dto.CustomerResponseGaussDTO;
import com.portal.dto.ErrorGaussDTO;
import com.portal.dto.ProductGaussDTO;
import com.portal.dto.ProductsResponseGaussDTO;
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

	private String divLoadingMainMessage;

	private final AuthenticatedRestClient authRestClient;

	private CustomerGaussDTO selectedCustomer;

	private LazyDataModel<CustomerGaussDTO> lazyCustomers;

	private LazyDataModel<ProductGaussDTO> lazyProducts;

	private final HoldMessageView holderMessage;

	private ProductsResponseGaussDTO productResponseDTO;

	public BudgetController() {
		this(null, null);
	}

	@Inject
	public BudgetController(@OAuth2RestAuth AuthenticatedRestClient authRestClient, HoldMessageView holderMessage) {
		super();
		this.authRestClient = authRestClient;
		this.lazyCustomers = new CustomerGaussDTOLazyDataModel();
		this.lazyProducts = new ProductGaussDTOLazyDataModel();
		this.holderMessage = holderMessage;
	}

	public void onPageCustomerListener(PageEvent pageEvent) {
		this.loadLazyCustomers(pageEvent.getPage() + 1, 12);

	}

	public void getClientsFromWS() {
		try {
			this.loadLazyCustomers(1, 12);
		} catch (NotAuthorizedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadLazyCustomers(int offset, int pageSize) {
		try {
			Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("page", offset);
			queryParams.put("pageSize", pageSize);
			Object response = authRestClient.getForEntity("GAUSS_ORCAMENTO", "clients", CustomerResponseGaussDTO.class,
					ErrorGaussDTO.class, queryParams);
			if (response instanceof CustomerResponseGaussDTO) {
				this.customerResponseGaussDTO = (CustomerResponseGaussDTO) response;
				this.lazyCustomers.setPageSize(pageSize);
				this.lazyCustomers.setRowCount(this.customerResponseGaussDTO.getTotalItems());

				((CustomerGaussDTOLazyDataModel) this.lazyCustomers)
						.addCollectionToLazyCustomers(this.customerResponseGaussDTO.getClients());
			} else {
				ErrorGaussDTO error = (ErrorGaussDTO) response;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						holderMessage.label("resposta_servidor"), error.getErrorMessage()));
				System.out.println("error message " + ((ErrorGaussDTO) response).getErrorMessage());
				FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Pipeline-Status", "Error");
			}
		} catch (ProcessingException e) {
			if (e.getCause() instanceof SocketTimeoutException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						holderMessage.label("socket_exception"),
						MessageFormat.format(holderMessage.label("socket_exception_detalhes"), e.getMessage())));
			} else if (e.getCause() instanceof TimeoutException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						holderMessage.label("timeout_ler_response"),
						MessageFormat.format(holderMessage.label("timeout_ler_response_detalhes"), e.getMessage())));

			}
			FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Pipeline-Status", "Error");
			e.printStackTrace();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().getExternalContext().addResponseHeader("Pipeline-Status", "Error");
			e.printStackTrace();
		}
	}

	public void getProductsFromWS() {
		this.loadLazyProducts(0, 10, null);
	}

	private void loadLazyProducts(int offset, int pageSize, String code) {
		String endpoint = null;
//		if (code != null) {
//			endpoint = "products/{0}";
//			this.productResponseDTO = this.authRestClient.getForEntity("GAUSS_ORCAMENTO", endpoint,
//					ProductsResponseGaussDTO.class, code);
//		} else {
//			endpoint = "products?page={0}&pageSize={1}";
//			this.authRestClient.getForEntity("GAUSS_ORCAMENTO", endpoint, ProductsResponseGaussDTO.class, offset,
//					pageSize);
//		}

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

	public String getDivLoadingMainMessage() {
		return divLoadingMainMessage;
	}

	public void setDivLoadingMainMessage(String divLoadingMainMessage) {
		this.divLoadingMainMessage = divLoadingMainMessage;
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
}
