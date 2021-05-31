package com.portal.exception;

public class ObjectNotFoundOnCloudBucket extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1178934453117987691L;

	public ObjectNotFoundOnCloudBucket(String msg) {
		super(msg);
	}
}
