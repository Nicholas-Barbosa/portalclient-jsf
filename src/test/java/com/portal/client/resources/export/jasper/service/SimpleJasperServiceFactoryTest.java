package com.farawaybr.portal.resources.export.jasper.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.farawaybr.portal.resources.export.jasper.service.ExcelJasperService;
import com.farawaybr.portal.resources.export.jasper.service.JasperReportType;
import com.farawaybr.portal.resources.export.jasper.service.PdfJasperService;
import com.farawaybr.portal.resources.export.jasper.service.SimpleJasperServiceFactory;
import com.farawaybr.portal.resources.poi.microsoft.excel.writer.XssfWriterImpl;
import com.portal.ShrinkwrapDeploymentUtils;

@RunWith(Arquillian.class)
public class SimpleJasperServiceFactoryTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkwrapDeploymentUtils
				.createdDeployment(true, "com.farawaybr.portal.resources", "com.farawaybr.portal.security.api")
				.addClass(XssfWriterImpl.class);
//		System.out.println(jar.toString(true));
		return jar;
	}

	@Inject
	private SimpleJasperServiceFactory factory;

	@Test
	public void shouldCreateFactoryInstance() {
		assertNotNull(factory);
	}

	@Test
	public void getPdfInstance() {
		assertTrue(factory.getService(JasperReportType.PDF) instanceof PdfJasperService);
	}

	@Test
	public void getExcelInstance() {
		assertTrue(factory.getService(JasperReportType.EXCEL) instanceof ExcelJasperService);
	}
}
