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
//		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
//				.withTransitivity().asFile();
//		System.out.println("files " + files.length);
		JavaArchive jar = ShrinkwrapDeploymentUtils.createdDeployment(true, "com.portal.client.microsoft.excel");
//		for (File file : files) {
//			jar.addAsResource(file);
//		}
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
