package com.portal.java.microsoft.excel;

public interface XssfReaderBuilder {

	static XssfReader createReader() {
		return new XssfReaderImpl();
	}

}
