package com.portal.client.microsoft.excel.writer;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.portal.ShrinkwrapDeploymentUtils;

@RunWith(Arquillian.class)
public class XssfWriterImplTest {

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = ShrinkwrapDeploymentUtils.createdDeployment(true, "com.portal.client.microsoft.excel");
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
