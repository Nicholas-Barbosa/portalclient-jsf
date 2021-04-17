package com.portal.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GssResponseClientsDTO {

	@JsonProperty("total_items")
	private Integer totalItems;
	@JsonProperty("total_page")
	private Integer totalPages;
	@JsonProperty("page_size")
	private Integer pageSize;
	@JsonProperty("page")
	private Integer page;

	@JsonProperty("client")
	private List<GssClientDTO> clients;

	public GssResponseClientsDTO() {
		// TODO Auto-generated constructor stub
	}

	public GssResponseClientsDTO(Integer totalItems, Integer totalPages, Integer pageSize, Integer page,
			List<GssClientDTO> clients) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.clients = clients;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public List<GssClientDTO> getClients() {
		return clients;
	}

	@Override
	public String toString() {
		return "GssResponseClientsDTO [totalItems=" + totalItems + ", totalPages=" + totalPages + ", pageSize="
				+ pageSize + ", page=" + page + ", clients=" + clients + "]";
	}

}
