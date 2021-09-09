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

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerAddress;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.CustomerPurchaseInfo;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.DiscountView;
import com.portal.client.dto.ItemLineDiscountForm;
import com.portal.client.dto.ProspectCustomerForm;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.exception.ItemQuantityNotAllowed;
import com.portal.client.service.ItemService;
import com.portal.client.service.OrderCommonBehaviorHelper;
import com.portal.client.service.ZipCodeService;
import com.portal.client.service.crud.BudgetCrudService;
import com.portal.client.service.crud.ProductService;
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

	private final BudgetCrudService budgetService;

	private final ClientErrorExceptionController responseController;

	private final ProcessingExceptionFacesMessageHelper prossExceptionMessageShower;

	private final ItemService itemService;

	private final ZipCodeService cepCervice;

	private final OrderCommonBehaviorHelper budgetBehaviorHelper;

	private String nameCustomerToFind;

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
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public NewBudgetOrderController(BudgetCrudService budgetService, ClientErrorExceptionController responseController,
			ProcessingExceptionFacesMessageHelper processingExceptionMessageHelper, ProductService productService,
			ItemService itemService, ZipCodeService cep, OrderCommonBehaviorHelper budgetRequestService) {
		super();
		bulkInstantiationObjectsInBackGround();
		this.budgetService = budgetService;
		this.responseController = responseController;
		this.prossExceptionMessageShower = processingExceptionMessageHelper;
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
				this.prospectCustomerForm.setStreet(cep.getStreet());
				this.prospectCustomerForm.setStateAcronym(cep.getState());
				this.prospectCustomerForm.setDistrict(cep.getDistrict());
				this.prospectCustomerForm.setCity(cep.getCity());
				FacesUtils.info(null, "CEP encontrado!", cep.getStreet() + ", " + cep.getDistrict() + " - "
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

			CustomerAddress customerAddress = new CustomerAddress(prospectCustomerForm.getStreet(),
					prospectCustomerForm.getDistrict(), prospectCustomerForm.getCity(), cepToSearch,
					prospectCustomerForm.getStateAcronym());
			CustomerPurchaseInfo purshaseInfo = new CustomerPurchaseInfo(0f, 0f, 0f, null, null,
					prospectCustomerForm.getPaymentTerms(), null, null);
			CustomerOnOrder customer = new ProspectCustomerOnOrder(null, null, prospectCustomerForm.getCnpj(), null,
					prospectCustomerForm.getName(), prospectCustomerForm.getName(), customerAddress, purshaseInfo, null,
					prospectCustomerForm.getSellerType());
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

	private final void bulkInstantiationObjectsInBackGround() {
		this.budget = new Budget();
		this.itemLines = new HashSet<>();
		this.itemLineDiscount = new ItemLineDiscountForm();
		this.prospectCustomerForm = new ProspectCustomerForm();
		this.discView = new DiscountView();
		this.customerRepresentativeOrderForm = new CustomerRepresentativeOrderForm();
	}

	public String getNameCustomerToFind() {
		return nameCustomerToFind;
	}

	public void setNameCustomerToFind(String nameCustomerToFind) {
		this.nameCustomerToFind = nameCustomerToFind;
	}

	public ClientErrorExceptionController getResponseController() {
		return responseController;
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
