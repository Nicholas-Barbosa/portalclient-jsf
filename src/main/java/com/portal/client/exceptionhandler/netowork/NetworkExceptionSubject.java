package com.portal.client.exceptionhandler.netowork;

public interface NetworkExceptionSubject {

	void notifyObservers(Exception e);
}
