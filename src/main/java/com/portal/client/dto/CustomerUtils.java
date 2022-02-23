package com.portal.client.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.portal.client.vo.Customer;

public class CustomerUtils {

	public static boolean isNull(Customer customer) {
		return customer == null ? true : reflectAllMethods(customer);
	}

	private static boolean reflectAllMethods(Customer customer) {
		return Arrays.stream(customer.getClass().getMethods()).filter(method -> method.getName().startsWith("get"))
				.allMatch(method -> {
					try {
						return method.invoke(customer) != null;
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return false;
				});
	}
}
