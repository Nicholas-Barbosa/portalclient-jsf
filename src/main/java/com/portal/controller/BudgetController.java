package com.portal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.api.UIData;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.controller.util.ExceptionMessageHandler;
import com.portal.dto.ItemQuoteBudgetForm;
import com.portal.dto.ProductGaussDTO;
import com.portal.dto.SearchProductForm;
import com.portal.pojo.Customer;
import com.portal.pojo.CustomerPage;
import com.portal.pojo.Product;
import com.portal.pojo.ProductPage;
import com.portal.repository.CustomerRepository;
import com.portal.repository.ProductRepository;
import com.portal.service.faces.FacesHelper;
import com.portal.service.view.HoldMessageView;
import com.portal.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.ui.lazy.datamodel.ProductLazyDataModel;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient UIData uiCustomerDataTable;

	private Customer selectedCustomer;

	private LazyDataModel<Customer> lazyCustomers;

	private LazyDataModel<Product> lazyProducts;

	private final HoldMessageView holderMessage;

	private final FacesHelper facesService;

	private final CustomerRepository customerRepository;

	private final ExceptionMessageHandler exceptionMessageHandler;

	private final ProductRepository productRepository;
	private String customerCode, customerStore, h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private List<ProductGaussDTO> selectedProducts;

	private Set<ItemQuoteBudgetForm> items;

	private SearchProductForm searchProductForm;

	public BudgetController() {
		this(null, null, null, null, null);
	}

	@Inject
	public BudgetController(HoldMessageView holderMessage, FacesHelper facesService,
			CustomerRepository customerRepository, ExceptionMessageHandler exceptionMessageHandler,
			ProductRepository productRepository) {
		super();

		this.holderMessage = holderMessage;
		this.facesService = facesService;
		this.customerRepository = customerRepository;
		this.exceptionMessageHandler = exceptionMessageHandler;
		this.productRepository = productRepository;
	}

	@PostConstruct
	public void init() {
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.h5DivLoadCustomers = holderMessage.label("carregando_clientes");
		this.searchProductForm = new SearchProductForm();
		this.items = new HashSet<>();
	}

	public void initTableCustomers() {
		fetchCustomers(1);
	}

	public void onPageCustomerListener(PageEvent pageEvent) {
		int page = pageEvent.getPage();
		fetchCustomers(++page);
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

		} catch (Exception e) {
			this.facesService.addHeaderForResponse("Backbone-Status", "Error");
			exceptionMessageHandler.addMessageByException(null, e);

		}
	}

	public void refreshDtCustomers() {

	}

	public void findProductByDescription() {
		try {
			ProductPage product = productRepository.getByDescription(0, 10, searchProductForm.getDescription());
			((ProductLazyDataModel) (lazyProducts)).addCollection(new ArrayList<>(product.getContent()));
		} catch (Exception e) {
			exceptionMessageHandler.addMessageByException(null, e);
			e.printStackTrace();
		}
	}

	public void findProductByCode() {
		try {
			Optional<Product> product = productRepository.getByCode(searchProductForm.getCode());
			product.ifPresentOrElse(presentProduct -> {
				items.add(ItemQuoteBudgetForm.getInstanceFromProduct(presentProduct));
			}, () -> {
				facesService.error(null, holderMessage.label("nao_encontrado"), null);

			});

		} catch (Exception e) {
			exceptionMessageHandler.addMessageByException(null, e);
			e.printStackTrace();
		}
	}

	public void onCellItemEdit(CellEditEvent<Integer> event) {
		((ItemQuoteBudgetForm) items.parallelStream().collect(CopyOnWriteArrayList::new, List::add, List::addAll)
				.get(event.getRowIndex())).setQuantity(event.getNewValue());

	}

	public void removeItem(ItemQuoteBudgetForm item) {
		this.items.removeIf(currentItem -> currentItem.getCode().equals(item.getCode()));
	}

	public void onPageProducts(PageEvent pageEvent) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("page", pageEvent.getPage() + 1);
		queryParams.put("pageSize", pageSizeForProducts);

	}

	public void onProductSelected(SelectEvent<ProductGaussDTO> selectedProduct) {
		ProductGaussDTO product = selectedProduct.getObject();
		items.add(new ItemQuoteBudgetForm(product.getCode(), product.getDescriptionType(), product.getCommercialCode(),
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
		} catch (Exception e) {
			this.facesService.addHeaderForResponse("Backbone-Status", "Error");
			exceptionMessageHandler.addMessageByException("formClientTb:dtCustomer", e);
		}

	}

	/**
	 * Method triggered by rowSelect ajax event
	 */
	public void onCustomerSelected() {
		// this.uiCustomerDataTable.setRendered(false);
	}

	public UIData getUiCustomerDataTable() {
		return uiCustomerDataTable;
	}

	public void setUiCustomerDataTable(UIData uiCustomerDataTable) {
		this.uiCustomerDataTable = uiCustomerDataTable;
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

	public LazyDataModel<Product> getLazyProducts() {
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

	public Set<ItemQuoteBudgetForm> getItems() {
		return items;
	}

	public void setItems(Set<ItemQuoteBudgetForm> items) {
		this.items = items;
	}

	public SearchProductForm getSearchProductForm() {
		return searchProductForm;
	}

	public void setSearchProductForm(SearchProductForm searchProductForm) {
		this.searchProductForm = searchProductForm;
	}
}
