package com.portal.client.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.BudgetXlsxPreviewedDTO;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerAddress;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.CustomerPurchaseInfo;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.DiscountView;
import com.portal.client.dto.FindProductByDescriptionDTO;
import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.dto.ItemXlsxFileLayout;
import com.portal.client.dto.ProductPageDTO;
import com.portal.client.dto.ProspectCustomerForm;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.dto.ProspectCustomerOnOrder.SellerType;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.service.BudgetCommonBehaviorHelper;
import com.portal.client.service.ItemService;
import com.portal.client.service.ResourceBundleService;
import com.portal.client.service.ZipCodeService;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.service.crud.ProductService;
import com.portal.client.ui.lazy.datamodel.CustomerLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.ui.lazy.datamodel.ProductLazyDataModel;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;
import com.portal.client.util.jsf.ServerEndpointErrorUtils;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;
import com.portal.client.vo.Product;

@Named
@ViewScoped
public class NewBudgetOrderController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537974193888491899L;

	private final ResourceBundleService resourceBundleService;

	private final BudgetCrudService budgetService;

	private final ClientErrorExceptionController responseController;

	private final ProcessingExceptionFacesMessageHelper prossExceptionMessageShower;

	private final ProductService productService;

	private final ItemService itemService;

	private final ZipCodeService cepCervice;

	private final BudgetCommonBehaviorHelper budgetBehaviorHelper;

	private LazyBehaviorDataModel<Product> lazyProducts;

	private LazyBehaviorDataModel<Customer> lazyCustomers;

	private String h5DivLoadCustomers, h5DivLoadProducts;

	private Integer pageSizeForCustomers = 10, pageSizeForProducts = 20;

	private Set<Product> selectedProducts;

	private FindProductByDescriptionDTO findProductByDescriptionDTO;

	private String headerExceptionDialog;
	private String processingEntity;

	private SearchCustomerByCodeAndStoreDTO searchCustomerDTO;

	private String nameCustomerToFind;

	private ItemXlsxFileLayout budgetImportXlsxForm;

	private BudgetXlsxPreviewedDTO budgetXlsxPreview;

	private Budget budget;

	private BigDecimal itemDiscountToView;

	private Set<String> itemLines;

	private ItemLineDiscountForm itemLineDiscount;

	private ProspectCustomerForm prospectCustomerForm;

	private BigDecimal globalDiscount;

	private DiscountView discView;

	private int onRowItemQuantity;

	@NotEmpty
	@Size(max = 8, min = 8)
	private String cepToSearch;

	private CustomerRepresentativeOrderForm customerRepresentativeOrderForm;

	private String paramBudgetID;

	private boolean isOrder;

	public NewBudgetOrderController() {
		this(null, null, null, null, null, null, null, null);
	}

	@Inject
	public NewBudgetOrderController(ResourceBundleService resourceBundleService, BudgetCrudService budgetService,
			ClientErrorExceptionController responseController,
			ProcessingExceptionFacesMessageHelper processingExceptionMessageHelper, ProductService productService,
			ItemService itemService, ZipCodeService cep, BudgetCommonBehaviorHelper budgetRequestService) {
		super();
		bulkInstantiationObjectsInBackGround();
		this.resourceBundleService = resourceBundleService;
		this.budgetService = budgetService;
		this.responseController = responseController;
		this.prossExceptionMessageShower = processingExceptionMessageHelper;
		this.productService = productService;
		this.itemService = itemService;
		this.cepCervice = cep;
		this.budgetBehaviorHelper = budgetRequestService;
	}

	public void openItemImportView() {
		if (budget.getCustomerOnOrder() != null) {
			if (budget.getCustomerOnOrder() instanceof ProspectCustomerOnOrder) {
				FacesUtils.warn(null, "Operação indisponível",
						"Esta operação não está disponivel para clientes de tipo prospect.", "growl");
			}
			FacesUtils.openViewOnDialog(
					Map.of("modal", true, "responsive", true, "contentWidth", "98vw", "contentHeight", "80vh"),
					"itemImport",
					Map.of("customerCode", List.of(budget.getCustomerOnOrder().getCode()), "customerStore",
							List.of(budget.getCustomerOnOrder().getStore()), "onDialog", List.of("true")));
			return;
		}
		FacesUtils.error(null, "Cliente não selecionado", null, "growl");
	}

	public void handleItemImportReturn(SelectEvent<Budget> event) {
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
		} catch (ProcessingException e) {
			prossExceptionMessageShower.displayMessage(e, null, "growl");
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
		} catch (ProcessingException e) {
			prossExceptionMessageShower.displayMessage(e, null);
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
			System.out.println("Global discount " + globalDiscount);
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
		this.itemLines = budget.getItems().parallelStream().map(Item::getLine).collect(Collectors.toSet());
	}

	public void onRowItemEdit(RowEditEvent<Item> event) {
		calculateItemQuantity(event.getObject(), onRowItemQuantity);
		budgetBehaviorHelper.calculateTotals(budget);
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

	public void removeItem(Item item) {
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

	public void newBudgetObject() {
		this.budget = new Budget();
	}

	public void handleCustomerResult(SelectEvent<Optional<Customer>> event) {
		event.getObject().ifPresentOrElse(c -> {
			budgetBehaviorHelper.setCustomer(budget, new CustomerOnOrder(c));
			FacesUtils.info(null, "Cliente selecionado", null, "growl");
			FacesUtils.ajaxUpdate("customerForm");
			FacesUtils.executeScript("PF('dlgSearchCustomer').hide();PF('blockItems').hide();");
		}, () -> FacesUtils.warn(null, "Nenhum cliente selecionado", null, "growl"));

	}

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresent(p -> {
			budgetBehaviorHelper.addItem(budget, Item.product(p));
			FacesUtils.ajaxUpdate("formItems:dtItems", "budgetTotals");
		});

	}

	public void findProductByDescription(int page) {
		try {
			Optional<ProductPageDTO> maybeProduct = productService
					.findByDescription(findProductByDescriptionDTO.getDescription(), page, pageSizeForProducts);
			maybeProduct.ifPresentOrElse(p -> {
				LazyPopulatorUtils.populate(lazyProducts, p);
			}, () -> {
				FacesUtils.error(null, resourceBundleService.getMessage("nao_encontrado"), null);
				FacesUtils.addHeaderForResponse("Backbone-Status", "Error");
			});

		} catch (ProcessingException e) {
			prossExceptionMessageShower.displayMessage(e, null);
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
		this.findProductByDescriptionDTO = new FindProductByDescriptionDTO();
		this.budgetXlsxPreview = new BudgetXlsxPreviewedDTO();
		this.budget = new Budget();
		this.itemLines = new HashSet<>();
		this.itemLineDiscount = new ItemLineDiscountForm();
		this.prospectCustomerForm = new ProspectCustomerForm();
		this.discView = new DiscountView();
		this.customerRepresentativeOrderForm = new CustomerRepresentativeOrderForm();
	}

	public LazyBehaviorDataModel<Product> getLazyProducts() {
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

	public LazyBehaviorDataModel<Customer> getLazyCustomers() {
		return lazyCustomers;
	}

	public ClientErrorExceptionController getResponseController() {
		return responseController;
	}

	public ItemXlsxFileLayout getBudgetImportXlsxForm() {
		return budgetImportXlsxForm;
	}

	public BudgetXlsxPreviewedDTO getBudgetXlsxPreview() {
		return budgetXlsxPreview;
	}

	public Budget getBudget() {
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
