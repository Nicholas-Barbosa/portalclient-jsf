package com.portal.java.service;

import java.util.Locale;

public interface ResourceBundleService extends ServiceSerializable{

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

	String getMessage(String message, Object... arguments);

	/**
	 * Init ResourceBundleSerive object, for subsequent calls in current session
	 * scope.
	 * 
	 * @param locale
	 */
	void init(Locale locale);

}
