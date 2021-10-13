package com.portal.client.controller;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.portal.client.vo.Product;

@RequestScoped
@Named
public class ProductHanlderTest {

	public void handle(SelectEvent<Optional<Product>> event) {
		System.out.println("handle!");
	}
}
