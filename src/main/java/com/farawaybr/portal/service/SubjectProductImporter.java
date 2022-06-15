package com.farawaybr.portal.service;

import java.io.IOException;

import com.farawaybr.portal.service.excelimporter.TemplateProductImporter;

public interface SubjectProductImporter extends TemplateProductImporter{

	void registerObserver(ObserverProductImporter observer);
	
	void unRegisterObserver(ObserverProductImporter observer);
	
	void notifyObserver();
	
	@Override
	default void extractResData(byte[] xlsxstreams, int codeColumn, int quantityColumn) throws IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	@Override
	default void findProducts() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
