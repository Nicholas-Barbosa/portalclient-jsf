package com.portal.java.dto;

import javax.validation.constraints.NotBlank;

import com.portal.java.http.ReportType;

public class DownloadStreamsForm {

	@NotBlank
	private String name;
	private ReportType contentType;

	public DownloadStreamsForm() {
		// TODO Auto-generated constructor stub
	}

	public DownloadStreamsForm(@NotBlank String name, @NotBlank ReportType contentType) {
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

	public ReportType getContentType() {
		return contentType;
	}

	public void setContentType(ReportType contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "DownloadStreamsForm [name=" + name + ", contentType=" + contentType + "]";
	}

}
