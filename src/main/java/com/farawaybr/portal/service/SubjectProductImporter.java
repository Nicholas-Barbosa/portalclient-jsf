package com.farawaybr.portal.service;

import java.io.IOException;

import com.farawaybr.portal.resources.importt.TemplateProductImporter;

public interface SubjectProductImporter extends TemplateProductImporter {

	void registerObserver(ObserverProductImporter observer);

	void unRegisterObserver(ObserverProductImporter observer);

	@Override
	default void extractData(byte[] xlsxstreams, int codeColumn, int quantityColumn, String customerCode,
			String customerStore) throws IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	default void findProducts() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	void remove();
}
