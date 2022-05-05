package com.farawaybr.portal.resources.export.jasper.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.farawaybr.portal.cdi.qualifier.Excel;
import com.farawaybr.portal.cdi.qualifier.PDF;

@ApplicationScoped
public class SimpleJasperServiceFactory {

	@Inject
	@Any
	private Instance<JasperService> instances;

	private final AnnotationLiteral<PDF> pdfQualifier = new AnnotationLiteral<PDF>() {
		private static final long serialVersionUID = 1L;
	};
	private final AnnotationLiteral<Excel> excelQualifier = new AnnotationLiteral<Excel>() {
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
