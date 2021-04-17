package com.portal.service;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ResourceBundleServiceImpl implements Serializable, ResourceBundleService {

	private static final long serialVersionUID = 1L;
	private final ResourceBundle resourceBundle;

	public ResourceBundleServiceImpl() {
		this(null);
	}

	@Inject
	public ResourceBundleServiceImpl(Locale locale) {
		resourceBundle = ResourceBundle.getBundle("message", locale);
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
