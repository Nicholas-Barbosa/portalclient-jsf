package com.portal.java.dto;

import javax.validation.constraints.NotBlank;

import com.portal.java.resources.export.ExportType;

public class DownloadStreamsForm {

	@NotBlank
	private String name;
	private ExportType contentType;

	public DownloadStreamsForm() {
		// TODO Auto-generated constructor stub
	}

	public DownloadStreamsForm(@NotBlank String name, @NotBlank ExportType contentType) {
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

	public ExportType getContentType() {
		return contentType;
	}

	public void setContentType(ExportType contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "DownloadStreamsForm [name=" + name + ", contentType=" + contentType + "]";
	}

}
