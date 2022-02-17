package com.portal.client.security.api.register;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.portal.ShrinkwrapDeploymentUtils;
import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.api.ProtheusApiData;
import com.portal.client.security.api.ProtheusApiEnviroment;

@RunWith(Arquillian.class)
public class ProtheusApiRegisterImplTest {

	@Inject
	private ProtheusApiRegister register;

	@Deployment
	public static JavaArchive deployment() {
		return ShrinkwrapDeploymentUtils.createdDeployment(true,"com.portal.client.security.api").addClass(ConfigPropertyResolver.class);
	}

	@Test
	public void registerSession() {
		ProtheusApiData apiData =  register.token("token").tokenPrefix("tokenPrefix")
				.companyEnv(ProtheusApiEnviroment.SPG).register();
		assertEquals("http://192.168.0.201:8096/rest", apiData.getBaseUrl());
		System.out.println(apiData);
	}

}
