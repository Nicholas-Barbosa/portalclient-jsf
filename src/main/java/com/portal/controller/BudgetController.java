package com.portal.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetEstimatedDTO;
import com.portal.dto.BudgetJasperReportDTO;
import com.portal.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.DownloadStreamsForm;
import com.portal.dto.EstimatedItem;
import com.portal.dto.ItemEstimateBudgetForm;
import com.portal.dto.ItemFormDTO;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.dto.SearchProductForm;
import com.portal.jasper.service.BudgetReport;
import com.portal.jsf.faces.FacesHelper;
import com.portal.jsf.faces.ProcessingExceptionMessageHelper;
import com.portal.jsf.primefaces.PrimeFHelper;
import com.portal.repository.CustomerRepository;
import com.portal.repository.ProductRepository;
import com.portal.service.BudgetService;
import com.portal.service.ResourceBundleService;
import com.portal.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.ui.lazy.datamodel.ProductLazyDataModel;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537974193888491899L;

	private final ResourceBundleService resourceBundleService;

	private final CustomerRepository customerRepository;

	private final ProductRepository productRepository;

	private final BudgetService budgetService;

	private final BudgetReport budgetReport;

	private final ClientErrorExceptionController responseController;

	private final ProcessingExceptionMessageHelper processingExceptionMessageHelper;

	private LazyDataModel<ProductDTO> lazyProducts;

	private LazyDataModel<CustomerDTO> lazyCustomers;

	private String h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private Set<ItemFormDTO> originalItems;

	private Set<ProductDTO> selectedProducts;

	private SearchProductForm searchProductForm;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	private CustomerDTO selectedCustomer;

	private BudgetEstimatedDTO budgetEstimateDTO;

	private DownloadStreamsForm downloadStreamsForm;

	private EstimatedItem selectedItemToViewStock;

	public BudgetController() {
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public BudgetController(ResourceBundleService resourceBundleService, CustomerRepository customerRepository,
			ProductRepository productRepository, BudgetService budgetService, BudgetReport budgetReport,
			ClientErrorExceptionController responseController,
			ProcessingExceptionMessageHelper processingExceptionMessageHelper) {
		super();
		this.resourceBundleService = resourceBundleService;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.budgetService = budgetService;
		this.budgetReport = budgetReport;
		this.responseController = responseController;
		this.processingExceptionMessageHelper = processingExceptionMessageHelper;
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.selectedProducts = new HashSet<>();
		originalItems = new HashSet<>();
		this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
		this.downloadStreamsForm = new DownloadStreamsForm();
		this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
		this.searchProductForm = new SearchProductForm();
	}

	public void openViewInDialog() {
		PrimeFHelper.openClientErrorExceptionView(Response.status(201).entity("OK NICHOLAS!").build());
	}

	public void clearBudgetForm() throws InterruptedException {
		ExecutorService executor = null;
		try {
			executor = Executors.newFixedThreadPool(4);
			executor.execute(() -> selectedCustomer = null);
			executor.execute(() -> originalItems.clear());
			executor.execute(() -> selectedProducts.clear());
			executor.execute(() -> budgetEstimateDTO = null);
		} finally {
			executor.shutdown();
			executor.awaitTermination(20, TimeUnit.MILLISECONDS);
		}

	}

	public void exportReport() {
		try {
			BudgetJasperReportDTO jasperDTO = new BudgetJasperReportDTO(budgetEstimateDTO.getLiquidValue(),
					budgetEstimateDTO.getGrossValue(), budgetEstimateDTO.getStTotal(),
					new CustomerJasperReportDTO(selectedCustomer.getName(), selectedCustomer.getCity(),
							selectedCustomer.getAddress(), selectedCustomer.getState(), selectedCustomer.getCgc()),
					budgetEstimateDTO.getItems());
			byte[] btes = budgetReport.export(jasperDTO, downloadStreamsForm.getContentType());
			FacesHelper.prepareResponseHeadersResponseForDownloadOfStreams(downloadStreamsForm.getName(), btes,
					downloadStreamsForm.getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void estimate() {
		try {
			new Thread(() -> selectedProducts.clear()).start();
			budgetEstimateDTO = budgetService.estimate(
					new BudgetEstimateForm(selectedCustomer.getCode(), selectedCustomer.getStore(), originalItems));
		} catch (ProcessingException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
		} catch (EJBException e) {
			if (e.getCause() instanceof ClientErrorException) {
				ClientErrorException not = (ClientErrorException) e.getCause();
				PrimeFHelper.openClientErrorExceptionView(not.getResponse());
				FacesHelper.addHeaderForResponse("Backbone-Status", "Error");
			} else
				throw e;
		}

	}

	public void selectCustomer(SelectEvent<CustomerDTO> event) {
		if (event.getObject().getBlocked().equals("Sim")) {
			FacesHelper.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
			FacesHelper.addHeaderForResponse("customer-isBlocked", true);
			return;
		}
		selectedCustomer = event.getObject();
	}

	public void onPageCustomers(PageEvent pageEvent) {
		findCustomerByName(pageEvent.getPage() + 1);
	}

	public void findCustomerByName(int page) {
		try {
			Optional<CustomerPageDTO> maybeCustomer = this.customerRepository.getByName(nameCustomerToFind, page, 5);
			maybeCustomer.ifPresentOrElse(c -> {
				if (c.totalItems() > 1) {
					FacesHelper.addHeaderForResponse("customers", c.totalItems());
					LazyPopulateUtils.populate(lazyCustomers, c);
				} else {
					CustomerDTO cDTO = c.getClients().get(0);
					if (cDTO.getBlocked().equals("Sim")) {
						FacesHelper.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
						selectedCustomer = null;
						return;
					}
					selectedCustomer = cDTO;
				}
			}, () -> {
				FacesHelper.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				selectedCustomer = null;
			});
		} catch (ProcessingException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
		} catch (ClientErrorException e) {
			FacesHelper.error("customerDTO", resourceBundleService.getMessage("resposta_servidor"),
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
					FacesHelper.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
					selectedCustomer = null;
				} else {
					selectedCustomer = c;
				}
			}, () -> {
				FacesHelper.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				selectedCustomer = null;
			});
		} catch (ClientErrorException e) {
			FacesHelper.error(null, resourceBundleService.getMessage("resposta_servidor"),
					e.getResponse().getEntity().toString());
			selectedCustomer = null;
		} catch (ProcessingException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
			selectedCustomer = null;
		}
	}

	public void findProductByDescription(int page) {
		try {
			Optional<ProductPageDTO> maybeProduct = productRepository.getByDescription(page, pageSizeForProducts,
					searchProductForm.getDescription());
			maybeProduct.ifPresentOrElse(p -> {
				LazyPopulateUtils.populate(lazyProducts, p);
			}, () -> {
				FacesHelper.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
				FacesHelper.addHeaderForResponse("Backbone-Status", "Error");
			});

		} catch (ProcessingException e) {
			processingExceptionMessageHelper.displayMessage(e, null);
			FacesHelper.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		}
	}

	public void findProductByCode() {
		try {
			Optional<ProductDTO> product = productRepository.getByCode(searchProductForm.getCode());
			product.ifPresentOrElse(presentProduct -> {
				new Thread(() -> originalItems
						.add(new ItemFormDTO(presentProduct.getCode(), presentProduct.getMultiple()))).start();
				selectedProducts.add(presentProduct);
				FacesHelper.info(null, resourceBundleService.getMessage("produto_selecionado"), null);
			}, () -> {
				FacesHelper.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
			});

		} catch (ProcessingException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
			FacesHelper.addHeaderForResponse("Backbone-Status", "Error");
			// e.printStackTrace();
		}
	}

	public void onPageProducts(PageEvent pageEvent) {
		findProductByDescription(pageEvent.getPage() + 1);
	}

	public void reEditItemQuantity(RowEditEvent<EstimatedItem> event) {
		new Thread(() -> originalItems.parallelStream()
				.filter(i -> i.getCommercialCode().equals(event.getObject().getCommercialCode()))
				.forEach(i -> i.changeQuantity(event.getObject().getQuantity()))).start();

		budgetService.updateQuantity(budgetEstimateDTO, event.getObject());

	}

	public void removeEstimatedItem(EstimatedItem item) {
		new Thread(() -> originalItems.removeIf(i -> i.getCommercialCode().equals(item.getCommercialCode()))).start();
		new Thread(() -> selectedProducts.removeIf(i -> i.getCommercialCode().equals(item.getCommercialCode())))
				.start();
		budgetService.removeItem(budgetEstimateDTO, item);
	}

	public void removeSelectProduct(ItemEstimateBudgetForm item) {
		// this.selectItems.removeIf(currentItem ->
		// currentItem.getCode().equals(item.getCode()));
	}

	public void onProductSelected(ProductDTO productDTO) {
		ExecutorService executorService = null;
		try {
			executorService = Executors.newFixedThreadPool(2);
			executorService.execute(() -> selectedProducts.add(productDTO));
			executorService.execute(() -> {
				originalItems.add(new ItemFormDTO(productDTO.getCommercialCode(), productDTO.getMultiple()));
			});

		} finally {
			executorService.shutdown();
			try {
				executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
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

	public Set<ProductDTO> getSelectedProducts() {
		return selectedProducts;
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

	public CustomerDTO getSelectedCustomer() {
		return selectedCustomer;
	}

	public BudgetEstimatedDTO getBudgetEstimateDTO() {
		return budgetEstimateDTO;
	}

	public DownloadStreamsForm getDownloadStreamsForm() {
		return downloadStreamsForm;
	}

	public EstimatedItem getSelectedItemToViewStock() {
		return selectedItemToViewStock;
	}

	public void setSelectedItemToViewStock(EstimatedItem selectedItemToViewStock) {
		this.selectedItemToViewStock = selectedItemToViewStock;
	}

	public ClientErrorExceptionController getResponseController() {
		return responseController;
	}
}
