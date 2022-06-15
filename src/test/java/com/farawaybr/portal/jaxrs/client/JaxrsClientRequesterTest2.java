package com.farawaybr.portal.jaxrs.client;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.farawaybr.portal.jaxrs.client.JaxrsRequestData.JaxrsRequestDataBuilder;

@ExtendWith(ArquillianExtension.class)
class JaxrsClientRequesterTest2 {

	@Inject
	private JaxrsClientRequester requester;

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class)
				.addClasses(JaxrsClientRequester.class, JaxrsRequestData.class, JaxrsClientSource.class,
						JaxrsRequestDataBuilder.class)
				// Enable CDI (Optional since Java EE 7.0)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	@DisplayName("Http get request")
	void testGet() {
		System.out.println(requester);
//		requester.request(JaxrsRequestDataBuilder.getInstance().method("GET")
//				.url("https://api.coindesk.com/v1/bpi/currentprice.json").mediaType(MediaType.APPLICATION_JSON)
//				.build());

	}

}
