package com.portal.client.service.microsoft.excel.reader;

public class XssfReaderBuilder {

	public static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
