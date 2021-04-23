package com.portal.service;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;

@Named
@SessionScoped
public class ResourceBundleServiceImpl implements Serializable, ResourceBundleService {

	private static final long serialVersionUID = 1L;
	private final ResourceBundle resourceBundle;
	private final ServletRequest servletRequest;

	public ResourceBundleServiceImpl() {
		this(null);
	}

	@Inject
	public ResourceBundleServiceImpl(ServletRequest servletRequest) {
		this.servletRequest = servletRequest;
		resourceBundle = ResourceBundle.getBundle("message", this.servletRequest.getLocale());
	}

	@Override
	public String getMessage(String message) {
		// TODO Auto-generated method stub

		return resourceBundle.getString(message);
	}

	@Override
	public String getMessage(String message, Locale locale) {
		// TODO Auto-generated method stub
		return ResourceBundle.getBundle("message").getString(message);
	}

}
