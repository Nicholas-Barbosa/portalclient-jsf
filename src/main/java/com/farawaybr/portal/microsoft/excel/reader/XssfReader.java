package com.farawaybr.portal.microsoft.excel.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.farawaybr.portal.microsoft.excel.RowObject;

public interface XssfReader {

	List<RowObject> read(InputStream xlsxInputStream, int arg1, int arg2, CellReadPolicy readPolicy,int fromRow,int toRow) throws IOException;

	List<RowObject> read(byte[] xlsxStreams, int arg1, int arg2, CellReadPolicy readPolicy,int fromRow,int toRow) throws IOException;

}
