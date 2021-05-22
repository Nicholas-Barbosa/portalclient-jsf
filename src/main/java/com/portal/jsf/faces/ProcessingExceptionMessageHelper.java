package com.portal.jsf.faces;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;

import com.portal.service.ResourceBundleService;

@Singleton
public class ProcessingExceptionMessageHelper {

	@Inject
	private ResourceBundleService resourceBundleService;

	public void displayMessage(ProcessingException p, String clientId) {
		Throwable rootException = p.getCause();
		if (rootException instanceof SocketException) {
			FacesHelper.error(clientId, resourceBundleService.getMessage("socket_exception"),
					resourceBundleService.getMessage("socket_exception_detalhes"));
		} else if (rootException instanceof ConnectException) {
			FacesHelper.error(clientId, resourceBundleService.getMessage("connect_exception"),
					resourceBundleService.getMessage("connect_exception_detales"));

		} else if (rootException instanceof TimeoutException || rootException instanceof SocketTimeoutException) {
			FacesHelper.error(clientId, resourceBundleService.getMessage("timeout_ler_response"),
					resourceBundleService.getMessage("timeout_ler_response_detalhes"));
		} else if (rootException instanceof InternalServerErrorException) {
			FacesHelper.error(clientId, resourceBundleService.getMessage("erro_servidor_destino"), null);
		}
	}

}
