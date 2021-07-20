package com.portal.client.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class DataTableSkeletonObjects {

	public List<Integer> getSkeleton(int numberOfElements) {
		return Stream.iterate(0, i -> i <= numberOfElements, i -> i + 1).collect(Collectors.toList());
	}

}
