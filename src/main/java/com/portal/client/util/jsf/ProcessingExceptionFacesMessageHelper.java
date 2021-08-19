package com.portal.client.util.jsf;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;

import org.primefaces.PrimeFaces;

import com.portal.client.service.ResourceBundleService;

@ApplicationScoped
public class ProcessingExceptionFacesMessageHelper {

	private ResourceBundleService resourceBundleService;

	public ProcessingExceptionFacesMessageHelper() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public ProcessingExceptionFacesMessageHelper(ResourceBundleService resourceBundleService) {
		super();
		this.resourceBundleService = resourceBundleService;
	}

	public void displayMessage(ProcessingException exception, String clientId) {
		Throwable cause = exception.getCause();
		if (cause instanceof SocketException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("socket_exception"),
					resourceBundleService.getMessage("socket_exception_detalhes"));
		} else if (cause instanceof ConnectException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("connect_exception"),
					resourceBundleService.getMessage("connect_exception_detales"));
		} else if (cause instanceof TimeoutException || cause instanceof SocketTimeoutException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("timeout_ler_response"),
					resourceBundleService.getMessage("timeout_ler_response_detalhes"));
		} else if (cause instanceof InternalServerErrorException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("erro_servidor_destino"), null);
		}
	}

	public void displayMessage(ProcessingException exception, String clientId, String messageIdToUpdate) {
		this.displayMessage(exception, clientId);
		PrimeFaces.current().ajax().update(messageIdToUpdate);
	}
}
