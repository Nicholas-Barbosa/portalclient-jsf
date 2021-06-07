package com.portal.helper.jsf.faces;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;

import com.portal.service.ResourceBundleService;

@Singleton
public class ResourceExceptionMessageHelper {

	@Inject
	private ResourceBundleService resourceBundleService;

	public void displayMessage(Exception exception, String clientId) {
		if (exception instanceof SocketException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("socket_exception"),
					resourceBundleService.getMessage("socket_exception_detalhes"));
		} else if (exception instanceof ConnectException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("connect_exception"),
					resourceBundleService.getMessage("connect_exception_detales"));
		} else if (exception instanceof TimeoutException || exception instanceof SocketTimeoutException) {
			System.out.println("timeout exception ou  socket timeout exception!");
			FacesUtils.error(clientId, resourceBundleService.getMessage("timeout_ler_response"),
					resourceBundleService.getMessage("timeout_ler_response_detalhes"));
		} else if (exception instanceof InternalServerErrorException) {
			FacesUtils.error(clientId, resourceBundleService.getMessage("erro_servidor_destino"), null);
		}
	}

}
