package com.farawaybr.portal.microsoft.excel.writer;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.farawaybr.portal.microsoft.excel.writer.XssfWriter;
import com.portal.ShrinkwrapDeploymentUtils;

@ExtendWith(ArquillianExtension.class)
public class XssfWriterImplTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkwrapDeploymentUtils.createdDeployment(true, "com.farawaybr.portal.microsoft.excel");
		System.out.println(jar.toString(true));
		return jar;
	}

	@Inject
	private XssfWriter writer;

	@Test
	public void notNull() {
		assertNotNull(writer);
	}

}
