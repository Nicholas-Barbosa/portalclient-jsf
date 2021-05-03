package com.portal.service.faces;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacesService() {
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

}
