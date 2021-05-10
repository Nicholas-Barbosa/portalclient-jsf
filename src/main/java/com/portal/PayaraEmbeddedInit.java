package com.portal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.embeddable.BootstrapProperties;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;

public class PayaraEmbeddedInit {

	public static void main(String[] args) {
		try {
			BootstrapProperties bootstrap = new BootstrapProperties();
			GlassFishRuntime runtime = GlassFishRuntime.bootstrap(bootstrap);
			GlassFishProperties glassfishProperties = new GlassFishProperties();
			glassfishProperties.setPort("http-listener", 8083);
			glassfishProperties.setPort("https-listener", 8184);
			GlassFish glassfish = runtime.newGlassFish(glassfishProperties);
			glassfish.start();

		}

		catch (GlassFishException ex) {
			Logger.getLogger(PayaraEmbeddedInit.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
