package com.portal.dto;

import javax.validation.constraints.NotBlank;

import com.portal.http.ContentType;

public class DownloadStreamsForm {

	@NotBlank
	private String name;
	private ContentType contentType;

	public DownloadStreamsForm() {
		// TODO Auto-generated constructor stub
	}

	public DownloadStreamsForm(@NotBlank String name, @NotBlank ContentType contentType) {
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

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "DownloadStreamsForm [name=" + name + ", contentType=" + contentType + "]";
	}

}
