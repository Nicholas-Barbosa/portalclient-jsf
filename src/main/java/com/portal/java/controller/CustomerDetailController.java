package com.portal.java.controller;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.portal.java.dto.Customer;
import com.portal.java.service.ZipCodeService;
import com.portal.java.util.jsf.FacesUtils;

@RequestScoped
@Named
public class CustomerDetailController {

	
	@Inject
	private HttpSession session;

	@Inject
	private ZipCodeService cepService;

	private Customer customer;

	private MapModel gMap;

	private String currentLatLng;

	@PostConstruct
	public void init() {
		customer = (Customer) session.getAttribute("customer_to_detail");
		currentLatLng = "-25.504460, -49.331579";
	}

	public void loadGMap() {
		if (gMap == null) {
			gMap = new DefaultMapModel();
			try {
				cepService.find(customer.getZipCode()).ifPresentOrElse(c -> {
					Marker marker = new Marker(new LatLng(c.getLat(), c.getLng()), customer.getName());
					gMap.addOverlay(marker);
					currentLatLng = c.getLat() + ", " + c.getLng();
				}, () -> {
					FacesUtils.error(null, "CEP não encontrado", customer.getZipCode() +" não encontrado. O mapa será centralizado usando as configurações padrões.");
					PrimeFaces.current().ajax().update("growl");
				});
			} catch (SocketTimeoutException | SocketException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
}
