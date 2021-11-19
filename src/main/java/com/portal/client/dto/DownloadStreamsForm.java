package com.portal.client.dto;

import javax.validation.constraints.NotBlank;

import com.portal.client.service.export.BudgetExportType;

public class DownloadStreamsForm {

	@NotBlank
	private String name;
	private BudgetExportType contentType;

	public DownloadStreamsForm() {
		// TODO Auto-generated constructor stub
	}

	public DownloadStreamsForm(@NotBlank String name, @NotBlank BudgetExportType contentType) {
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

	public BudgetExportType getContentType() {
		return contentType;
	}

	public void setContentType(BudgetExportType contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "DownloadStreamsForm [name=" + name + ", contentType=" + contentType + "]";
	}

}
