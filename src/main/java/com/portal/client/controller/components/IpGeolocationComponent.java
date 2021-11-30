package com.portal.client.controller.components;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.portal.client.dto.IPGeoData;
import com.portal.client.service.IpGeoService;

@ViewScoped
@Named
public class IpGeolocationComponent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1369508368041116790L;

	private IPGeoData data;

	@Inject
	private IpGeoService ipGeoService;

	public void findIp(String ip) {
		ip = ip.equals("127.0.0.1") ? "35.231.237.151" : ip;
		this.data = ipGeoService.findByIp(ip);
	}

	public IPGeoData getData() {
		return data;
	}
}
