package com.portal.client.controller;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.portal.client.service.FinancialBondsService;
import com.portal.client.service.ZipCodeService;
import com.portal.client.ui.lazy.datamodel.FinancialTitleLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.client.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.client.util.jsf.ServerApiExceptionFacesMessageHelper;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.Customer;
import com.portal.client.vo.FinancialBondsPage;
import com.portal.client.vo.FinancialBondsPage.FinacialBondsDTO;

@RequestScoped
@Named
public class CustomerDetailController {

	private ZipCodeService zipCodeService;

	private FinancialBondsService bondsService;

	private Customer customer;

	private MapModel gMap;

	private String currentLatLng;

	private LazyDataModelBase<FinacialBondsDTO> titles;

	private ServerApiExceptionFacesMessageHelper externalExcpetionHelper;

	@Inject
	public CustomerDetailController(HttpSession session, ZipCodeService zipCodeService,
			FinancialBondsService bondsService, ServerApiExceptionFacesMessageHelper externalExcpetionHelper) {
		super();
		this.zipCodeService = zipCodeService;
		this.bondsService = bondsService;
		currentLatLng = "-25.504460, -49.331579";
		customer = (Customer) session.getAttribute("customer_to_detail");
		this.externalExcpetionHelper = externalExcpetionHelper;
	}

	public void loadGMap() {
		if (gMap == null) {
			gMap = new DefaultMapModel();
			try {
				zipCodeService.find(customer.getZipCode()).ifPresentOrElse(c -> {
					Marker marker = new Marker(new LatLng(c.getLat(), c.getLng()), customer.getName());
					gMap.addOverlay(marker);
					currentLatLng = c.getLat() + ", " + c.getLng();
				}, () -> {
					FacesUtils.error(null, "CEP não encontrado", customer.getZipCode()
							+ " não encontrado. O mapa será centralizado usando parâmetros de lat e lng padrões.");
					PrimeFaces.current().ajax().update("growl");
				});
			} catch (SocketTimeoutException | SocketException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onBondsPage(PageEvent event) {

	}

	public void loadFinancialBonds(int page) {
		if (titles == null)
			titles = new FinancialTitleLazyDataModel();
		if (customer.getCode() != null)
			try {
				Optional<FinancialBondsPage> optional = bondsService.findByCustomerCodeStore(page, 10,
						customer.getCode(), customer.getStore());
				optional.ifPresentOrElse(f -> {
					LazyPopulateUtils.populate(titles, f);
				}, () -> {
					FacesUtils.error(null, "Nenhum título encontrado", null);
					PrimeFaces.current().ajax().update("growl");
				});

			} catch (SocketTimeoutException | SocketException | TimeoutException e) {
				externalExcpetionHelper.displayMessage(e, null, "growl");
			}

	}

	public Customer getCustomer() {
		return customer;
	}

	public MapModel getgMap() {
		return gMap;
	}

	public String getCurrentLatLng() {
		return currentLatLng;
	}

	public LazyDataModel<FinacialBondsDTO> getTitles() {
		return titles;
	}

}
