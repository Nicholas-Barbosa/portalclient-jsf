package com.portal.client.service.export.jasper.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.portal.client.service.export.jasper.service.annt.Excel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@ApplicationScoped
@Excel
public class ExcelJasperService implements JasperService{

	@Override
	public byte[] export(InputStream compiledReport, Map<String, Object> params, Object dataSource) {
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
