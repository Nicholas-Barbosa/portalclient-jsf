package com.portal.client.service.microsoft.excel.reader;

public interface XssfReaderBuilder {

	static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
