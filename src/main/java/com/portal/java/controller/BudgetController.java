package com.portal.java.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.java.dto.BudgetDTO;
import com.portal.java.dto.BudgetEstimateForm;
import com.portal.java.dto.BudgetEstimatedDTO;
import com.portal.java.dto.BudgetJasperReportDTO;
import com.portal.java.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.java.dto.BudgetXlsxPreviewForm;
import com.portal.java.dto.BudgetXlsxPreviewedDTO;
import com.portal.java.dto.CustomerDTO;
import com.portal.java.dto.CustomerPageDTO;
import com.portal.java.dto.DownloadStreamsForm;
import com.portal.java.dto.EstimatedItemDTO;
import com.portal.java.dto.FindProductByCodeForm;
import com.portal.java.dto.FindProductByDescriptionDTO;
import com.portal.java.dto.ProductBudgetFormDTO;
import com.portal.java.dto.ProductDTO;
import com.portal.java.dto.ProductPageDTO;
import com.portal.java.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.java.jasper.service.BudgetReport;
import com.portal.java.service.BudgetService;
import com.portal.java.service.CustomerService;
import com.portal.java.service.ProductService;
import com.portal.java.service.ResourceBundleService;
import com.portal.java.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.java.ui.lazy.datamodel.LazyOperations;
import com.portal.java.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.java.ui.lazy.datamodel.ProductLazyDataModel;
import com.portal.java.util.jsf.ClientExceptionFacesUtils;
import com.portal.java.util.jsf.FacesUtils;
import com.portal.java.util.jsf.ResourceExceptionMessageHelper;

@Named
@ViewScoped
public class BudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537974193888491899L;

	private final ResourceBundleService resourceBundleService;

	private final CustomerService customerService;

	private final BudgetService budgetService;

	private final BudgetReport budgetReport;

	private final ClientErrorExceptionController responseController;

	private final ResourceExceptionMessageHelper processingExceptionMessageHelper;

	private final ProductService productService;

	private LazyDataModel<ProductDTO> lazyProducts;

	private LazyDataModel<CustomerDTO> lazyCustomers;

	private String h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private Set<ProductBudgetFormDTO> itemsOnCartToPost;

	private Set<ProductDTO> selectedProducts;

	private FindProductByDescriptionDTO findProductByDescriptionDTO;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	private CustomerDTO selectedCustomer;

	private BudgetEstimatedDTO budgetEstimateDTO;

	private DownloadStreamsForm downloadStreamsForm;

	private EstimatedItemDTO selectedItemToViewStock;

	private FindProductByCodeForm findProductByCodeForm;

	private ProductDTO previewProduct;

	private byte[] imageToSeeOnDlg;

	private BudgetXlsxPreviewForm budgetImportXlsxForm;

	private BudgetXlsxPreviewedDTO budgetXlsxPreview;

	private BudgetDTO budgetDTO;

	public BudgetController() {
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public BudgetController(ResourceBundleService resourceBundleService, CustomerService customerService,
			BudgetService budgetService, BudgetReport budgetReport, ClientErrorExceptionController responseController,
			ResourceExceptionMessageHelper processingExceptionMessageHelper, ProductService productService) {
		super();
		bulkInstantiationObjectsInBackGround();
		this.resourceBundleService = resourceBundleService;
		this.customerService = customerService;
		this.budgetService = budgetService;
		this.budgetReport = budgetReport;
		this.responseController = responseController;
		this.processingExceptionMessageHelper = processingExceptionMessageHelper;
		this.productService = productService;
		this.imageToSeeOnDlg = new byte[0];
	}

	public void addPreviewProductToBudget() {
		budgetDTO.addProduct(previewProduct);
		previewProduct = null;
	}

	public void changeProductDiscount() {
		productService.changeProductDiscount(previewProduct);
	}

	public void changProductQuantity() {
		productService.changeProductQuantity(previewProduct);
	}

	public void previewBudgetXlsxContent() {
		budgetXlsxPreview = budgetService.previewXlsxContent(budgetImportXlsxForm);
	}

	public void handleFileUpload(FileUploadEvent event) {
		budgetImportXlsxForm.setXlsxStreams(event.getFile().getContent());
	}

	public void estimate() {
		try {
			new Thread(() -> {
				selectedProducts.clear();
				LazyOperations<?> lazy = (LazyOperations<?>) lazyProducts;
				lazy.turnCollectionElegibleToGB();
			}).start();
			budgetEstimateDTO = budgetService.estimate(
					new BudgetEstimateForm(selectedCustomer.getCode(), selectedCustomer.getStore(), itemsOnCartToPost));
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			processingExceptionMessageHelper.displayMessage(e, null);
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
		} catch (ClientErrorException e) {
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			ClientExceptionFacesUtils.openClientExcpetionView(e.getResponse());

		}

	}

	public void onItemRowEdit(RowEditEvent<ProductDTO> event) {
		budgetService.recalculate(budgetDTO, event.getObject());

	}

	public void showOpenedTitlesPageOnDialog() {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("position", "center");
		options.put("contentWidth", "80vw");
		options.put("contentHeight", "70vh");
		options.put("responsive", "true");
		PrimeFaces.current().dialog().openDynamic("openedTitles", options, null);
	}

	public void loadImageFromPreviewProduct() {
		this.productService.loadImage(previewProduct);
	}

	public void loadImageForProduct(ProductDTO product) {
		this.productService.loadImage(product);
	}

	public void clearBudgetForm() throws InterruptedException {
		ExecutorService executor = null;
		try {
			executor = Executors.newFixedThreadPool(3);
			executor.execute(() -> {
				selectedCustomer = null;
				budgetEstimateDTO = null;
				previewProduct = null;
			});
			executor.execute(() -> itemsOnCartToPost.clear());
			executor.execute(() -> selectedProducts.clear());
		} finally {
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.MILLISECONDS);
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
			FacesUtils.prepareResponseHeadersResponseForDownloadOfStreams(downloadStreamsForm.getName(), btes,
					downloadStreamsForm.getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectCustomer(SelectEvent<CustomerDTO> event) {
		if (event.getObject().getBlocked().equals("Sim")) {
			FacesUtils.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
			FacesUtils.addHeaderForResponse("customer-isBlocked", true);
			return;
		}
		budgetDTO.setCustomer(event.getObject());
		LazyOperations<?> lazy = (LazyOperations<?>) lazyCustomers;
		lazy.turnCollectionElegibleToGB();
	}

	public void onPageCustomers(PageEvent pageEvent) {
		findCustomerByName(pageEvent.getPage() + 1);
	}

	public void findCustomerByName(int page) {
		try {
			Optional<CustomerPageDTO> maybeCustomer = this.customerService.findByName(nameCustomerToFind, page, 5);
			maybeCustomer.ifPresentOrElse(c -> {
				if (c.totalItems() > 1) {
					FacesUtils.addHeaderForResponse("customers", c.totalItems());
					LazyPopulateUtils.populate(lazyCustomers, c);
				} else {
					CustomerDTO cDTO = c.getClients().get(0);
					if (cDTO.getBlocked().equals("Sim")) {
						FacesUtils.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
						selectedCustomer = null;
						return;
					}
					selectedCustomer = cDTO;
				}
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				selectedCustomer = null;
			});
		} catch (ProcessingException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
		} catch (ClientErrorException e) {
			FacesUtils.error("customerDTO", resourceBundleService.getMessage("resposta_servidor"),
					e.getResponse().getEntity().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findCustomerByCode() {
		try {
			Optional<CustomerDTO> maybeCustomer = customerService.findByCodeAndStore(searchCustomerDTO);
			maybeCustomer.ifPresentOrElse(c -> {
				if (c.getBlocked().equals("Sim")) {
					FacesUtils.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
					selectedCustomer = null;
				} else {
					selectedCustomer = c;
				}
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				selectedCustomer = null;
			});
		} catch (ClientErrorException e) {
			FacesUtils.error(null, resourceBundleService.getMessage("resposta_servidor"),
					e.getResponse().getEntity().toString());
			selectedCustomer = null;
		} catch (SocketTimeoutException | SocketException | TimeoutException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
			selectedCustomer = null;
		}
	}

	public void confirmSelectedProduct() {
		this.selectedProducts.add(previewProduct);
		this.previewProduct = null;
	}

	public void findProductByCode() {
		try {
			Optional<ProductDTO> product = productService.findByCode(findProductByCodeForm.getCode(),
					budgetDTO.getCustomer().getCode(), budgetDTO.getCustomer().getStore());
			product.ifPresentOrElse(presentProduct -> {
				FacesUtils.addHeaderForResponse("product-found", true);
				previewProduct = new ProductDTO(presentProduct);

			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
				previewProduct = null;
			});
			findProductByCodeForm = new FindProductByCodeForm();
		} catch (SocketTimeoutException | TimeoutException | SocketException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			// e.printStackTrace();
		}

	}

	public void findProductByDescription(int page) {
		try {
			Optional<ProductPageDTO> maybeProduct = productService
					.findByDescription(findProductByDescriptionDTO.getDescription(), page, pageSizeForProducts);
			maybeProduct.ifPresentOrElse(p -> {
				LazyPopulateUtils.populate(lazyProducts, p);
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
				FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			});

		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			processingExceptionMessageHelper.displayMessage(e, null);
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		}
	}

	public void onPageProducts(PageEvent pageEvent) {
		findProductByDescription(pageEvent.getPage() + 1);
	}

	public void removeItem(ProductDTO item) {
		System.out.println("Remove item!");
		budgetService.removeItem(budgetDTO, item);
	}

	public void removeSelectedProduct(ProductDTO product) {
		new Thread(() -> itemsOnCartToPost.removeIf(i -> i.getCommercialCode().equals(product.getCommercialCode())))
				.start();
		this.selectedProducts.remove(product);
	}

	public void onProductSelected(ProductDTO productDTO) {
		selectedProducts.add(productDTO);
		// itemsOnCartToPost.add(new ProductBudgetFormDTO(productDTO));

	}

	private final void bulkInstantiationObjectsInBackGround() {
		new Thread(() -> {
			this.lazyProducts = new ProductLazyDataModel();
			this.lazyCustomers = new CustomerLazyDataModel();
			this.selectedProducts = new HashSet<>();
			itemsOnCartToPost = new HashSet<>();
			this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
			this.downloadStreamsForm = new DownloadStreamsForm();
			this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
			this.findProductByDescriptionDTO = new FindProductByDescriptionDTO();
			findProductByCodeForm = new FindProductByCodeForm();
			this.budgetImportXlsxForm = new BudgetXlsxPreviewForm((short) 1, (short) 1, (short) 2, (short) 0, (short) 2,
					(short) 1, (short) 2, (short) 0);
			this.budgetXlsxPreview = new BudgetXlsxPreviewedDTO();
			this.budgetDTO = new BudgetDTO();
		}).start();
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

	public FindProductByDescriptionDTO getFindProductByDescriptionDTO() {
		return findProductByDescriptionDTO;
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

	public EstimatedItemDTO getSelectedItemToViewStock() {
		return selectedItemToViewStock;
	}

	public void setSelectedItemToViewStock(EstimatedItemDTO selectedItemToViewStock) {
		this.selectedItemToViewStock = selectedItemToViewStock;
	}

	public ClientErrorExceptionController getResponseController() {
		return responseController;
	}

	public FindProductByCodeForm getFindProductByCodeForm() {
		return findProductByCodeForm;
	}

	public ProductDTO getPreviewProduct() {
		return previewProduct;
	}

	public byte[] getImageToSeeOnDlg() {
		return imageToSeeOnDlg;
	}

	public void setImageToSeeOnDlg(byte[] imageToSeeOnDlg) {
		this.imageToSeeOnDlg = imageToSeeOnDlg;
	}

	public BudgetXlsxPreviewForm getBudgetImportXlsxForm() {
		return budgetImportXlsxForm;
	}

	public BudgetXlsxPreviewedDTO getBudgetXlsxPreview() {
		return budgetXlsxPreview;
	}

	public BudgetDTO getBudgetDTO() {
		return budgetDTO;
	}
}
