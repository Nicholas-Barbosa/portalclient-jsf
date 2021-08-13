package com.portal.client.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetXlsxPreviewedDTO;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerAddress;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.CustomerPurchaseInfo;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.DiscountView;
import com.portal.client.dto.DownloadStreamsForm;
import com.portal.client.dto.FindProductByCodeForm;
import com.portal.client.dto.FindProductByDescriptionDTO;
import com.portal.client.dto.ItemBudget;
import com.portal.client.dto.ItemBudgetBuilder;
import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.Product;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.dto.ProspectCustomerForm;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.dto.ProspectCustomerOnOrder.SellerType;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.export.OrderExport;
import com.portal.client.service.BudgetCommonBehaviorHelper;
import com.portal.client.service.CustomerService;
import com.portal.client.service.ItemService;
import com.portal.client.service.ResourceBundleService;
import com.portal.client.service.ZipCodeService;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.service.crud.ProductService;
import com.portal.client.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.client.ui.lazy.datamodel.LazyOperations;
import com.portal.client.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.client.ui.lazy.datamodel.ProductLazyDataModel;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ServerApiExceptionFacesMessageHelper;
import com.portal.client.util.jsf.ServerEndpointErrorUtils;

@Named
@ViewScoped
public class NewBudgetOrderController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537974193888491899L;

	private final ResourceBundleService resourceBundleService;

	private final CustomerService customerService;

	private final BudgetCrudService budgetService;

	private final OrderExport orderExporter;

	private final ClientErrorExceptionController responseController;

	private final ServerApiExceptionFacesMessageHelper serverExceptionFacesMessageHelper;

	private final ProductService productService;

	private final ItemService itemService;

	private final ZipCodeService cepCervice;

	private final BudgetCommonBehaviorHelper budgetBehaviorHelper;

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

	private ItemBudget previewItem;

	private byte[] imageToSeeOnDlg;

	private ItemXlsxFileLayout budgetImportXlsxForm;

	private BudgetXlsxPreviewedDTO budgetXlsxPreview;

	private BaseBudget budget;

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

	private CustomerRepresentativeOrderForm customerRepresentativeOrderForm;

	private String paramBudgetID;

	private boolean isOrder;

	public NewBudgetOrderController() {
		this(null, null, null, null, null, null, null, null, null, null);
	}

	@Inject
	public NewBudgetOrderController(ResourceBundleService resourceBundleService, CustomerService customerService,
			BudgetCrudService budgetService, OrderExport orderExporter,
			ClientErrorExceptionController responseController,
			ServerApiExceptionFacesMessageHelper processingExceptionMessageHelper, ProductService productService,
			ItemService itemService, ZipCodeService cep, BudgetCommonBehaviorHelper budgetRequestService) {
		super();
		bulkInstantiationObjectsInBackGround();
		this.resourceBundleService = resourceBundleService;
		this.customerService = customerService;
		this.budgetService = budgetService;
		this.orderExporter = orderExporter;
		this.responseController = responseController;
		this.serverExceptionFacesMessageHelper = processingExceptionMessageHelper;
		this.productService = productService;
		this.imageToSeeOnDlg = new byte[0];
		this.itemService = itemService;
		this.cepCervice = cep;
		this.budgetBehaviorHelper = budgetRequestService;
	}

	public void openItemImportView() {
		if (budget.getCustomerOnOrder() != null) {
			FacesUtils.openViewOnDialog(
					Map.of("modal", true, "responsive", true, "contentWidth", "98vw", "contentHeight", "80vh"),
					"itemImport", Map.of("customerCode", List.of(budget.getCustomerOnOrder().getCode()),
							"customerStore", List.of(budget.getCustomerOnOrder().getStore()), "onDialog",
							List.of("true")));
			return;
		}
		FacesUtils.error(null, "Cliente não selecionado", null, "growl");
	}

	public void handleItemImportReturn(SelectEvent<BaseBudget> event) {
		this.budgetBehaviorHelper.merge(budget, event.getObject());
		FacesUtils.ajaxUpdate("formItems:dtItems", "budgetTotals");
	}

	public void saveBudgetOrOrder() {
		try {
			if (!isOrder) {
				budgetService.save(budget, customerRepresentativeOrderForm);
				PrimeFaces.current().executeScript("PF('successSavedBudgetOrOrder').show();");
				FacesUtils.ajaxUpdate("successSavedBudgetOrOrder");
			}
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientErrorException e) {
			ServerEndpointErrorUtils.openEndpointErrorOnDialog(e.getResponse());
		}
	}

	public void checkBudgetRequestObj() {
		try {
			budgetService.checkBudgetState(budget);
			PrimeFaces.current().executeScript("PF('saveBudgetOrder').show()");
		} catch (IllegalArgumentException e) {
			FacesUtils.error(null, "Não é possível efetivar o orçamento neste momento.",
					"Objeto incompleto. Entre com os dados necessário.", "growl");
		} catch (CustomerNotAllowed e) {
			FacesUtils.error(null, "Não é possível efetivar o orçamento.",
					"Esta operação é destinada a clientes normais(não prospects)", "growl");
		}

	}

	public void showCustomerDetail(Customer customer) {
		if (customer != null) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customer_to_detail", customer);
			Map<String, Object> options = new HashMap<>();
			options.put("modal", true);
			options.put("draggable", true);
			options.put("position", "center");
			options.put("contentWidth", "60vw");
			options.put("contentHeight", "45vh");
			options.put("responsive", "true");
			PrimeFaces.current().dialog().openDynamic("customerDetail", options, null);
			return;
		}
		FacesUtils.error(null, "Cliente não selecionado", "Não foi selecionado nenhum cliente neste ínterim.", "growl");
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
		FacesUtils.info(null, "Mensagem salva!", null);
	}

	public void setProspectCustomer() {
		if (prospectCustomerForm.getCity() != null && !prospectCustomerForm.getCity().isEmpty()) {

			CustomerAddress customerAddress = new CustomerAddress(prospectCustomerForm.getAddress(),
					prospectCustomerForm.getDistrict(), prospectCustomerForm.getCity(), cepToSearch,
					prospectCustomerForm.getStateAcronym());
			CustomerPurchaseInfo purshaseInfo = new CustomerPurchaseInfo(0f, 0f, 0f, null, null,
					prospectCustomerForm.getPaymentTerms(), null, null);
			CustomerOnOrder customer = new ProspectCustomerOnOrder(null, null, prospectCustomerForm.getCnpj(), null,
					prospectCustomerForm.getName(), prospectCustomerForm.getName(), customerAddress, purshaseInfo, null,
					SellerType.valueOf(prospectCustomerForm.getSellerType()));
			budgetBehaviorHelper.setCustomer(budget, customer);
			PrimeFaces.current().executeScript("PF('dlgSearchCustomer').hide();");
			return;
		}
		FacesUtils.fatal(null, "Digite as informações relevantes ao endereço!", null);
		PrimeFaces.current().ajax().update("growl");
	}

	public void applyGlobalDiscount() {
		try {
			budgetBehaviorHelper.setDiscount(budget, globalDiscount);
		} catch (CustomerNotAllowed e) {
			FacesUtils.fatal(null, "Cliente não autorizado", null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void applyLineDiscount() {
		itemService.applyLineDiscount(budget.getItems(), itemLineDiscount);
		budgetBehaviorHelper.calculateTotals(budget);
	}

	public void loadCurrentItemLines() {
		this.itemLines = budget.getItems().parallelStream().map(ItemBudget::line).collect(Collectors.toSet());
	}

	public void addPreviewItemToBudget() {
		budgetBehaviorHelper.addItem(budget, previewItem);
		previewItem = null;
	}

	public void changeItemDiscount() {

	}

	public void changItemQuantity() {
		calculateItemQuantity(previewItem, previewItemQuantity);
	}

	public void onRowItemEdit(RowEditEvent<ItemBudget> event) {
		calculateItemQuantity(event.getObject(), onRowItemQuantity);
		budgetBehaviorHelper.calculateTotals(budget);
		onRowItemQuantity = 1;
	}

	private void calculateItemQuantity(ItemBudget item, int quantity) {
		try {
			itemService.calculateDueQuantity(item, quantity);
		} catch (ItemQuantityNotAllowed e) {
			e.printStackTrace();
			FacesUtils.error(null, e.getMessage(), null);
			PrimeFaces.current().ajax().update("growl");
		}
	}

	public void removeItem(ItemBudget item) {
		budgetBehaviorHelper.removeItem(budget, item);
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

	public void newBudgetObject() {
		this.budget = new BaseBudget();
	}

	public void exportOrder() {
		if (budget == null || budget.getItems().size() == 0) {
			FacesUtils.error(null, "Objeto de pedido não está pronto",
					"Entre com as informações necessárias para criar um pedido/orçamento.");
			return;
		}
		try {
			byte[] btes = orderExporter.export(budget, downloadStreamsForm.getContentType());
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
		budgetBehaviorHelper.setCustomer(budget, new CustomerOnOrder(event.getObject()));
		LazyOperations<?> lazy = (LazyOperations<?>) lazyCustomers;
		lazy.turnCollectionElegibleToGB();
	}

	public void onPageCustomers(PageEvent pageEvent) {
		findCustomerByName(pageEvent.getPage() + 1, false);
	}

	public void findCustomerByName(int page, boolean updateFormSelectCustomer) {
		try {
			Optional<CustomerPageDTO> maybeCustomer = this.customerService.findByName(nameCustomerToFind, page, 5);
			maybeCustomer.ifPresentOrElse(c -> {
				if (c.totalItems() > 1) {
					FacesUtils.addHeaderForResponse("customers", c.totalItems());
					LazyPopulateUtils.populate(lazyCustomers, c);
					if (updateFormSelectCustomer)
						FacesUtils.ajaxUpdate("fomrSelectCustomer");
				} else {
					Customer cDTO = c.getClients().get(0);
					if (cDTO.getBlocked().equals("Sim")) {
						FacesUtils.error(null, resourceBundleService.getMessage("cliente_bloqueado"), null);
						return;
					}
					budgetBehaviorHelper.setCustomer(budget, new CustomerOnOrder(cDTO));
					FacesUtils.ajaxUpdate(":customerForm", "budgetToolsForm:btnViewCDetail");
					FacesUtils.addHeaderForResponse("customers-found", true);
				}
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("cliente_nao_encontrado"), null);
				FacesUtils.addHeaderForResponse("customers-found", false);
			});
		} catch (SocketTimeoutException | SocketException | TimeoutException p) {
			serverExceptionFacesMessageHelper.displayMessage(p, null);
		}
	}

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresent(p -> {
			budgetBehaviorHelper.addItem(budget, ItemBudgetBuilder.product(p));
			FacesUtils.ajaxUpdate("formItems:dtItems", "budgetTotals");
		});

	}

	public void openSearchProduct() {
		CustomerOnOrder customer = budget.getCustomerOnOrder();
		Map<String, List<String>> queryParams = new HashMap<>();
		queryParams.put("customerType", List.of(customer.getType().name()));
		if (customer instanceof ProspectCustomerOnOrder) {
			ProspectCustomerOnOrder prospCustomer = (ProspectCustomerOnOrder) customer;
			queryParams.put("customerPropState", List.of(prospCustomer.getState()));
			queryParams.put("customerPropSelType", List.of(prospCustomer.getSellerType().getType()));
		} else {
			queryParams.put("customerCode", List.of(customer.getCode()));
			queryParams.put("customerStore", List.of(customer.getStore()));
		}

		FacesUtils.openViewOnDialog(
				Map.of("modal", true, "responsive", true, "contentWidth", "30vw", "contentHeight", "65vh"),
				"searchProduct", queryParams);
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
			serverExceptionFacesMessageHelper.displayMessage(e, null);
			FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			e.printStackTrace();
		}
	}

	public void onPageProducts(PageEvent pageEvent) {
		findProductByDescription(pageEvent.getPage() + 1);
	}

	public void onProductSelected(Product productDTO) {
		selectedProducts.add(productDTO);

	}

	private final void bulkInstantiationObjectsInBackGround() {
		this.lazyProducts = new ProductLazyDataModel();
		this.lazyCustomers = new CustomerLazyDataModel();
		this.selectedProducts = new HashSet<>();
		this.searchCustomerDTO = new SearchCustomerByCodeAndStoreDTO();
		this.downloadStreamsForm = new DownloadStreamsForm();
		this.findProductByDescriptionDTO = new FindProductByDescriptionDTO();
		findProductByCodeForm = new FindProductByCodeForm();
		this.budgetXlsxPreview = new BudgetXlsxPreviewedDTO();
		this.budget = new BaseBudget();
		this.itemLines = new HashSet<>();
		this.itemLineDiscount = new ItemLineDiscountForm();
		this.prospectCustomerForm = new ProspectCustomerForm();
		this.discView = new DiscountView();
		this.customerRepresentativeOrderForm = new CustomerRepresentativeOrderForm();
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

	public ItemBudget getPreviewItem() {
		return previewItem;
	}

	public void changePreviewItem(ItemBudget previewItem) {
		this.previewItem = previewItem;
	}

	public byte[] getImageToSeeOnDlg() {
		return imageToSeeOnDlg;
	}

	public void setImageToSeeOnDlg(byte[] imageToSeeOnDlg) {
		this.imageToSeeOnDlg = imageToSeeOnDlg;
	}

	public ItemXlsxFileLayout getBudgetImportXlsxForm() {
		return budgetImportXlsxForm;
	}

	public BudgetXlsxPreviewedDTO getBudgetXlsxPreview() {
		return budgetXlsxPreview;
	}

	public BaseBudget getBudget() {
		return budget;
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

	public CustomerRepresentativeOrderForm getCustomerRepresentativeOrderForm() {
		return customerRepresentativeOrderForm;
	}

	public String getParamBudgetID() {
		return paramBudgetID;
	}

	public void setParamBudgetID(String paramBudgetID) {
		this.paramBudgetID = paramBudgetID;
	}

	public boolean isOrder() {
		return isOrder;
	}

	public void setOrder(boolean isOrder) {
		this.isOrder = isOrder;
	}

}
