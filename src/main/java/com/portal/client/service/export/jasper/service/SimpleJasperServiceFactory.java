package com.portal.client.service.export.jasper.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.portal.client.service.export.jasper.service.annt.PDF;

@ApplicationScoped
public class SimpleJasperServiceFactory {

	@Inject
	@Any
	private Instance<JasperService> instances;

	private final AnnotationLiteral<PDF> pdfQualifier = new AnnotationLiteral<PDF>() {
		private static final long serialVersionUID = 1L;
	};
	private final AnnotationLiteral<PDF> excelQualifier = new AnnotationLiteral<PDF>() {
		private static final long serialVersionUID = 1L;
	};

	public JasperService getService(JasperReportType type) {
		switch (type) {
		case PDF:
			return instances.select(pdfQualifier).get();

		case EXCEL:
			return instances.select(excelQualifier).get();
		default:
			return instances.select(pdfQualifier).get();

		}
	}
}
