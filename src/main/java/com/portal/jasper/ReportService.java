package com.portal.jasper;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Singleton;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Singleton
public class ReportService {

	/**
	 * Export to pdf in bytes. This method will close input stream inside
	 * try-with-resources
	 * 
	 * @param compiledReport
	 * @param params
	 * @param dataSource
	 * @return
	 */
	public byte[] exportToPdf(InputStream compiledReport, Map<String, Object> params, Object dataSource) {
		try (compiledReport) {
			JasperPrint filledDocument = JasperFillManager.fillReport(compiledReport, params,
					new JRBeanCollectionDataSource(List.of(dataSource)));

			return JasperExportManager.exportReportToPdf(filledDocument);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] exportToExcel() {
		return null;
	}

}
