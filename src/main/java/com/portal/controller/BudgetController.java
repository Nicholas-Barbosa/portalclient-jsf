package com.portal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;

import org.primefaces.component.api.UIData;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.ItemQuoteBudgetForm;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.dto.SearchProductForm;
import com.portal.repository.BudgetRepository;
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

	private CustomerDTO selectedCustomer;

	private LazyDataModel<CustomerDTO> lazyCustomers;

	private LazyDataModel<ProductDTO> lazyProducts;

	private final HoldMessageView holderMessage;

	private final FacesHelper facesHelper;

	private final CustomerRepository customerRepository;

	private final ProductRepository productRepository;

	private final BudgetRepository budgetRepository;

	private String customerCode, customerStore, h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private List<ProductDTO> selectedProducts;

	private Set<ItemQuoteBudgetForm> items;

	private SearchProductForm searchProductForm;

	private String headerExceptionDialog;
	private String processingEntity;

	private BudgetEstimateDTO budgetEstimate;

	public BudgetController() {
		this(null, null, null, null, null);
	}

	@Inject
	public BudgetController(HoldMessageView holderMessage, FacesHelper facesService,
			CustomerRepository customerRepository, ProductRepository productRepository,
			BudgetRepository budgetRepository) {
		super();

		this.holderMessage = holderMessage;
		this.facesHelper = facesService;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.budgetRepository = budgetRepository;
	}

	@PostConstruct
	public void init() {
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.h5DivLoadCustomers = holderMessage.label("carregando_clientes");
		this.searchProductForm = new SearchProductForm();
		this.selectedProducts = new ArrayList<>();
		this.items = new HashSet<>();
	}

	public void reEditItemQuantity() {
		budgetEstimate = budgetRepository.recalculateEstimate(budgetEstimate);

	}

	public void generateQuote() {
		BudgetEstimateForm budgetForm = new BudgetEstimateForm(selectedCustomer.getCode(), selectedCustomer.getStore(),
				items);
		try {
			budgetEstimate = this.budgetRepository.estimate(budgetForm);
			budgetEstimate.setStTotal();
			budgetEstimate.setCustomer(selectedCustomer);
			facesHelper.info(null, holderMessage.label("estimativa_orcamento_gerado"), null);
		} catch (ClientErrorException e) {
			this.processingEntity = e.getResponse().readEntity(String.class);
			facesHelper.addHeaderForResponse("Backbone-Status", "Error");
			this.headerExceptionDialog = holderMessage.label("erro_msg_servidor");
		} catch (InternalServerErrorException e) {
			this.processingEntity = holderMessage.label("erro_servidor_destino");
			this.headerExceptionDialog = holderMessage.label("resposta_servidor");
			facesHelper.addHeaderForResponse("Backbone-Status", "Error");
		} catch (Exception e) {
			e.printStackTrace();
			facesHelper.exceptionMessage().addMessageByException(null, e);

		}
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
			Optional<CustomerDTO> opCustomer = customerRepository.getByCodeAndStore(customerCode, customerStore);
			opCustomer.ifPresentOrElse(c -> {
				LazyPopulateUtils.populateSingleRow(lazyCustomers, c);
			}, () -> {
				this.facesHelper.error(null, holderMessage.label("nao_encontrado"), null);
				((CustomerLazyDataModel) this.lazyCustomers).clearCollection();
				this.lazyCustomers.setPageSize(0);
				this.lazyCustomers.setRowCount(0);
			});

		} catch (Exception e) {
			this.facesHelper.addHeaderForResponse("Backbone-Status", "Error");
			facesHelper.exceptionMessage().addMessageByException(null, e);

		}
	}

	public void findProductByDescription(int page) {
		try {
			Optional<ProductPageDTO> maybeProduct = productRepository.getByDescription(page, pageSizeForProducts,
					searchProductForm.getDescription());
			maybeProduct.ifPresentOrElse(p -> {
				LazyPopulateUtils.populate(lazyProducts, p);
			}, () -> {
				facesHelper.error(null, holderMessage.label("nao_encontrado"), null);
				this.facesHelper.addHeaderForResponse("Backbone-Status", "Error");
			});

		} catch (Exception e) {
			facesHelper.exceptionMessage().addMessageByException(null, e);
			this.facesHelper.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		}
	}

	public void findProductByCode() {
		try {
			Optional<ProductDTO> product = productRepository.getByCode(searchProductForm.getCode());
			product.ifPresentOrElse(presentProduct -> {
				items.add(ItemQuoteBudgetForm.getInstanceFromProduct(presentProduct));
			}, () -> {
				facesHelper.error(null, holderMessage.label("nao_encontrado"), null);

			});

		} catch (Exception e) {
			facesHelper.exceptionMessage().addMessageByException(null, e);
			this.facesHelper.addHeaderForResponse("Backbone-Status", "Error");
			// e.printStackTrace();
		}
	}

	public void onPageProducts(PageEvent pageEvent) {
		findProductByDescription(pageEvent.getPage() + 1);

	}

	public void removeItem(ItemQuoteBudgetForm item) {
		ExecutorService executor = null;
		try {
			executor = Executors.newFixedThreadPool(2);
			executor.submit(() -> this.items.removeIf(currentItem -> currentItem.getCode().equals(item.getCode())));
			executor.submit(() -> this.selectedProducts.removeIf(p -> p.getCode().equals(item.getCode())));
		} finally {
			executor.shutdown();
			try {
				executor.awaitTermination(1, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void removeTest() {
		this.selectedProducts.clear();
	}

	public void onProductSelected(SelectEvent<ProductDTO> selectedProduct) {
		ProductDTO product = selectedProduct.getObject();
		items.add(new ItemQuoteBudgetForm(product.getCode(), product.getDescriptionType(), product.getCommercialCode(),
				product.getDescriptionType(), 1));

	}

	public void unProductUnSelected(UnselectEvent<ProductDTO> unSelectProduct) {
		ProductDTO product = unSelectProduct.getObject();
		items.removeIf(p -> p.getCommercialCode().equals(product.getCommercialCode()));

	}

	private void fetchCustomers(int page) {
		CustomerPageDTO customerPage;
		try {
			customerPage = this.customerRepository.getAllByPage(page, pageSizeForCustomers);
			LazyPopulateUtils.populate(lazyCustomers, customerPage);
		} catch (Exception e) {
			this.facesHelper.addHeaderForResponse("Backbone-Status", "Error");
			facesHelper.exceptionMessage().addMessageByException("formClientTb:dtCustomer", e);
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

	public CustomerDTO getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(CustomerDTO selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public LazyDataModel<CustomerDTO> getLazyCustomers() {
		return lazyCustomers;
	}

	public LazyDataModel<ProductDTO> getLazyProducts() {
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

	public List<ProductDTO> getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(List<ProductDTO> selectedProducts) {
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

	public String getProcessingEntity() {
		return processingEntity;
	}

	public BudgetEstimateDTO getBudgetEstimate() {
		return budgetEstimate;
	}

	public String getHeaderExceptionDialog() {
		return headerExceptionDialog;
	}
}
