package com.portal.java.exception;

public class ItemOutOfStockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7133583737880895418L;

	public ItemOutOfStockException(String msg) {
		super(msg);
	}
}
