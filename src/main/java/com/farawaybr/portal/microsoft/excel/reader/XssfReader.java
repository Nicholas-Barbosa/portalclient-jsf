package com.farawaybr.portal.microsoft.excel.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.farawaybr.portal.microsoft.excel.RowObject;

public interface XssfReader {

	List<RowObject> read(InputStream xlsxInputStream, int initialOffset, int endOffset) throws IOException;

	List<RowObject> read(byte[] xlsxStreams, int initialOffset, int endOffset) throws IOException;
}
