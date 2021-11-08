package com.portal.client.dto;

import javax.validation.constraints.NotBlank;

import com.portal.client.service.export.OrderExportType;

public class DownloadStreamsForm {

	@NotBlank
	private String name;
	private OrderExportType contentType;

	public DownloadStreamsForm() {
		// TODO Auto-generated constructor stub
	}

	public DownloadStreamsForm(@NotBlank String name, @NotBlank OrderExportType contentType) {
		super();
		this.name = name;
		this.contentType = contentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrderExportType getContentType() {
		return contentType;
	}

	public void setContentType(OrderExportType contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "DownloadStreamsForm [name=" + name + ", contentType=" + contentType + "]";
	}

}
