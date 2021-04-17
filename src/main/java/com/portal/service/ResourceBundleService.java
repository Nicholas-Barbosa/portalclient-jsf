package com.portal.service;

import java.util.Locale;

public interface ResourceBundleService {

	/**
	 * Will retrieve a message from a resource bundle witch contains the locale
	 * obtained from ServletRequest object.
	 * 
	 * @param message
	 * @return
	 */
	String getMessage(String message);

	/**
	 * Will retrieve a message from a resource bundle witch contains this locale
	 * object.
	 * 
	 * @param message
	 * @param locale
	 * @return
	 */
	String getMessage(String message, Locale locale);
}
