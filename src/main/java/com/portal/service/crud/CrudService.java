package com.portal.service.crud;

public interface CrudService {

	<T> T find(int page, int pageSize);

}
