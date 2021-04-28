package com.portal.service.faces;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;

import com.portal.service.view.HoldMessageView;

public class FacesService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final HoldMessageView holderMessage;

	public FacesService() {
		this(null);
	}

	@Inject
	public FacesService(HoldMessageView holderMessage) {
		super();
		this.holderMessage = holderMessage;
	}

	public FacesService warn(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
		return this;
	}

	public FacesService info(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
		return this;
	}

	public FacesService fatal(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
		return this;
	}

	public FacesService error(String clientId, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
		return this;
	}

	public FacesService checkProcessingExceptionCauseAndAddMessage(ProcessingException e) {
		if (e.getCause() instanceof SocketTimeoutException) {
			this.error(null, holderMessage.label("socket_exception"),
					MessageFormat.format(holderMessage.label("socket_exception_detalhes"), e.getMessage()));
		} else if (e.getCause() instanceof TimeoutException) {
			this.error(null, holderMessage.label("timeout_ler_response"),
					MessageFormat.format(holderMessage.label("timeout_ler_response_detalhes"), e.getMessage()));

		} else {
			e.printStackTrace();
		}
		
		return this;
	}

	public FacesService addHeaderForResponse(String name, String value) {
		FacesContext.getCurrentInstance().getExternalContext().addResponseHeader(name, value);
		return this;
	}
}
