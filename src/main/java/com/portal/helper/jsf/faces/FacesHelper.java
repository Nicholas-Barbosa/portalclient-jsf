package com.portal.helper.jsf.faces;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.ws.rs.InternalServerErrorException;

import com.portal.http.ContentType;
import com.portal.service.view.HoldMessageView;

public class FacesHelper {

	/**
	 * Add a warn message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public static void warn(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	/**
	 * Add a info message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public static void info(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	/**
	 * Add a fatal message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public static void fatal(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
	}

	/**
	 * Add a error message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public static void error(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	public static void addHeaderForResponse(String name, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().addResponseHeader(name, value.toString());
	}

	public static void prepareResponseHeadersResponseForDownloadOfStreams(String fileName, byte[] streams,
			ContentType contentType) throws IOException {
		switch (contentType) {
		case EXCEL:
			downloadExcel(fileName, streams);
			break;

		case PDF:
			downloadPdf(fileName, streams);
			break;
		}
	}

	private static void downloadPdf(String fileName, byte[] streams) throws IOException {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();

		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseContentLength(streams.length);
		externalContext.setResponseHeader("Content-Disposition",
				String.format("%s;%s=%s", "attachment", "filename", fileName));
		try (OutputStream outputStream = new BufferedOutputStream(externalContext.getResponseOutputStream())) {
			outputStream.write(streams);
		} finally {
			currentInstance.responseComplete();
		}

	}

	private static void downloadExcel(String fileName, byte[] streams) throws IOException {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();

		externalContext.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		externalContext.setResponseContentLength(streams.length);
		externalContext.setResponseHeader("Content-Disposition",
				String.format("%s;%s=%s", "attachment", "filename", fileName));
		try (OutputStream outputStream = new BufferedOutputStream(externalContext.getResponseOutputStream())) {
			outputStream.write(streams);
		} finally {
			currentInstance.responseComplete();
		}

	}

	public class ExceptionMessageHandler implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 195511019926868526L;
		private final HoldMessageView holderMessage;

		public ExceptionMessageHandler(HoldMessageView holderMessage) {
			super();
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
				error(clientId, holderMessage.label("socket_exception"),
						holderMessage.label("socket_exception_detalhes"));
			} else if (e instanceof ConnectException) {
				error(clientId, holderMessage.label("connect_exception"),
						holderMessage.label("connect_exception_detales"));

			} else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
				error(clientId, holderMessage.label("timeout_ler_response"),
						holderMessage.label("timeout_ler_response_detalhes"));
			} else if (e instanceof InternalServerErrorException) {
				error(clientId, holderMessage.label("erro_servidor_destino"), null);
			} else
				e.printStackTrace();
		}
	}

}
