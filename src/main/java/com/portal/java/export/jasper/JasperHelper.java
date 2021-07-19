package com.portal.java.export.jasper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Singleton
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JasperHelper {

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

	public byte[] exportToExcel(InputStream compiledReport, Map<String, Object> params, Object dataSource) {
		try (compiledReport; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			JasperPrint filledDocument = JasperFillManager.fillReport(compiledReport, params,
					new JRBeanCollectionDataSource(List.of(dataSource)));
			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setSheetNames(new String[] { "budget-sheet-1" });
			JRXlsxExporter excelExporter = new JRXlsxExporter();
			excelExporter.setExporterInput(new SimpleExporterInput(filledDocument));

			excelExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			excelExporter.setConfiguration(configuration);
			excelExporter.exportReport();
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
