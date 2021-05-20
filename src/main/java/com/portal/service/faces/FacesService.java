package com.portal.service.faces;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.InternalServerErrorException;

import com.portal.http.ContentType;
import com.portal.service.view.HoldMessageView;

@Stateless
public class FacesService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ExceptionMessageHandler exceptionMessageHandler;

	private final FacesDownloadStream facesDownloadStream;

	public FacesService() {
		this(null);
	}

	@Inject
	public FacesService(HoldMessageView holdMessageView) {
		this.exceptionMessageHandler = new ExceptionMessageHandler(holdMessageView);
		this.facesDownloadStream = new FacesDownloadStream();
	}

	/**
	 * Add a warn message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public FacesService warn(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
		return this;
	}

	/**
	 * Add a info message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public FacesService info(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
		return this;
	}

	/**
	 * Add a fatal message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public FacesService fatal(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
		return this;
	}

	/**
	 * Add a error message to the current request object(FacesContext)
	 * 
	 * @param clientId
	 * @param summary
	 * @param detail
	 * @return
	 */
	public FacesService error(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
		return this;
	}

	public FacesService addHeaderForResponse(String name, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().addResponseHeader(name, value.toString());
		return this;
	}

	public String getPreviousPath() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return URI.create(request.getHeader("Referer")).getPath();
	}

	public ExceptionMessageHandler exceptionMessage() {
		return exceptionMessageHandler;
	}

	public FacesDownloadStream downloadHelper() {
		return facesDownloadStream;
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

	public class FacesDownloadStream {

		public void download(String fileName, byte[] streams, ContentType contentType) throws IOException {
			switch (contentType) {
			case EXCEL:
				System.out.println("download excel");
				downloadExcel(fileName, streams);
				break;

			case PDF:
				downloadPdf(fileName, streams);
				break;
			}
			
		}

		public void downloadPdf(String fileName, byte[] streams) throws IOException {
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

		public void downloadExcel(String fileName, byte[] streams) throws IOException {
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
	}
}
