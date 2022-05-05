package com.farawaybr.portal.exception;

import java.util.List;

import org.apache.poi.ss.usermodel.CellType;

import com.farawaybr.portal.microsoft.excel.CellAttribute;

public class MismatchCellTypeExceptions extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3090333437021992224L;
	
	private List<MismatchCellTypeException> exceptions;

	public MismatchCellTypeExceptions(List<MismatchCellTypeException> exceptions) {
		super();
		this.exceptions = exceptions;
	}

	public List<MismatchCellTypeException> getExceptions() {
		return exceptions;
	}

	public static class MismatchCellTypeException extends RuntimeException {

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
}