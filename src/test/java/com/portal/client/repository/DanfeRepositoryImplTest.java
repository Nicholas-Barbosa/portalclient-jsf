package com.portal.client.repository;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.farawaybr.portal.repository.DanfeRepository;
import com.farawaybr.portal.repository.DanfeRepositoryImpl;
import com.farawaybr.portal.resources.ConfigPropertiesResolver;
import com.farawaybr.portal.security.api.register.ProtheusApiRegisterImpl;
import com.portal.ShrinkwrapDeploymentUtils;

@RunWith(Arquillian.class)
public class DanfeRepositoryImplTest {

	@Inject
	private DanfeRepository repository;

	@Inject
	private ProtheusApiRegisterImpl apiRegister;

	@Deployment
	public static JavaArchive createDeploy() {
		return ShrinkwrapDeploymentUtils
				.createdDeployment(true, "com.nicholas.jaxrsclient", "com.farawaybr.portal.security.api")
				.addClass(ConfigPropertiesResolver.class).addClass(DanfeRepository.class)
				.addClass(DanfeRepositoryImpl.class);
	}

	@Test
	@InSequence(1)
	public void shouldNotBeNull() {
		assertNotNull(repository);
	}

}
