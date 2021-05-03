package com.portal.controller;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import org.primefaces.component.api.UIData;
import org.primefaces.component.blockui.BlockUI;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.dto.CustomerGaussDTO;
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

	private CustomerGaussDTO selectedCustomer;

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

	/**
	 * This method will be called when view page is render by async ajax request.
	 */
	public void initTableCustomers() {

		CustomerPage customerPage;
		try {
			customerPage = this.customerRepository.getAllByPage(1, pageSizeForCustomers);
			LazyPopulateUtils.populate(lazyCustomers, customerPage);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void onPageCustomerListener(PageEvent pageEvent) {
		this.globalLoadCustomers(pageEvent.getPage() + 1);
	}

	public void initTableProducts() {
		this.globalLoadProducts(0);
	}

	public void findCustomer() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("code", customerCode);
		pathParams.put("store", customerStore);
//		this.populateCollectionWithSingleRow(null, "clients/{code}/loja/{store}", this.lazyCustomers,
//				CustomerPageGaussDTO.class, pathParams, holderMessage.label("nao_encontrado"),
//				"formClientTb:dtCustomer");

	}

	public void refreshDtCustomers() {
		this.globalLoadCustomers(0);

	}

	public void onPageProducts(PageEvent pageEvent) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", pageEvent.getPage() + 1);
		queryParams.put("pageSize", pageSizeForProducts);

		this.globalLoadProducts(pageEvent.getPage() + 1);
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

	public void globalLoadCustomers(int page) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSizeForCustomers);
//		h5DivLoadCustomers = this.populateLazyCollection(queryParams, "clients", this.lazyCustomers,
//				CustomerPageGaussDTO.class, null, holderMessage.label("impossivel_procurar_clientes"),
//				"formClientTb:dtCustomer") ? holderMessage.label("selecione_cliente")
//						: holderMessage.label("impossivel_carregar_clientes");

	}

	public void globalLoadProducts(int page) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSizeForProducts);
//		this.h5DivLoadProducts = this.populateLazyCollection(queryParams, "products", this.lazyProducts,
//				ProductPageGaussDTO.class, null, holderMessage.label("impossivel_procurar_produtos"),
//				"formProdutos:dtProducts") ? holderMessage.label("selecione_produtos")
//						: holderMessage.label("impossivel_carregar_produtos");

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
