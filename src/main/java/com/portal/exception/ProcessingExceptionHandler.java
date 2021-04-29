package com.portal.exception;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;

import com.portal.service.faces.FacesService;
import com.portal.service.view.HoldMessageView;

public class ProcessingExceptionHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7440991180440747061L;
	private final FacesService facesService;
	private final HoldMessageView holderMessage;

	public ProcessingExceptionHandler() {
		this(null, null);
	}

	@Inject
	public ProcessingExceptionHandler(FacesService facesService, HoldMessageView holderMessage) {
		super();
		this.facesService = facesService;
		this.holderMessage = holderMessage;
	}

	/**
	 * Check the cause for ProcessingException. If the cause is
	 * SocketTimeoutException,TimeoutException,IllegalArgumentException
	 * HoldMessageView label mehtod will be called with the following parameters
	 * socket_exception,timeout_ler_response,impossivel_enviar_request respectively
	 * and a message will be added to the current request object(FacesContext)
	 * 
	 * @param e
	 */
	public void checkProcessingExceptionCauseAndAddMessage(ProcessingException e,String clientIdMessage) {
		if (e.getCause() instanceof SocketTimeoutException) {
			facesService.error(clientIdMessage,holderMessage.label("socket_exception"),
					MessageFormat.format(holderMessage.label("socket_exception_detalhes"), e.getMessage()));
		} else if (e.getCause() instanceof TimeoutException) {
			facesService.error(clientIdMessage, holderMessage.label("timeout_ler_response"),
					MessageFormat.format(holderMessage.label("timeout_ler_response_detalhes"), e.getMessage()));

		} else if (e.getCause() instanceof IllegalArgumentException) {
			facesService.error(clientIdMessage, holderMessage.label("respota_invalida_servidor"),
					holderMessage.label("detalhes_reposta_invalida_servidor"));

		} else if (e.getCause() instanceof ConnectException) {
			facesService.error(clientIdMessage, holderMessage.label("connect_exception"),
					holderMessage.label("connect_exception_detales"));
		} else
			e.printStackTrace();

	}
}
