package com.farawaybr.portal.resources.poi.microsoft.excel.reader;

public class XssfReaderBuilder {

	public static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
