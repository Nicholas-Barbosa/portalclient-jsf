package com.farawaybr.portal.util.jsf;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

public class FacesUtils {

	private FacesUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void openViewOnDialog(Map<String, Object> options, String viewName) {
		PrimeFaces.current().dialog().openDynamic(viewName, options, null);
	}

	public static void openViewOnDialog(Map<String, Object> options, String viewName,
			Map<String, List<String>> params) {
		PrimeFaces.current().dialog().openDynamic(viewName, options, params);
	}

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

	public static void warn(String clientId, String summary, String detail, String messageComponentToUpdate) {
		warn(clientId, summary, detail);
		PrimeFaces.current().ajax().update(messageComponentToUpdate);
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

	public static void info(String clientId, String summary, String detail, String messageIdToUpdate) {
		info(clientId, summary, detail);
		ajaxUpdate(messageIdToUpdate);
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

	public static void fatal(String clientId, String summary, String detail, String messageIdToUpdate) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
		ajaxUpdate(messageIdToUpdate);
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

	public static void error(String clientId, String summary, String detail, String messageIdToUpdate) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
		ajaxUpdate(messageIdToUpdate);
	}

	public static void addHeaderForResponse(String name, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().addResponseHeader(name, value.toString());
	}

	public static void ajaxUpdate(String... id) {
		PrimeFaces.current().ajax().update(id);
	}

	public static void executeScript(String script) {
		PrimeFaces.current().executeScript(script);
	}

	public static void prepareResponseForDownloadOfStreams(String fileName, byte[] streams, String contentType) {

		FacesContext currentInstance = FacesContext.getCurrentInstance();
		ExternalContext externalContext = currentInstance.getExternalContext();

		externalContext.setResponseContentType(contentType);
		externalContext.setResponseContentLength(streams.length);
		externalContext.setResponseHeader("Content-Disposition",
				String.format("%s;%s=%s", "attachment", "filename", fileName));
		try (OutputStream outputStream = new BufferedOutputStream(externalContext.getResponseOutputStream())) {
			outputStream.write(streams);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			currentInstance.responseComplete();
		}

	}

}
