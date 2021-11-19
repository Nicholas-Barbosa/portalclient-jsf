package com.portal.client.service.export.jasper.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleJasperServiceFactoryTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addPackage("com.portal.client.service.export.jasper.service")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
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
		assertNotNull(factory.getService(JasperReportType.PDF));
	}
	
	@Test
	public void getExcelInstance() {
		assertNotNull(factory.getService(JasperReportType.PDF));
	}
}
