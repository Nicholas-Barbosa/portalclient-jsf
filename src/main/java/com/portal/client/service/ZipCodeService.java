package com.portal.client.service;

import java.util.Optional;

import com.portal.client.pojo.ZipCode;

public interface ZipCodeService {

	Optional<ZipCode> find(String cep);
}
