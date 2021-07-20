package com.portal.client.dto;

import java.util.Collection;

import com.portal.client.vo.Page;

public class BasePageDTO<T> implements Page<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3745876969464987327L;
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

	@Override
	public int totalPages() {
		// TODO Auto-generated method stub
		return totalPage;
	}

	@Override
	public int totalItems() {
		// TODO Auto-generated method stub
		return totalItems;
	}

	@Override
	public Collection<T> getContent() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("getContent() must be overriden");
	}

}
