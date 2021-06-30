package com.portal.java.util.jsf;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import com.portal.java.http.ReportType;

public class FacesUtils {

	private FacesUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void openViewOnDialog(Map<String, Object> options, String viewName) {
		PrimeFaces.current().dialog().openDynamic(viewName, options, null);
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

	public static void prepareResponseForDownloadOfStreams(String fileName, byte[] streams,
			ReportType contentType) throws IOException {
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

}
