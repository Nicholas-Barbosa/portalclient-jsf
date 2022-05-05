package com.farawaybr.portal.service;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.farawaybr.portal.dto.XlsxProductFileReadLayout;
import com.farawaybr.portal.exception.MismatchCellTypeExceptions;
import com.farawaybr.portal.microsoft.excel.reader.XssfReaderImpl;
import com.farawaybr.portal.service.XlsxProductImporter;
import com.portal.ShrinkwrapDeploymentUtils;

@RunWith(Arquillian.class)
public class XlsxProductImporterTest {

	@Inject
	private XlsxProductImporter importer;
	private XlsxProductFileReadLayout layout;

	{
		layout = new XlsxProductFileReadLayout();
		layout.setInitPosition(0);
		layout.setLastPosition(0);
		layout.setOffSetCellForProductCode(0);
		layout.setOffSetCellForProductQuantity(1);
		try (InputStream in = new FileInputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\testImportBudgetV2.xlsx")) {
			layout.setXlsxStreams(in.readAllBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Deployment
	public static JavaArchive deploy() {
		return ShrinkwrapDeploymentUtils.createdDeployment(XssfReaderImpl.class, XlsxProductImporter.class);
	}

	@Test
	@InSequence(1)
	public void importerNotNull() {
		assertNotNull(importer);
	}

	@Test
	@InSequence(2)
	public void shouldNotThrowMismatchCellTypeExceptions() {
		assertNotNull(importer.extractData(layout));
		
	}


	

}
