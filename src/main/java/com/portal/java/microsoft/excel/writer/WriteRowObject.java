package com.portal.java.microsoft.excel.writer;

import java.util.ArrayList;
import java.util.List;

public class WriteRowObject {

	private List<WriteCellAttribute> attributes;

	public WriteRowObject(List<WriteCellAttribute> attributes) {
		super();
		this.attributes = new ArrayList<>(attributes);
	}

	public List<WriteCellAttribute> getAttributes() {
		return new ArrayList<>(attributes);
	}

}
