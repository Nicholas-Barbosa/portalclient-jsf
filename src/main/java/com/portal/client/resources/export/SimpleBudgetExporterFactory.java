package com.portal.client.resources.export;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import com.portal.client.cdi.qualifier.Excel;
import com.portal.client.cdi.qualifier.PDF;
import com.portal.client.cdi.qualifier.Summary;

@ApplicationScoped
public class SimpleBudgetExporterFactory {

	@Inject
	@Any
	private Instance<BudgetExporter> instances;
	private AnnotationLiteral<PDF> pdfQualifier = new AnnotationLiteral<PDF>() {
		private static final long serialVersionUID = 3542596392005026580L;
	};
	private AnnotationLiteral<Excel> excelQualifier = new AnnotationLiteral<Excel>() {
		private static final long serialVersionUID = 3542596392005026580L;
	};
	private AnnotationLiteral<Summary> summaryQualifier = new AnnotationLiteral<Summary>() {
		private static final long serialVersionUID = 3542596392005026580L;
	};

	public BudgetExporter getExporter(BudgetExportType type) {
		switch (type) {
		case PDF:
			return instances.select(pdfQualifier).get();
		case EXCEL:
			return instances.select(excelQualifier).get();
		default:
			return instances.select(summaryQualifier).get();
		}
	}
}
