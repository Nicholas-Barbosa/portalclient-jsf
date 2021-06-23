package com.portal.java.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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
import com.portal.java.dto.Customer;
import com.portal.java.dto.CustomerOnOrder;
import com.portal.java.dto.CustomerOnOrder.CustomerType;
import com.portal.java.dto.CustomerPageDTO;
import com.portal.java.dto.DiscountView;
import com.portal.java.dto.DownloadStreamsForm;
import com.portal.java.dto.EstimatedItemDTO;
import com.portal.java.dto.FindProductByCodeForm;
import com.portal.java.dto.FindProductByDescriptionDTO;
import com.portal.java.dto.Item;
import com.portal.java.dto.ItemLineDiscount;
import com.portal.java.dto.ItemPrice;
import com.portal.java.dto.Product;
import com.portal.java.dto.Product.ProductPrice;
import com.portal.java.dto.ProductBudgetForm;
import com.portal.java.dto.ProductPageDTO;
import com.portal.java.dto.ProspectCustomerForm;
import com.portal.java.dto.ProspectCustomerOnOrder;
import com.portal.java.dto.ProspectCustomerOnOrder.SellerType;
import com.portal.java.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.java.jasper.service.BudgetReport;
import com.portal.java.service.BudgetService;
import com.portal.java.service.CustomerService;
import com.portal.java.service.ItemService;
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

	private final ItemService itemService;

	private LazyDataModel<Product> lazyProducts;

	private LazyDataModel<Customer> lazyCustomers;

	private String h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private Set<ProductBudgetForm> itemsOnCartToPost;

	private Set<Product> selectedProducts;

	private FindProductByDescriptionDTO findProductByDescriptionDTO;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	private Customer selectedCustomer;

	private BudgetEstimatedDTO budgetEstimateDTO;

	private DownloadStreamsForm downloadStreamsForm;

	private EstimatedItemDTO selectedItemToViewStock;

	private FindProductByCodeForm findProductByCodeForm;

	private Item previewItem;

	private byte[] imageToSeeOnDlg;

	private BudgetXlsxPreviewForm budgetImportXlsxForm;

	private BudgetXlsxPreviewedDTO budgetXlsxPreview;

	private BudgetDTO budgetDTO;

	private BigDecimal itemDiscountToView;

	private Set<String> itemLines;

	private ItemLineDiscount itemLineDiscount;

	private ProspectCustomerForm prospectCustomerForm;

	private BigDecimal globalDiscount;

	private DiscountView discView;

	public BudgetController() {
		this(null, null, null, null, null, null, null, null);
	}

	@Inject
	public BudgetController(ResourceBundleService resourceBundleService, CustomerService customerService,
			BudgetService budgetService, BudgetReport budgetReport, ClientErrorExceptionController responseController,
			ResourceExceptionMessageHelper processingExceptionMessageHelper, ProductService productService,
			ItemService itemService) {
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
		this.itemService = itemService;
	}

	public void setProspectCustomer() {
		CustomerOnOrder customer = new ProspectCustomerOnOrder();
		customer.setType(CustomerType.PROSPECT);
		((ProspectCustomerOnOrder) customer).setSellerType(SellerType.valueOf(prospectCustomerForm.getSellerType()));
		((ProspectCustomerOnOrder) customer).setState(prospectCustomerForm.getStateAcronym());
		budgetService.setCustomer(budgetDTO, customer);
	}

	public void applyGlobalDiscount() {
		budgetService.setDiscount(budgetDTO, globalDiscount);
	}

	public void applyLineDiscount() {
		budgetDTO.getItems().parallelStream().filter(i -> i.line().equals(itemLineDiscount.getLine()))
				.peek(i -> i.setLineDiscount(itemLineDiscount.getDiscount()))
				.forEach(itemService::calculateDueDiscount);
		budgetService.calculateTotals(budgetDTO);
	}

	public void loadCurrentItemLines() {
		this.itemLines = budgetDTO.getItems().parallelStream().map(Item::line).collect(Collectors.toSet());
	}

	public void addPreviewItemToBudget() {
		budgetService.addItem(budgetDTO, previewItem);
		previewItem = null;
	}

	public void changeItemDiscount() {
		itemService.calculateDueDiscount(previewItem);
	}

	public void changItemQuantity() {
		itemService.calculateDueQuantity(previewItem, false);
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

	public void onItemRowEdit(RowEditEvent<Item> event) {
		budgetService.calculateTotals(budgetDTO, event.getObject(), false, true);

	}

	public void removeItem(Item item) {
		budgetService.removeItem(budgetDTO, item);
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
		this.productService.loadImage(previewItem.getProduct());
	}

	public void loadImageForProduct(Product product) {
		this.productService.loadImage(product);
	}

	public void newBudgetObject() throws InterruptedException {
		this.budgetDTO = new BudgetDTO();
	}

	public void exportReport() {
		try {
			BudgetJasperReportDTO jasperDTO = new BudgetJasperReportDTO(budgetEstimateDTO.getLiquidValue(),
					budgetEstimateDTO.getGrossValue(), budgetEstimateDTO.getStTotal(),
					new CustomerJasperReportDTO(selectedCustomer.getName(), selectedCustomer.getCity(),
							selectedCustomer.getAddress(), selectedCustomer.getState(), selectedCustomer.getCnpj()),
					budgetEstimateDTO.getItems());
			byte[] btes = budgetReport.export(jasperDTO, downloadStreamsForm.getContentType());
			FacesUtils.prepareResponseHeadersResponseForDownloadOfStreams(downloadStreamsForm.getName(), btes,
					downloadStreamsForm.getContentType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectCustomer(SelectEvent<Customer> event) {
		if (event.getObject().getBlocked().equals("Sim")) {
			FacesUtils.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
			FacesUtils.addHeaderForResponse("customer-isBlocked", true);
			return;
		}
		budgetService.setCustomer(budgetDTO, new CustomerOnOrder(event.getObject(), CustomerType.NORMAL));
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
					Customer cDTO = c.getClients().get(0);
					if (cDTO.getBlocked().equals("Sim")) {
						FacesUtils.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
						selectedCustomer = null;
						return;
					}
					selectedCustomer = cDTO;
				}
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				FacesUtils.addHeaderForResponse("customers-found", false);
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
			Optional<Customer> maybeCustomer = customerService.findByCodeAndStore(searchCustomerDTO);
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

	public void findProductByCode() {
		try {
			Optional<Product> product = null;
			switch (budgetDTO.getCustomerOnOrder().getType()) {
			case NORMAL:
				product = productService.findByCode(findProductByCodeForm.getCode(),
						budgetDTO.getCustomerOnOrder().getCustomer().getCode(),
						budgetDTO.getCustomerOnOrder().getCustomer().getStore());
				break;
			default:
				ProspectCustomerOnOrder customer = (ProspectCustomerOnOrder) budgetDTO.getCustomerOnOrder();
				product = productService.findByCodeForProspect(findProductByCodeForm.getCode(),
						customer.getCustomer().getState(), customer.getSellerType().getType());
				break;
			}
			this.checkPossibleProduct(product);
			findProductByCodeForm = new FindProductByCodeForm();
		} catch (SocketTimeoutException | TimeoutException | SocketException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			// e.printStackTrace();
		} catch (NullPointerException e) {
			FacesUtils.error(null, "Cliente não selecionado", "Selecione o cliente");
		}

	}

	private void checkPossibleProduct(Optional<Product> product) {
		product.ifPresentOrElse(presentProduct -> {
			FacesUtils.addHeaderForResponse("product-found", true);
			ProductPrice productPrice = presentProduct.getPrice();
			previewItem = new Item(BigDecimal.ZERO, BigDecimal.ZERO, presentProduct, 1,
					new ItemPrice(productPrice.getUnitStValue(), productPrice.getUnitValue(),
							productPrice.getUnitGrossValue(), productPrice.getUnitStValue(),
							productPrice.getUnitValue(), productPrice.getUnitGrossValue()));

		}, () -> {
			FacesUtils.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
			FacesUtils.addHeaderForResponse("product-found", false);
			previewItem = null;
		});

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

	public void removeSelectedProduct(Product product) {
		new Thread(() -> itemsOnCartToPost.removeIf(i -> i.getCommercialCode().equals(product.getCommercialCode())))
				.start();
		this.selectedProducts.remove(product);
	}

	public void onProductSelected(Product productDTO) {
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
			this.itemLines = new HashSet<>();
			this.itemLineDiscount = new ItemLineDiscount();
			this.prospectCustomerForm = new ProspectCustomerForm();
			this.discView = new DiscountView();
		}).start();
	}

	public LazyDataModel<Product> getLazyProducts() {
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

	public Set<Product> getSelectedProducts() {
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

	public LazyDataModel<Customer> getLazyCustomers() {
		return lazyCustomers;
	}

	public Customer getSelectedCustomer() {
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

	public Item getPreviewItem() {
		return previewItem;
	}

	public void changePreviewItem(Item previewItem) {
		this.previewItem = previewItem;
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

	public BigDecimal getItemDiscountToView() {
		return itemDiscountToView;
	}

	public void setItemDiscountToView(BigDecimal itemDiscountToView) {
		this.itemDiscountToView = itemDiscountToView;
	}

	public Set<String> getItemLines() {
		return Collections.unmodifiableSet(itemLines);
	}

	public ItemLineDiscount getItemLineDiscount() {
		return itemLineDiscount;
	}

	public ProspectCustomerForm getProspectCustomerForm() {
		return prospectCustomerForm;
	}

	public BigDecimal getGlobalDiscount() {
		return globalDiscount;
	}

	public void setGlobalDiscount(BigDecimal globalDiscount) {
		this.globalDiscount = globalDiscount;
	}

	public DiscountView getDiscView() {
		return discView;
	}

}
