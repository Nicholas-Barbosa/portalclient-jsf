package com.farawaybr.portal.repository;

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

import com.farawaybr.portal.dto.BatchProductSearchDataWrapper;
import com.farawaybr.portal.dto.ProductToFind;
import com.farawaybr.portal.exception.ProductsNotFoundException;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriter;
import com.farawaybr.portal.microsoft.excel.writer.XssfWriterImpl;
import com.farawaybr.portal.repository.ProductRepositoryImpl;
import com.farawaybr.portal.resources.ConfigPropertyResolver;
import com.farawaybr.portal.service.jsonb.JsonbService;
import com.farawaybr.portal.service.jsonb.JsonbServiceImpl;
import com.portal.ShrinkwrapDeploymentUtils;

@RunWith(Arquillian.class)
public class ProductRepositoryImplTest {

	@Inject
	private ProductRepositoryImpl repository;

	@Deployment
	public static JavaArchive deploy() {
		return ShrinkwrapDeploymentUtils
				.createdDeployment(true, "com.nicholas.jaxrsclient", "com.farawaybr.portal.repository",
						"com.farawaybr.portal.security.api")
				.addClass(ConfigPropertyResolver.class).addClass(XssfWriter.class).addClass(XssfWriterImpl.class)
				.addClass(JsonbService.class).addClass(JsonbServiceImpl.class);
	}

	@Test
	@InSequence(1)
	public void notNull() {
		assertNotNull(repository);
	}

	@Test
	@InSequence(2)
	public void shouldFindProducts() {
		this.shouldApiRegistered();
		ProductToFind product1 = new ProductToFind("AX001", 1);
		Set<ProductToFind> productsToFind = new HashSet<>();
		productsToFind.add(product1);
		BatchProductSearchDataWrapper dataWrapper = repository.batchProductSearch("000030", "01", productsToFind);
		assertNotNull(dataWrapper);
	}

	@Test(expected = ProductsNotFoundException.class)
	@InSequence(3)
	public void shouldThrowProductsNotFoundException() {
		this.shouldApiRegistered();
		ProductToFind product1 = new ProductToFind("AX01", 1);
		Set<ProductToFind> productsToFind = new HashSet<>();
		productsToFind.add(product1);
		repository.batchProductSearch("000030", "01", productsToFind);

	}

	private void shouldApiRegistered() {
//		assertNotNull(apiRegister.companyEnv(ProtheusApiEnviroment.CDG).token(
//				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InBKd3RQdWJsaWNLZXlGb3IyNTYifQ.eyJpc3MiOiJUT1RWUy1BRFZQTC1GV0pXVCIsInN1YiI6ImhzdyIsImlhdCI6MTYzNzc3NTQ3MSwidXNlcmlkIjoiMDAwMTYxIiwiZXhwIjoxNjM3Nzc5MDcxLCJlbnZJZCI6IkRCX0NERyJ9.V2VIxmNpFLBekJtX0kODXh9v1tgVTca6ywSWC-1WWPnzQdNNXGvvu8_RRDlKIWVEmxZTaTHRFdJz9Rgdct7OWcc6sANDT6HRAE2vlmya3h16ulhD160Oxrg7Szllui7DhvTz7lk5KbxvE7YORJa3PZcyuvYo9j8bEyNurPsD4pqDelcUH04qLSY2VV2_7_frf1Rp_3oIv5mEp9hlW6UHMTVqGNAqG73P3Mi-5vQ7kwzsbx3aEnYYTAL5-yRQXm0nIg5Mkzyu-TDJD2bzGWtklCC_FEFdFupAyT_lEGbBceVqFfkVMESUgc0wTAlRLjutnAPQSKSf27Tq1r9GCFsAkA")
//				.tokenPrefix("Bearer").register());
	}

}
