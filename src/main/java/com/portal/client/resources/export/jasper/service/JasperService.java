package com.portal.client.resources.export.jasper.service;

import java.io.InputStream;
import java.util.Map;

public interface JasperService {

	/**
	 * Export to pdf in bytes. This method will close input stream inside
	 * try-with-resources
	 * 
	 * @param compiledReport
	 * @param params
	 * @param dataSource
	 * @return
	 */
	public byte[] export(InputStream compiledReport, Map<String, Object> params, Object dataSource);


	
}
