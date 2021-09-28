package com.portal.client.vo.builder;

import java.util.Collection;

import com.portal.client.vo.Page;
import com.portal.client.vo.PageImpl;

public class PageBuilder<T> {

	private int pageSize, page, totalPages, totalItems;
	private Collection<T> content;

	public static <T> PageBuilder<T> getInstance(Class<T> type) {
		return new PageBuilder<T>();
	}

	public static <T> PageImpl<T> deepCopy(Page<T> page) {
		return new PageImpl<T>(page);
	}

	public PageBuilder<T> withPage(int page) {
		this.page = page;
		return this;
	}

	public PageBuilder<T> withPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public PageBuilder<T> withTotalPages(int totalPages) {
		this.totalPages = totalPages;
		return this;
	}

	public PageBuilder<T> withTotalItems(int totalItems) {
		this.totalItems = totalItems;
		return this;
	}

	public PageBuilder<T> withContent(Collection<T> content) {
		this.content = content;
		return this;
	}

	public PageImpl<T> build() {
		return new PageImpl<T>(pageSize, page, totalPages, totalItems, content);
	}
}
