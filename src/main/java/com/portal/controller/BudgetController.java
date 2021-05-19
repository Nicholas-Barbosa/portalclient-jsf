package com.portal.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PreDestroy;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.dto.BudgetEstimateDTO;
import com.portal.dto.BudgetEstimateDTO.EstimatedItem;
import com.portal.dto.BudgetEstimateForm;
import com.portal.dto.BudgetJasperReportDTO;
import com.portal.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.DownloadStreamsForm;
import com.portal.dto.ItemEstimateBudgetForm;
import com.portal.dto.ProductDTO;
import com.portal.dto.ProductPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.dto.SearchProductForm;
import com.portal.jasper.service.BudgetReport;
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

	private Set<ItemEstimateBudgetForm> originalItems;

	private Set<ItemEstimateBudgetForm> preItems;

	private SearchProductForm searchProductForm;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	@Inject
	private BudgetService budgetService;

	private CustomerDTO selectedCustomer;

	private BudgetEstimateDTO budgetEstimateDTO;

	@Inject
	private BudgetReport budgetReport;

	private DownloadStreamsForm downloadStreamsForm;

	@Inject
	private HttpSession httpSession;

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
		this.searchProductForm = new SearchProductForm();
		this.preItems = new HashSet<>();
		originalItems = new HashSet<>();
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.downloadStreamsForm = new DownloadStreamsForm();
	}

	public void invalidSession() {
		httpSession.invalidate();
	}

	public void testeRedirect() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportReport() {
		try {
			BudgetJasperReportDTO jasperDTO = new BudgetJasperReportDTO(budgetEstimateDTO.getLiquidValue(),
					budgetEstimateDTO.getGrossValue(), budgetEstimateDTO.getStTotal(),
					new CustomerJasperReportDTO(selectedCustomer.getName(), selectedCustomer.getCity(),
							selectedCustomer.getAddress(), selectedCustomer.getState(), selectedCustomer.getCgc()),
					budgetEstimateDTO.getEstimatedItemValues());
			byte[] btes = budgetReport.toPdf(jasperDTO);
			facesHelper.downloadHelper().downloadPdf(downloadStreamsForm.getName(), btes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reEditItemQuantity(RowEditEvent<EstimatedItem> event) {
		new Thread(() -> originalItems.parallelStream()
				.filter(i -> i.getCommercialCode().equals(event.getObject().getCommercialCode()))
				.forEach(i -> i.setQuantity(event.getObject().getQuantity()))).start();
		;
		budgetService.updateQuantity(budgetEstimateDTO, event.getObject());
	}

	public void generateQuote() {
		try {
			budgetEstimateDTO = budgetService.estimate(
					new BudgetEstimateForm(selectedCustomer.getCode(), selectedCustomer.getStore(), originalItems));
			preItems.clear();
		} catch (SocketTimeoutException | ProcessingException | IllegalArgumentException | SocketException
				| TimeoutException e) {
			facesHelper.exceptionMessage().addMessageByException(null, e);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectCustomer(SelectEvent<CustomerDTO> event) {
		if (event.getObject().getBlocked().equals("Sim")) {
			facesHelper.error(null, holderMessage.label("cliente_bloqueado"), null);
			facesHelper.addHeaderForResponse("customer-isBlocked", true);
			return;
		}
		selectedCustomer = event.getObject();
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
						facesHelper.error(null, holderMessage.label("cliente_bloqueado"), null);
						selectedCustomer = null;
						return;
					}
					selectedCustomer = cDTO;
				}
			}, () -> {
				facesHelper.error(null, holderMessage.label("cliente_nao_encontrado"), null);
				selectedCustomer = null;
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
					facesHelper.error(null, holderMessage.label("cliente_bloqueado"), null);
					selectedCustomer = null;
				} else {
					selectedCustomer = c;
				}
			}, () -> {
				facesHelper.error("customerDTO", holderMessage.label("cliente_nao_encontrado"), null);
				selectedCustomer = null;
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
				preItems.add(ItemEstimateBudgetForm.of(presentProduct));
				facesHelper.info(null, holderMessage.label("produto_selecionado"), null);
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

	public void removePreItems(ItemEstimateBudgetForm item) {
		this.preItems.removeIf(currentItem -> currentItem.getCode().equals(item.getCode()));
	}

	public void onProductSelected(ProductDTO productDTO) {
		ExecutorService executorService = null;
		try {
			executorService = Executors.newFixedThreadPool(2);
			executorService.execute(() -> preItems.add(ItemEstimateBudgetForm.of(productDTO)));
			executorService.execute(() -> {
				originalItems.add(ItemEstimateBudgetForm.of(productDTO));
			});

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

	public Set<ItemEstimateBudgetForm> getPreItems() {
		return preItems;
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

	public BudgetEstimateDTO getBudgetEstimateDTO() {
		return budgetEstimateDTO;
	}

	public DownloadStreamsForm getDownloadStreamsForm() {
		return downloadStreamsForm;
	}
}
