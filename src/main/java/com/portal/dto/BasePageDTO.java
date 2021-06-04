package com.portal.dto;

public class BasePageDTO {

	private int page;
	private int pageSize;
	private int totalItems;
	private int totalPage;

	public BasePageDTO() {
		// TODO Auto-generated constructor stub
	}

	public BasePageDTO(int page, int pageSize, int totalItems, int totalPage) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.totalItems = totalItems;
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
