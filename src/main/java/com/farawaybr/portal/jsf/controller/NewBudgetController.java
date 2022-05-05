package com.farawaybr.portal.jsf.controller;

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

import com.farawaybr.portal.dto.CustomerRepresentativeOrderForm;
import com.farawaybr.portal.exception.CustomerNotAllowed;
import com.farawaybr.portal.jsf.controller.components.CustomerSearchObserver;
import com.farawaybr.portal.jsf.controller.components.ItemOrderContainerController;
import com.farawaybr.portal.service.OrderBehaviorHelper;
import com.farawaybr.portal.service.crud.BudgetCrudService;
import com.farawaybr.portal.service.crud.ProductService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.util.jsf.ProcessingExceptionFacesMessageHelper;
import com.farawaybr.portal.util.jsf.ServerEndpointErrorUtils;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.CustomerOnOrder;
import com.farawaybr.portal.vo.ProspectCustomerOnOrder;

@Named
@ViewScoped
public class NewBudgetController implements Serializable, CustomerSearchObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537974193888491899L;

	private final BudgetCrudService budgetService;

	private final ClientErrorExceptionController responseController;

	private final ProcessingExceptionFacesMessageHelper prossExceptionMessageShower;

	private final OrderBehaviorHelper budgetBehaviorHelper;

	private final HttpSession session;

	private Budget budget;

	private BigDecimal itemDiscountToView;

	private CustomerRepresentativeOrderForm customerRepresentativeOrderForm;

	private String cNameToSearch;

	private ItemOrderContainerController dtItemsController;

	public NewBudgetController() {
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public NewBudgetController(BudgetCrudService budgetService, ClientErrorExceptionController responseController,
			ProcessingExceptionFacesMessageHelper processingExceptionMessageHelper, ProductService productService,
			OrderBehaviorHelper budgetRequestService, HttpSession session,
			ItemOrderContainerController dtItemsController) {
		super();
		this.budgetService = budgetService;
		this.responseController = responseController;
		this.prossExceptionMessageShower = processingExceptionMessageHelper;
		this.budgetBehaviorHelper = budgetRequestService;
		this.session = session;
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

	@Override
	public void onCustomerSelect(Customer customer) {
		budgetBehaviorHelper.setCustomer(budget, new CustomerOnOrder(customer));
		FacesUtils.info(null, "Cliente selecionado", null, "growl");
		FacesUtils.ajaxUpdate("customerForm");
		FacesUtils.executeScript("PF('blockItems').hide();");

	}

	public void handleCustomerResult(SelectEvent<Optional<Customer>> event) {

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
