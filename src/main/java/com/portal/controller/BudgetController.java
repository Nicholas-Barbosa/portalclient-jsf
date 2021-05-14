package com.portal.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.ItemEstimateBudgetForm;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.dto.SearchProductForm;
import com.portal.repository.BudgetRepository;
import com.portal.repository.CustomerRepository;
import com.portal.repository.ProductRepository;
import com.portal.service.BudgetService;
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

	private LazyDataModel<ProductDTO> lazyProducts;

	private LazyDataModel<CustomerDTO> lazyCustomers;

	private final HoldMessageView holderMessage;

	private final FacesHelper facesHelper;

	private final CustomerRepository customerRepository;

	private final ProductRepository productRepository;

	private String h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private List<ProductDTO> selectedProducts;

	private Set<ItemEstimateBudgetForm> backedItems;

	private SearchProductForm searchProductForm;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	@EJB
	private BudgetService budgetService;

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
		this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();

	}

	@PostConstruct
	public void init() {
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.h5DivLoadCustomers = holderMessage.label("carregando_clientes");
		this.searchProductForm = new SearchProductForm();
		this.selectedProducts = new ArrayList<>();
		this.backedItems = new HashSet<>();

	}

	public void reEditItemQuantity() {
		budgetService.calculatePreEstiamte();
	}

	public void generateQuote() {
		try {
			budgetService.estimateValues();
			backedItems.clear();
		} catch (SocketTimeoutException | ProcessingException | IllegalArgumentException | SocketException
				| TimeoutException e) {
			facesHelper.exceptionMessage().addMessageByException(null, e);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectCustomer(SelectEvent<CustomerDTO> event) {
		if (event.getObject().getBlocked().equals("Sim")) {
			facesHelper.error("fomrSelectCustomer", holderMessage.label("cliente_bloqueado"), null);
			facesHelper.addHeaderForResponse("customer-isBlocked", true);
			return;
		}
		budgetService.setCustomer(event.getObject());
	}

	public void findCustomerByName(int page) {
		try {
			Optional<CustomerPageDTO> maybeCustomer = this.customerRepository.getByName(nameCustomerToFind, 0, 5);
			maybeCustomer.ifPresentOrElse(c -> {
				if (c.totalItems() > 1) {
					facesHelper.addHeaderForResponse("customers", c.totalItems());
					LazyPopulateUtils.populate(lazyCustomers, c);
				} else {
					System.out.println("Is present " + maybeCustomer.isPresent());
					CustomerDTO cDTO = c.getClients().get(0);
					if (cDTO.getBlocked().equals("Sim")) {
						facesHelper.error("customerDTO", holderMessage.label("cliente_bloqueado"), null);
						budgetService.setCustomer(null);
						return;
					}
					budgetService.setCustomer(cDTO);
				}
			}, () -> {
				facesHelper.error(null, holderMessage.label("cliente_nao_encontrado"), null);
				budgetService.setCustomer(null);
			});
		} catch (SocketTimeoutException | ProcessingException | IllegalArgumentException | SocketException
				| TimeoutException e) {
			facesHelper.exceptionMessage().addMessageByException(null, e);
		} catch (ClientErrorException e) {
			facesHelper.error("customerDTO", holderMessage.label("resposta_servidor"),
					e.getResponse().getEntity().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findCustomerByCode() {
		try {
			Optional<CustomerDTO> maybeCustomer = customerRepository.getByCodeAndStore(searchCustomerDTO);
			maybeCustomer.ifPresentOrElse(c -> {
				if (c.getBlocked().equals("Sim")) {
					facesHelper.error("customerDTO", holderMessage.label("cliente_bloqueado"), null);
					budgetService.setCustomer(null);
				} else {
					budgetService.setCustomer(c);
				}
			}, () -> {
				facesHelper.error("customerDTO", holderMessage.label("cliente_nao_encontrado"), null);
				budgetService.setCustomer(null);
			});
		} catch (ClientErrorException e) {
			facesHelper.error("customerDTO", holderMessage.label("resposta_servidor"),
					e.getResponse().getEntity().toString());
		} catch (Exception e) {
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
				backedItems.add(ItemEstimateBudgetForm.getInstanceFromProduct(presentProduct));
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

	public void removeItem(ItemEstimateBudgetForm item) {
		ExecutorService executor = null;
		try {
			executor = Executors.newFixedThreadPool(2);
			executor.submit(
					() -> this.backedItems.removeIf(currentItem -> currentItem.getCode().equals(item.getCode())));
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

	public void onProductSelected(ProductDTO productDTO) {
		ExecutorService executorService = null;
		try {
			executorService = Executors.newFixedThreadPool(2);
			executorService.execute(() -> backedItems.add(ItemEstimateBudgetForm.getInstanceFromProduct(productDTO)));
			executorService.execute(() -> budgetService.addItem(productDTO));
		} finally {
			executorService.shutdown();
			try {
				executorService.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public LazyDataModel<ProductDTO> getLazyProducts() {
		return lazyProducts;
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

	public Set<ItemEstimateBudgetForm> getItems() {
		return backedItems;
	}

	public void setItems(Set<ItemEstimateBudgetForm> items) {
		this.backedItems = items;
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

	public String getHeaderExceptionDialog() {
		return headerExceptionDialog;
	}

	public SearchCustomerByCodeAndStoreDTO getSearchCustomerDTO() {
		return searchCustomerDTO;
	}

	public String getNameCustomerToFind() {
		return nameCustomerToFind;
	}

	public void setNameCustomerToFind(String nameCustomerToFind) {
		this.nameCustomerToFind = nameCustomerToFind;
	}

	public LazyDataModel<CustomerDTO> getLazyCustomers() {
		return lazyCustomers;
	}

	public BudgetService getBudgetFacade() {
		return budgetService;
	}
}
