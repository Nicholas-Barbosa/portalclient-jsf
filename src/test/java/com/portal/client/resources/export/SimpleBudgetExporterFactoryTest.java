package com.portal.client.resources.export;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.portal.ShrinkwrapDeploymentUtils;
import com.portal.client.microsoft.excel.writer.XssfWriterImpl;
import com.portal.client.resources.ConfigPropertyResolver;

@RunWith(Arquillian.class)
public class SimpleBudgetExporterFactoryTest {

	@Inject
	private SimpleBudgetExporterFactory factory;

	@Deployment
	public static JavaArchive deployment() {
		return ShrinkwrapDeploymentUtils
				.createdDeployment(true, "com.portal.client.resources.export", "com.portal.client.security.api")
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
