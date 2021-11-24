package com.portal.client.repository;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.portal.ShrinkwrapDeploymentUtils;
import com.portal.client.dto.BatchProductSearchDataWrapper;
import com.portal.client.dto.ProductToFind;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.api.APIsRepository;
import com.portal.client.security.api.ProtheusApiUrlResolver;
import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.security.api.register.ProtheusApiRegisterImpl;

@RunWith(Arquillian.class)
public class ProductRepositoryImplTest {

	@Inject
	private ProductRepositoryImpl repository;

	@Inject
	private ProtheusApiRegisterImpl apiRegister;

	@Deployment
	public static JavaArchive deploy() {
		return ShrinkwrapDeploymentUtils.createdDeployment(true, "com.nicholas.jaxrsclient")
				.addClass(ProductRepositoryImpl.class).addClass(ProtheusAPIHelper.class).addClass(APIsRepository.class)
				.addClass(ProtheusApiRegisterImpl.class).addClass(ProtheusApiUrlResolver.class)
				.addClass(ConfigPropertyResolver.class);
	}

	@Test
	@InSequence(1)
	public void notNull() {
		assertNotNull(repository);
	}

	@Test
	@InSequence(2)
	public void batchSearchTest() {
		apiRegister.companyEnv(ProtheusCompanyApiEnv.CDG).token(
				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InBKd3RQdWJsaWNLZXlGb3IyNTYifQ.eyJpc3MiOiJUT1RWUy1BRFZQTC1GV0pXVCIsInN1YiI6ImhzdyIsImlhdCI6MTYzNzcxNjA0MiwidXNlcmlkIjoiMDAwMTYxIiwiZXhwIjoxNjM3NzE5NjQyLCJlbnZJZCI6IkRCX0NERyJ9.G8jIY5l9W9jtvvSPYGY7AJQEPiAKgqu5K8i-BigClMjGJrXJ0LI03qd69_s9N8T1CNeDNxvYk0-HTr5Zx_0NvBqVs7T0hCgs1RpkelkTqMsNWfL9sLuk8n1-8jAGIUJJTy101K_cVuger_PemJJxb22hTYzDnZyZR-UEMHVxOl17qJPAfz_CkxAQNzmXahOyEMpzif9x5WWI5sYyjHc_xr2zyG0oMKnJq9HHOUGJn0g12T3z5AEZtO8kgCntOkpcGNPY_Gji_QgbbY9H670SOzE5wdgYWLFinX4RLA1b4z0phEGj2hhyynkh4p0aeRyE1nD7NLxmyHjseY4z-x5DQw")
				.tokenPrefix("Bearer").register();

		ProductToFind product1 = new ProductToFind("AX001", 1);
		Set<ProductToFind> productsToFind = new HashSet<>();
		productsToFind.add(product1);
		BatchProductSearchDataWrapper dataWrapper = repository.batchProductSearch("000030", "01", productsToFind);
		assertNotNull(dataWrapper);
	}
}
