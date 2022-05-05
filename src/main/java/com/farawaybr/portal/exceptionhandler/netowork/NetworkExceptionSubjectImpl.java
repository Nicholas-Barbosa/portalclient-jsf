package com.farawaybr.portal.exceptionhandler.netowork;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class NetworkExceptionSubjectImpl implements NetworkExceptionSubject {

//	private final List<NetworkExceptionObserver> observers = new CopyOnWriteArrayList<>();

	@Inject
	@Any
	private Instance<NetworkExceptionObserver> observers;


	@Override
	public void notifyObservers(Exception e) {
		observers.forEach(o -> o.onException(e));
	}

}
