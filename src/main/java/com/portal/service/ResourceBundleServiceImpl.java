package com.portal.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ResourceBundleServiceImpl implements ResourceBundleService {

	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle;
	private Locale defualtLocale;

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

	@Override
	public String getMessage(String message, Object... arguments) {
		// TODO Auto-generated method stub
		return MessageFormat.format(resourceBundle.getString(message), arguments);
	}

	@Override
	public void init(Locale locale) {
		if (defualtLocale != null) {
			if (!defualtLocale.equals(locale))
				resourceBundle = ResourceBundle.getBundle("message", locale);
		} else
			resourceBundle = ResourceBundle.getBundle("message", locale);
	}

}
