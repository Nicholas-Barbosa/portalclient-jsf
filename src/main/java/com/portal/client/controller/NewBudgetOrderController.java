package com.portal.client.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerOnOrder;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.DiscountView;
import com.portal.client.dto.ProspectCustomerOnOrder;
import com.portal.client.exception.CustomerNotAllowed;
import com.portal.client.service.OrderCommonBehaviorHelper;
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

	private final OrderCommonBehaviorHelper budgetBehaviorHelper;

	private final HttpSession session;

	private Budget budget;

	private BigDecimal itemDiscountToView;

	private DiscountView discView;

	private CustomerRepresentativeOrderForm customerRepresentativeOrderForm;

	private String cNameToSearch;

	private ItemOrderContainerController dtItemsController;

	public NewBudgetOrderController() {
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public NewBudgetOrderController(BudgetCrudService budgetService, ClientErrorExceptionController responseController,
			ProcessingExceptionFacesMessageHelper processingExceptionMessageHelper, ProductService productService,
			OrderCommonBehaviorHelper budgetRequestService, HttpSession session,
			ItemOrderContainerController dtItemsController) {
		super();
		this.budgetService = budgetService;
		this.responseController = responseController;
		this.prossExceptionMessageShower = processingExceptionMessageHelper;
		this.budgetBehaviorHelper = budgetRequestService;
		this.session = session;
		this.discView = new DiscountView();
		this.customerRepresentativeOrderForm = new CustomerRepresentativeOrderForm();
		this.dtItemsController = dtItemsController;
		this.newBudget();
	}

	public void save() {
		try {
			budgetService.save(budget, customerRepresentativeOrderForm);
			PrimeFaces.current().executeScript("PF('successSavedBudgetOrOrder').show();");
			FacesUtils.ajaxUpdate("successPersisted");
		} catch (ProcessingException e) {
			prossExceptionMessageShower.displayMessage(e, null, "growl");
		} catch (ClientErrorException e) {
			ServerEndpointErrorUtils.openEndpointErrorOnDialog(e.getResponse());
		}
	}

	public void validBudget() {
		try {
			budgetService.checkBudgetState(budget);
			PrimeFaces.current().executeScript("PF('dlgBudgetPersistForm').show()");
		} catch (IllegalArgumentException e) {
			FacesUtils.error(null, "Não é possível efetivar o orçamento neste momento.",
					"Objeto incompleto. Entre com os dados necessário.", "growl");
		} catch (CustomerNotAllowed e) {
			FacesUtils.error(null, "Não é possível efetivar o orçamento.",
					"Esta operação é destinada a clientes normais(não prospects)", "growl");
		}

	}

	public void handleItemImportReturn(SelectEvent<Budget> event) {
		this.budgetBehaviorHelper.merge(budget, event.getObject());
		FacesUtils.ajaxUpdate("formItems:dtItems", "budgetTotals");
	}

	public final void newBudget() {
		this.budget = new Budget();
		dtItemsController.setOrder(budget);
	}

	public void handleCustomerResult(SelectEvent<Optional<Customer>> event) {
		event.getObject().ifPresentOrElse(c -> {
			budgetBehaviorHelper.setCustomer(budget, new CustomerOnOrder(c));
			
			FacesUtils.info(null, "Cliente selecionado", null, "growl");
			FacesUtils.ajaxUpdate("customerForm");
			FacesUtils.executeScript("PF('dlgSearchCustomer').hide();PF('blockItems').hide();");
		}, () -> {
			FacesUtils.warn(null, "Nenhum cliente selecionado", null, "growl");
			FacesUtils.executeScript("PF('blockItems').show;");
		});

	}

	public void handleProductResult(SelectEvent<Optional<Product>> event) {
		event.getObject().ifPresent(p -> {
			budgetBehaviorHelper.addItem(budget, Item.product(p));
			FacesUtils.ajaxUpdate("formItems:dtItems", "budgetTotals");
		});

	}

	public void getProspectFromSession() {
		ProspectCustomerOnOrder prosp = (ProspectCustomerOnOrder) session.getAttribute("prospect");
		budgetBehaviorHelper.setCustomer(budget, prosp);
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

	public DiscountView getDiscView() {
		return discView;
	}

	public CustomerRepresentativeOrderForm getCustomerRepresentativeOrderForm() {
		return customerRepresentativeOrderForm;
	}

	public String getcNameToSearch() {
		return cNameToSearch;
	}

	public void setcNameToSearch(String cNameToSearch) {
		this.cNameToSearch = cNameToSearch;
	}

	public ItemOrderContainerController getDtItemsController() {
		return dtItemsController;
	}
}
