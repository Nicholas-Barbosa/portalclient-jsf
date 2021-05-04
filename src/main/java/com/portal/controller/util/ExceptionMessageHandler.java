package com.portal.controller.util;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import com.portal.service.faces.FacesHelper;
import com.portal.service.view.HoldMessageView;

public class ExceptionMessageHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 195511019926868526L;
	private final FacesHelper facesHelper;
	private final HoldMessageView holderMessage;

	@Inject
	public ExceptionMessageHandler(FacesHelper facesHelper, HoldMessageView holderMessage) {
		super();
		this.facesHelper = facesHelper;
		this.holderMessage = holderMessage;
	}

	/**
	 * Add message object to current request object(FacesContext) following
	 * Exception instanceof.
	 * 
	 * The messages will be retrieved from resources bundles.
	 * 
	 * @param clientId
	 * @param e
	 * @throws RuntimeException
	 */
	public void addMessageByException(String clientId, Exception e) throws RuntimeException {
		if (e instanceof SocketException) {
			this.facesHelper.error(clientId, holderMessage.label("socket_exception"),
					holderMessage.label("socket_exception_detalhes"));
		} else if (e instanceof ConnectException) {
			this.facesHelper.error(clientId, holderMessage.label("connect_exception"),
					holderMessage.label("connect_exception_detales"));
		} else if (e instanceof IllegalArgumentException) {
			facesHelper.error(clientId, holderMessage.label("respota_invalida_servidor"),
					holderMessage.label("detalhes_reposta_invalida_servidor"));
		} else if (e instanceof TimeoutException) {
			facesHelper.error(clientId, holderMessage.label("timeout_ler_response"),
					holderMessage.label("timeout_ler_response_detalhes"));
		} else
			throw new RuntimeException(e);
	}
}
