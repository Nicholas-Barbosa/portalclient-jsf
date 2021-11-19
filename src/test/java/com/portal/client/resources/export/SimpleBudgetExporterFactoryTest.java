package com.portal.client.resources.export;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.portal.ShrinkwrapDeploymentUtils;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.resources.export.jasper.BudgetReportImpl;
import com.portal.client.security.api.APIsRepository;
import com.portal.client.security.api.ProtheusApiUrlResolver;
import com.portal.client.security.api.register.ProtheusApiRegisterImpl;

@RunWith(Arquillian.class)
public class SimpleBudgetExporterFactoryTest {

	@Inject
	private SimpleBudgetExporterFactory factory;

	@Deployment
	public static JavaArchive deployment() {
		return ShrinkwrapDeploymentUtils.createdDeployment(SimpleBudgetExporterFactory.class, PdfBudgetExporter.class,
				ExcelBudgetExporter.class, ExcelCalculationCheckBudgetExporter.class, BudgetReportImpl.class,
				ConfigPropertyResolver.class, ProtheusApiRegisterImpl.class, APIsRepository.class,
				ProtheusApiUrlResolver.class).addPackage("com.portal.client.service.export.jasper.service");
	}

	@Test
	public void factoryNotNull() {
		assertNotNull(factory);
	}

}
