package com.portal.service;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class ResourceBundleServiceImpl implements Serializable, ResourceBundleService {

	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private Locale defualtLocale;

	@Override
	public String getMessage(String message) {
		// TODO Auto-generated method stub
		if (resourceBundle != null)
			return resourceBundle.getString(message);
		throw new IllegalStateException("ResourceBundle not initialized!");
	}

	@Override
	public String getMessage(String message, Locale locale) {
		// TODO Auto-generated method stub
		return ResourceBundle.getBundle("message").getString(message);
	}

	@Override
	public void init(Locale locale) {
		if (defualtLocale != null) {
			if (!defualtLocale.equals(locale))
				resourceBundle = ResourceBundle.getBundle("message", locale);
		}else
			resourceBundle = ResourceBundle.getBundle("message", locale);
	}

}
