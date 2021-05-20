package com.portal.dto;

import java.util.Set;

public class BudgetDTO {

	private Set<ItemFormDTO>items;
	
	public Set<ItemFormDTO> getItems() {
		return items;
	}

	public BudgetDTO(Set<ItemFormDTO> items) {
		super();
		this.items = items;
	}
	
	
}
