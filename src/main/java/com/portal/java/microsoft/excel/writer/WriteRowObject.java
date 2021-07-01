package com.portal.java.microsoft.excel.writer;

import java.util.ArrayList;
import java.util.List;

public class WriteRowObject {

	private int rowPosition;
	private List<WriteCellAttribute> attributes;

	
	public WriteRowObject(int rowPosition, List<WriteCellAttribute> attributes) {
		super();
		this.rowPosition = rowPosition;
		this.attributes = attributes;
	}

	public WriteRowObject(int rowPosition) {
		super();
		this.rowPosition = rowPosition;
	}

	public List<WriteCellAttribute> getAttributes() {
		return new ArrayList<>(attributes);
	}

	public void setAttributes(List<WriteCellAttribute> attributes) {
		this.attributes = attributes;
	}

	public int getRowPosition() {
		return rowPosition;
	}

}
