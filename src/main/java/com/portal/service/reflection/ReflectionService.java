package com.portal.service.reflection;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class ReflectionService implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -513654863841560219L;

	public <T> void executeMethods(Map<String, Object[]> mapMethods, T t) {
		Method[] methods = t.getClass().getDeclaredMethods();

		Arrays.stream(methods).parallel().filter(m -> mapMethods.containsKey(m.getName())).forEach(m -> {
			try {
				m.invoke(t, mapMethods.get(m.getName()));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		;
	}

}
