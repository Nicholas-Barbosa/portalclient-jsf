package com.portal.client.resources.export.jasper.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.portal.client.resources.export.jasper.service.annt.PDF;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ApplicationScoped
@PDF
public class PdfJasperService implements JasperService {

	@Override
	public byte[] export(InputStream compiledReport, Map<String, Object> params, Object dataSource) {
		try (compiledReport) {
			JasperPrint filledDocument = JasperFillManager.fillReport(compiledReport, params,
					new JRBeanCollectionDataSource(List.of(dataSource)));

			return JasperExportManager.exportReportToPdf(filledDocument);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
