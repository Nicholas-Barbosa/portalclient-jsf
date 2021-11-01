package com.portal.client.microsoft.excel.reader;

public class XssfReaderBuilder {

	public static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
