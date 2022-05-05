package com.farawaybr.portal.microsoft.excel.reader;

public class XssfReaderBuilder {

	public static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
