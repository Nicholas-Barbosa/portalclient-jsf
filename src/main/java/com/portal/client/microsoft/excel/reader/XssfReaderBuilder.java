package com.portal.client.microsoft.excel.reader;

public interface XssfReaderBuilder {

	static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
