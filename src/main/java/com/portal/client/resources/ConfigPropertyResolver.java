package com.portal.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Singleton
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ConfigPropertyResolver {

	private Properties properties;

	public ConfigPropertyResolver() {
		properties = new Properties();
		loadProperties();
	}

	public String getProperty(String key) {
		String property = properties.getProperty(key);
		if (property != null)
			return property;
		throw new IllegalArgumentException("Key not found in config.properties");
	}

	private void loadProperties() {
		try (InputStream inputStream = getClass().getResourceAsStream("/config.properties")) {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
