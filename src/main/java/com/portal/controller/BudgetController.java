package com.portal.controller;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;

import org.primefaces.component.api.UIData;
import org.primefaces.component.blockui.BlockUI;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.dto.ItemBudgetFormGssDTO;
import com.portal.dto.ProductGaussDTO;
import com.portal.dto.ProductPageGaussDTO;
import com.portal.pojo.Customer;
import com.portal.pojo.CustomerPage;
import com.portal.repository.CustomerRepository;
import com.portal.service.faces.FacesService;
import com.portal.service.view.HoldMessageView;
import com.portal.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.ui.lazy.datamodel.LazyPopulateUtils;
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

	private Customer selectedCustomer;

	private LazyDataModel<Customer> lazyCustomers;

	private LazyDataModel<ProductGaussDTO> lazyProducts;

	private final HoldMessageView holderMessage;

	private final FacesService facesService;

	private final CustomerRepository customerRepository;

	private ProductPageGaussDTO productResponseDTO;

	private String customerCode, customerStore, h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private List<ProductGaussDTO> selectedProducts;

	private Set<ItemBudgetFormGssDTO> items;

	public BudgetController() {
		this(null, null, null);
	}

	@Inject
	public BudgetController(HoldMessageView holderMessage, FacesService facesService,
			CustomerRepository customerRepository) {
		super();
		this.lazyProducts = new ProductGaussDTOLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.holderMessage = holderMessage;
		this.facesService = facesService;
		this.customerRepository = customerRepository;

	}

	@PostConstruct
	public void init() {
		this.h5DivLoadCustomers = holderMessage.label("carregando_clientes");
		this.items = new ConcurrentSkipListSet<>();
	}

	public void initTableCustomers() {
		fetchCustomers(1);
	}

	public void onPageCustomerListener(PageEvent pageEvent) {
		int page = pageEvent.getPage();
		fetchCustomers(++page);
	}

	public void initTableProducts() {

	}

	public void findCustomerByCodeAndStore() {
		try {
			Optional<Customer> opCustomer = customerRepository.getByCodeAndStore(customerCode, customerStore);
			opCustomer.ifPresentOrElse(c -> {
				LazyPopulateUtils.populateSingleRow(lazyCustomers, c);
			}, () -> {
				this.facesService.error(null, holderMessage.label("nao_encontrado"), null);
				((CustomerLazyDataModel) this.lazyCustomers).clearCollection();
				this.lazyCustomers.setPageSize(0);
				this.lazyCustomers.setRowCount(0);
			});
		} catch (SocketTimeoutException e) {
			this.facesService
					.error(null, holderMessage.label("socket_exception"),
							holderMessage.label("socket_exception_detalhes"))
					.addHeaderForResponse("Backbone-Status", "Error");
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			this.facesService
					.error(null, holderMessage.label("connect_exception"),
							holderMessage.label("connect_exception_detales"))
					.addHeaderForResponse("Backbone-Status", "Error");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			this.facesService
					.error(null, holderMessage.label("respota_invalida_servidor"),
							holderMessage.label("detalhes_reposta_invalida_servidor"))
					.addHeaderForResponse("Backbone-Status", "Error");
		} catch (TimeoutException e) {
			this.facesService
					.error(null, holderMessage.label("timeout_ler_response"),
							holderMessage.label("timeout_ler_response_detalhes"))
					.addHeaderForResponse("Backbone-Status", "Error");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshDtCustomers() {

	}

	public void onPageProducts(PageEvent pageEvent) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", pageEvent.getPage() + 1);
		queryParams.put("pageSize", pageSizeForProducts);

	}

	public void onProductSelected(SelectEvent<ProductGaussDTO> selectedProduct) {
		ProductGaussDTO product = selectedProduct.getObject();
		items.add(new ItemBudgetFormGssDTO(product.getCode(), product.getDescriptionType(), product.getCommercialCode(),
				product.getDescriptionType(), 10));

	}

	public void unProductUnSelected(UnselectEvent<ProductGaussDTO> unSelectProduct) {
		ProductGaussDTO product = unSelectProduct.getObject();
		items.removeIf(p -> p.getCommercialCode().equals(product.getCommercialCode()));

	}

	private void fetchCustomers(int page) {
		CustomerPage customerPage;
		try {
			customerPage = this.customerRepository.getAllByPage(page, pageSizeForCustomers);
			LazyPopulateUtils.populate(lazyCustomers, customerPage);
		} catch (SocketTimeoutException e) {
			this.facesService
					.error("formClientTb:dtCustomer", holderMessage.label("socket_exception"),
							holderMessage.label("socket_exception_detalhes"))
					.addHeaderForResponse("Backbone-Status", "Error");
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			this.facesService
					.error("formClientTb:dtCustomer", holderMessage.label("connect_exception"),
							holderMessage.label("connect_exception_detales"))
					.addHeaderForResponse("Backbone-Status", "Error");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			this.facesService
					.error("formClientTb:dtCustomer", holderMessage.label("respota_invalida_servidor"),
							holderMessage.label("detalhes_reposta_invalida_servidor"))
					.addHeaderForResponse("Backbone-Status", "Error");
		} catch (TimeoutException e) {
			this.facesService
					.error("formClientTb:dtCustomer", holderMessage.label("timeout_ler_response"),
							holderMessage.label("timeout_ler_response_detalhes"))
					.addHeaderForResponse("Backbone-Status", "Error");

		} catch (NotAuthorizedException e) {
			this.facesService
					.error("formClientTb:dtCustomer", holderMessage.label("nao_encontrado"),
							holderMessage.label("usuario_nao_encontrado"))
					.addHeaderForResponse("Backbone-Status", "Error");

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

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public LazyDataModel<Customer> getLazyCustomers() {
		return lazyCustomers;
	}

	public void setLazyCustomers(LazyDataModel<Customer> lazyCustomers) {
		this.lazyCustomers = lazyCustomers;
	}

	public ProductPageGaussDTO getProductResponseDTO() {
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

	public List<ProductGaussDTO> getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(List<ProductGaussDTO> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public Set<ItemBudgetFormGssDTO> getItems() {
		return items;
	}

	public void setItems(Set<ItemBudgetFormGssDTO> items) {
		this.items = items;
	}
}
