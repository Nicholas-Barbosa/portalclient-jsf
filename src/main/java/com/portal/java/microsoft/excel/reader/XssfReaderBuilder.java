package com.portal.java.microsoft.excel.reader;

public interface XssfReaderBuilder {

	static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
