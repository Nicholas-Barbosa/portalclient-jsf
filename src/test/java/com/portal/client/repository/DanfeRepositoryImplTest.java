package com.portal.client.repository;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.portal.ShrinkwrapDeploymentUtils;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.api.register.ProtheusApiRegisterImpl;

@RunWith(Arquillian.class)
public class DanfeRepositoryImplTest {

	@Inject
	private DanfeRepository repository;

	@Inject
	private ProtheusApiRegisterImpl apiRegister;

	@Deployment
	public static JavaArchive createDeploy() {
		return ShrinkwrapDeploymentUtils
				.createdDeployment(true, "com.nicholas.jaxrsclient", "com.portal.client.security.api")
				.addClass(ConfigPropertyResolver.class).addClass(DanfeRepository.class)
				.addClass(DanfeRepositoryImpl.class);
	}

	@Test
	@InSequence(1)
	public void shouldNotBeNull() {
		assertNotNull(repository);
	}

}
