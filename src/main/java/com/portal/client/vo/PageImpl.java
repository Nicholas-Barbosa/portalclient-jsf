package com.portal.client.vo;

import java.util.ArrayList;
import java.util.Collection;

public class PageImpl<T> implements Page<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3855652703840029305L;

	private int pageSize, page, totalPages, totalItems;
	private Collection<T> content;

	public PageImpl(int pageSize, int page, int totalPages, int totalItems, Collection<T> content) {
		super();
		this.pageSize = pageSize;
		this.page = page;
		this.totalPages = totalPages;
		this.totalItems = totalItems;
		this.content = content;
	}

	public PageImpl(Page<T> page) {
		this(page.getPageSize(), page.getPage(), page.totalPages(), page.totalItems(),
				new ArrayList<>(page.getContent()));
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public int totalPages() {
		// TODO Auto-generated method stub
		return totalPages;
	}

	@Override
	public int totalItems() {
		// TODO Auto-generated method stub
		return totalItems;
	}

	@Override
	public Collection<T> getContent() {
		// TODO Auto-generated method stub
		return content;
	}

}
