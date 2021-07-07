package com.portal.java.controller;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.portal.java.pojo.Customer;
import com.portal.java.service.CepService;

@RequestScoped
@Named
public class CustomerDetailController {

	@Inject
	private HttpSession session;

	@Inject
	private CepService cepService;

	private Customer customer;

	private MapModel gMap;

	private String currentLatLng;

	@PostConstruct
	public void init() {
		customer = (Customer) session.getAttribute("customer_to_detail");

	}

	public void loadGMap() {
		if (gMap == null) {
			gMap = new DefaultMapModel();
			try {
				cepService.find(customer.getZipCode()).ifPresentOrElse(c -> {
					Marker marker = new Marker(new LatLng(c.getLat(), c.getLng()));
					gMap.addOverlay(marker);
					currentLatLng = c.getLat() + ", " + c.getLng();
				}, null);
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
