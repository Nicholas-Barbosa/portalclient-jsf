package com.portal.client.exception;

import org.apache.poi.ss.usermodel.CellType;

import com.portal.client.microsoft.excel.CellAttribute;

public class MismatchCellTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4396991606340075689L;

	private int rowNum;
	private CellAttribute cell;
	private CellType expectedType;

	public MismatchCellTypeException(int rowNum, CellAttribute cell, CellType expectedType) {
		super("Mismatch cell type for cell: " + cell.getCellOffset() + " in row: " + rowNum + ".Expected type: "
				+ expectedType + " but got: " + cell.getCellType());
		this.rowNum = rowNum;
		this.cell = cell;
		this.expectedType = expectedType;
	}

	public int getRowNum() {
		return rowNum;
	}

	public CellAttribute getCell() {
		return cell;
	}

	public CellType getExpectedType() {
		return expectedType;
	}
}
