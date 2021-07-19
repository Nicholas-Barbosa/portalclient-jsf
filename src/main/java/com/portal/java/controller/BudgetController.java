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

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.java.dto.BudgetXlsxPreviewForm;
import com.portal.java.dto.BudgetXlsxPreviewedDTO;
import com.portal.java.dto.Customer;
import com.portal.java.dto.CustomerAddress;
import com.portal.java.dto.CustomerContact;
import com.portal.java.dto.CustomerOnOrder;
import com.portal.java.dto.CustomerOnOrder.CustomerType;
import com.portal.java.dto.CustomerPageDTO;
import com.portal.java.dto.CustomerPurchaseInfo;
import com.portal.java.dto.DiscountView;
import com.portal.java.dto.DownloadStreamsForm;
import com.portal.java.dto.FindProductByCodeForm;
import com.portal.java.dto.FindProductByDescriptionDTO;
import com.portal.java.dto.Item;
import com.portal.java.dto.ItemLineDiscountForm;
import com.portal.java.dto.ItemValues;
import com.portal.java.dto.Order;
import com.portal.java.dto.Product;
import com.portal.java.dto.ProductPageDTO;
import com.portal.java.dto.ProspectCustomerForm;
import com.portal.java.dto.ProspectCustomerOnOrder;
import com.portal.java.dto.ProspectCustomerOnOrder.SellerType;
import com.portal.java.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.java.exception.CustomerNotAllowed;
import com.portal.java.exception.ItemQuantityNotAllowed;
import com.portal.java.export.OrderExport;
import com.portal.java.service.BudgetService;
import com.portal.java.service.CustomerService;
import com.portal.java.service.ItemService;
import com.portal.java.service.ProductService;
import com.portal.java.service.ResourceBundleService;
import com.portal.java.service.ZipCodeService;
import com.portal.java.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.java.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.java.ui.lazy.datamodel.LazyOperations;
import com.portal.java.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.java.ui.lazy.datamodel.ProductLazyDataModel;
import com.portal.java.util.jsf.ExternalServerExceptionFacesHelper;
import com.portal.java.util.jsf.FacesUtils;

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

	private final OrderExport orderExporter;

	private final ClientErrorExceptionController responseController;

	private final ExternalServerExceptionFacesHelper processingExceptionMessageHelper;

	private final ProductService productService;

	private final ItemService itemService;

	private final ZipCodeService cepCervice;

	private LazyDataModelBase<Product> lazyProducts;

	private LazyDataModelBase<Customer> lazyCustomers;

	private String h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private Set<Product> selectedProducts;

	private FindProductByDescriptionDTO findProductByDescriptionDTO;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	private DownloadStreamsForm downloadStreamsForm;

	private FindProductByCodeForm findProductByCodeForm;

	private Item previewItem;

	private byte[] imageToSeeOnDlg;

	private BudgetXlsxPreviewForm budgetImportXlsxForm;

	private BudgetXlsxPreviewedDTO budgetXlsxPreview;

	private Order budgetDTO;

	private BigDecimal itemDiscountToView;

	private Set<String> itemLines;

	private ItemLineDiscountForm itemLineDiscount;

	private ProspectCustomerForm prospectCustomerForm;

	private BigDecimal globalDiscount;

	private DiscountView discView;

	private int previewItemQuantity;

	private int onRowItemQuantity;

	@NotEmpty
	@Size(max = 8, min = 8)
	private String cepToSearch;

	public BudgetController() {
		this(null, null, null, null, null, null, null, null, null);
	}

	@Inject
	public BudgetController(ResourceBundleService resourceBundleService, CustomerService customerService,
			BudgetService budgetService, OrderExport orderExporter, ClientErrorExceptionController responseController,
			ExternalServerExceptionFacesHelper processingExceptionMessageHelper, ProductService productService,
			ItemService itemService, ZipCodeService cep) {
		super();
		bulkInstantiationObjectsInBackGround();
		this.resourceBundleService = resourceBundleService;
		this.customerService = customerService;
		this.budgetService = budgetService;
		this.orderExporter = orderExporter;
		this.responseController = responseController;
		this.processingExceptionMessageHelper = processingExceptionMessageHelper;
		this.productService = productService;
		this.imageToSeeOnDlg = new byte[0];
		this.itemService = itemService;
		this.cepCervice = cep;
	}

	public void showCustomerDetail(Customer customer) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customer_to_detail", customer);
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("position", "center");
		options.put("contentWidth", "60vw");
		options.put("contentHeight", "45vh");
		options.put("responsive", "true");
		PrimeFaces.current().dialog().openDynamic("customerDetail", options, null);
	}

	public void findCep() {
		try {
			cepCervice.find(cepToSearch).ifPresentOrElse(cep -> {
				this.prospectCustomerForm.setAddress(cep.getAddress());
				this.prospectCustomerForm.setStateAcronym(cep.getState());
				this.prospectCustomerForm.setDistrict(cep.getDistrict());
				this.prospectCustomerForm.setCity(cep.getCity());
				FacesUtils.info(null, "CEP encontrado!", cep.getAddress() + ", " + cep.getDistrict() + " - "
						+ cep.getCity() + " lat: " + cep.getLat() + " lng: " + cep.getLng());
			}, () -> {
				FacesUtils.error(null, "CEP não encontrado", "Digite o endereço manualmente");
			});
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			FacesUtils.fatal(null, "Não foi possível consultar o cep no IBGE", "Serviço fora do ar.");
		}
	}

	public void saveMessageOrder() {
		if (budgetDTO != null && budgetDTO.getCustomerOnOrder().getCustomer() != null) {
			FacesUtils.info(null, "Mensagem salva!", null);
			return;
		}
		FacesUtils.warn(null, "Cliente não configurado", "Escolha um cliente antes de digitar uma mensagem");
	}

	public void setProspectCustomer() {
		if (prospectCustomerForm.getCity() != null && !prospectCustomerForm.getCity().isEmpty()) {
			ProspectCustomerOnOrder prospectCustomer = new ProspectCustomerOnOrder();
			prospectCustomer.setType(CustomerType.PROSPECT);
			prospectCustomer.setSellerType(SellerType.valueOf(prospectCustomerForm.getSellerType()));

			CustomerAddress customerAddress = new CustomerAddress(prospectCustomerForm.getAddress(),
					prospectCustomerForm.getDistrict(), prospectCustomerForm.getCity(), cepToSearch,
					prospectCustomerForm.getStateAcronym());
			CustomerPurchaseInfo purshaseInfo = new CustomerPurchaseInfo(0f, 0f, 0f, null, null,
					prospectCustomerForm.getPaymentTerms(), null, null);
			CustomerContact contact = new CustomerContact(null, null);
			Customer originCustomer = new Customer(null, null, prospectCustomerForm.getCnpj(), null,
					prospectCustomerForm.getName(), prospectCustomerForm.getName(), customerAddress, purshaseInfo,
					contact);
			prospectCustomer.setCustomer(originCustomer);
			budgetService.setCustomer(budgetDTO, prospectCustomer);
			PrimeFaces.current().executeScript("PF('dlgSearchCustomer').hide();");
			return;
		}
		FacesUtils.fatal(null, "Digite as informações relevantes ao endereço!", null);
		PrimeFaces.current().ajax().update("growl");
	}

	public void applyGlobalDiscount() {
		try {
			budgetService.setDiscount(budgetDTO, globalDiscount);
		} catch (CustomerNotAllowed e) {
			FacesUtils.fatal(null, "Cliente não autorizado", null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void applyLineDiscount() {
		itemService.applyLineDiscount(budgetDTO.getItems(), itemLineDiscount);
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

	}

	public void changItemQuantity() {
		calculateItemQuantity(previewItem, previewItemQuantity);
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		calculateItemQuantity(event.getObject(), onRowItemQuantity);
		budgetService.calculateTotals(budgetDTO);
		onRowItemQuantity = 1;
	}

	private void calculateItemQuantity(Item item, int quantity) {
		try {
			itemService.calculateDueQuantity(item, quantity);
		} catch (ItemQuantityNotAllowed e) {
			e.printStackTrace();
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void previewBudgetXlsxContent() {
		budgetXlsxPreview = budgetService.previewXlsxContent(budgetImportXlsxForm);
	}

	public void handleFileUpload(FileUploadEvent event) {
		budgetImportXlsxForm.setXlsxStreams(event.getFile().getContent());
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
		this.budgetDTO = new Order();
	}

	public void exportOrder() {
		if (budgetDTO == null || budgetDTO.getItems().size() == 0) {
			FacesUtils.error(null, "Objeto de pedido não está pronto",
					"Entre com as informações necessárias para criar um pedido/orçamento.");
			return;
		}
		try {
			byte[] btes = orderExporter.export(budgetDTO, downloadStreamsForm.getContentType());
			FacesUtils.prepareResponseForDownloadOfStreams(downloadStreamsForm.getName(), btes,
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
						return;
					}
				}
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				FacesUtils.addHeaderForResponse("customers-found", false);
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
			this.getOptionalProduct(product);
			findProductByCodeForm = new FindProductByCodeForm();
		} catch (SocketTimeoutException | TimeoutException | SocketException p) {
			processingExceptionMessageHelper.displayMessage(p, null);
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			// e.printStackTrace();
		} catch (NullPointerException e) {
			FacesUtils.error(null, "Cliente não selecionado", "Selecione o cliente");
		}

	}

	private void getOptionalProduct(Optional<Product> product) {
		product.ifPresentOrElse(presentProduct -> {
			FacesUtils.addHeaderForResponse("product-found", true);
			com.portal.java.dto.ProductPrice productPrice = presentProduct.getPrice();
			previewItem = new Item(BigDecimal.ZERO, BigDecimal.ZERO, presentProduct,
					new ItemValues(1, BigDecimal.ZERO, BigDecimal.ZERO, productPrice.getUnitStValue(),
							productPrice.getUnitValue(), productPrice.getUnitGrossValue(),
							productPrice.getUnitStValue(), productPrice.getUnitValue(),
							productPrice.getUnitGrossValue()));
			previewItemQuantity = presentProduct.getMultiple();
			PrimeFaces.current().executeScript("$('#footer').show();");
		}, () -> {
			FacesUtils.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
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

//	public void removeSelectedProduct(Product product) {
//		new Thread(() -> itemsOnCartToPost.removeIf(i -> i.getCommercialCode().equals(product.getCommercialCode())))
//				.start();
//		this.selectedProducts.remove(product);
//	}

	public void onProductSelected(Product productDTO) {
		selectedProducts.add(productDTO);
		// itemsOnCartToPost.add(new ProductBudgetFormDTO(productDTO));

	}

	private final void bulkInstantiationObjectsInBackGround() {
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.selectedProducts = new HashSet<>();
		this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
		this.downloadStreamsForm = new DownloadStreamsForm();
		this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
		this.findProductByDescriptionDTO = new FindProductByDescriptionDTO();
		findProductByCodeForm = new FindProductByCodeForm();
		this.budgetImportXlsxForm = new BudgetXlsxPreviewForm((short) 1, (short) 1, (short) 2, (short) 0, (short) 2,
				(short) 1, (short) 2, (short) 0);
		this.budgetXlsxPreview = new BudgetXlsxPreviewedDTO();
		this.budgetDTO = new Order();
		this.itemLines = new HashSet<>();
		this.itemLineDiscount = new ItemLineDiscountForm();
		this.prospectCustomerForm = new ProspectCustomerForm();
		this.discView = new DiscountView();
	}

	public LazyDataModelBase<Product> getLazyProducts() {
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

	public LazyDataModelBase<Customer> getLazyCustomers() {
		return lazyCustomers;
	}

	public DownloadStreamsForm getDownloadStreamsForm() {
		return downloadStreamsForm;
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

	public Order getBudgetDTO() {
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

	public ItemLineDiscountForm getItemLineDiscount() {
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

	public int getPreviewItemQuantity() {
		return previewItemQuantity;
	}

	public void setPreviewItemQuantity(int previewItemQuantity) {
		this.previewItemQuantity = previewItemQuantity;
	}

	public int getOnRowItemQuantity() {
		return onRowItemQuantity;
	}

	public void setOnRowItemQuantity(int onRowItemQuantity) {
		this.onRowItemQuantity = onRowItemQuantity;
	}

	public String getCepToSearch() {
		return cepToSearch;
	}

	public void setCepToSearch(String cepToSearch) {
		this.cepToSearch = cepToSearch;
	}

}
