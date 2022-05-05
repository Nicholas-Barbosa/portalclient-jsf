package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.PrimeFaces.Ajax;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.farawaybr.portal.service.ZipCodeService;
import com.farawaybr.portal.util.jsf.FacesUtils;

@ViewScoped
@Named
public class GMapController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3901260370961743998L;

	private ZipCodeService zipCodeService;

	private MapModel gMap;

	private String currentLatLng;

	public GMapController() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public GMapController(ZipCodeService zipCodeService) {
		super();
		this.zipCodeService = zipCodeService;
		currentLatLng = "-25.504460, -49.331579";
		gMap = new DefaultMapModel();
	}

	public void loadGMap(String zipCode, String markerTitle) {
		Ajax ajax = PrimeFaces.current().ajax();
		zipCodeService.find(zipCode).ifPresentOrElse(c -> {
			Marker marker = new Marker(new LatLng(c.getLat(), c.getLng()), markerTitle);
			gMap.addOverlay(marker);
			currentLatLng = c.getLat() + ", " + c.getLng();
		}, () -> {
			FacesUtils.error(null, "CEP não encontrado",
					zipCode + " não encontrado. O mapa será centralizado usando parâmetros de lat e lng padrões.");
			ajax.update("growl");
			Marker marker = new Marker(new LatLng(-25.504460, -49.331579), "Lat lng padrões");
			gMap.addOverlay(marker);
		});
	}

	public MapModel getgMap() {
		return gMap;
	}

	public String getCurrentLatLng() {
		return currentLatLng;
	}

}
