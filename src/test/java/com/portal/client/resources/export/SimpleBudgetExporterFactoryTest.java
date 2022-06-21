package com.farawaybr.portal.resources.export;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.farawaybr.portal.resources.ConfigPropertyResolver;
import com.farawaybr.portal.resources.export.BudgetExportType;
import com.farawaybr.portal.resources.export.SimpleBudgetExporterFactory;
import com.farawaybr.portal.resources.poi.microsoft.excel.writer.XssfWriterImpl;
import com.portal.ShrinkwrapDeploymentUtils;

@RunWith(Arquillian.class)
public class SimpleBudgetExporterFactoryTest {

	@Inject
	private SimpleBudgetExporterFactory factory;

	@Deployment
	public static JavaArchive deployment() {
		return ShrinkwrapDeploymentUtils
				.createdDeployment(true, "com.farawaybr.portal.resources.export", "com.farawaybr.portal.security.api")
				.addClass(ConfigPropertyResolver.class).addClass(XssfWriterImpl.class);
	}

	@Test
	public void factoryNotNull() {
		assertNotNull(factory);
	}

	@Test
	public void pdfExporter() {
		assertNotNull(factory.getExporter(BudgetExportType.PDF));
	}

	@Test
	public void excelExporter() {
		assertNotNull(factory.getExporter(BudgetExportType.EXCEL));
	}

	@Test
	public void calcExporter() {
		assertNotNull(factory.getExporter(BudgetExportType.EXCEL_CALC_CONFERENCE));
	}
}
