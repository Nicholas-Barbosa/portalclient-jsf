package com.farawaybr.portal.service.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.ResourceBundleService;

@Named
@SessionScoped
public class HoldMessageView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ResourceBundleService resourceBundleService;

	public HoldMessageView() {
		super();
	}

	public String getLabel(String message) {
		return resourceBundleService.getMessage(message);
	}

	public String label(String message) {
		return resourceBundleService.getMessage(message);
	}
}
