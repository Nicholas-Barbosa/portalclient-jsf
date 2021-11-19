package com.portal;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class ShrinkwrapDeploymentUtils {

	public static JavaArchive createdDeployment(Class<?>... javaClass) {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addClasses(javaClass)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
	}

	public static JavaArchive createdDeployment(String packageName) {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackage(packageName)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
	}

	public static JavaArchive createdDeployment(boolean addSubPackages, String... packages) {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackages(addSubPackages, packages)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
	}
}
